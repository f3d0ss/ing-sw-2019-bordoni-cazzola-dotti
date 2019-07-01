package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerId;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.view.ViewInterface;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static it.polimi.ingsw.network.server.ServerManager.MIN_PLAYERS;

/**
 * This class manages an entire match.
 * Controls the flow of a game, modifies the model using inputs received from the views.
 */
public class MatchController implements Runnable {
    private static final int[] POINTS_PER_KILL = {8, 6, 4, 2, 1};
    private static final int[] POINTS_PER_KILL_FLIPPED_BOARD = {2, 1};
    private static final int POINTS_PER_FIRST_BLOOD = 1;
    private static final int MARKS_PER_EXTRA_DAMAGE = 1;
    private final Match match;
    private final Map<PlayerId, ViewInterface> virtualViews = new LinkedHashMap<>();
    private final List<Player> players;

    public MatchController(Map<String, ViewInterface> lobby, int gameBoardNumber, int skulls) {
        match = new Match(skulls);
        match.initializeFromStandardFiles(gameBoardNumber);
        PlayerId[] values = PlayerId.values();
        List<String> nicknames = new ArrayList<>(lobby.keySet());
        for (int i = 0; i < nicknames.size(); i++) {
            String nickname = nicknames.get(i);
            Player player = new Player(match, values[i], nickname);
            match.addPlayer(player, lobby.get(nickname));
            virtualViews.put(values[i], lobby.get(nickname));
        }
        players = match.getCurrentPlayers();
    }

    /**
     * This method sends an update of the match to every client
     */
    public void sendFirstStateOfModel() {
        match.updateAllModel();
    }

    /**
     * This method reconnects a player to the game
     *
     * @param username Username of the player who's reconnected
     */
    public void reconnect(String username) {
        for (Player player : players)
            if (player.getNickname().equals(username))
                player.setConnected();
    }

    /**
     * This method sets as disconnected the player
     *
     * @param username Username of the player who's disconnected
     */
    public void disconnect(String username) {
        for (Player player : players)
            if (player.getNickname().equals(username)) {
                player.setDisconnected();
                checkDisconnections();
            }
    }

    /**
     * This method orders a map following the official rules:
     * If multiple players dealt the same amount of damage, break the tie in favor of the player whose damage landed first
     *
     * @param counts            map to order (ID,number of points/tokens)
     * @param orderByFirstBlood must be ordered by first blood
     * @return Leaderboard (ordered Map)
     */
    static Map<PlayerId, Long> sort(Map<PlayerId, Long> counts, List<PlayerId> orderByFirstBlood) {
        counts = counts.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        List<Long> duplicates = counts.values().stream().collect(Collectors.groupingBy(Function.identity()))
                .entrySet()
                .stream()
                .filter(e -> e.getValue().size() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        PlayerId[] keys;
        if (!duplicates.isEmpty()) {
            keys = new PlayerId[counts.size()];
            Long[] values = new Long[counts.size()];
            int index = 0;
            for (Map.Entry<PlayerId, Long> mapEntry : counts.entrySet()) {
                keys[index] = mapEntry.getKey();
                values[index] = mapEntry.getValue();
                index++;
            }
            duplicates.stream()
                    .mapToInt(duplicate -> Collections.frequency(Arrays.asList(values), duplicate))
                    .flatMap(duplicateFrequency -> IntStream.range(1, duplicateFrequency))
                    .flatMap(j -> IntStream.range(1, values.length))
                    .filter(i -> values[i].equals(values[i - 1]) && orderByFirstBlood.indexOf(keys[i - 1]) > orderByFirstBlood.indexOf(keys[i]))
                    .forEach(i -> {
                        PlayerId tempId = keys[i];
                        keys[i] = keys[i - 1];
                        keys[i - 1] = tempId;
                    });
            LinkedHashMap<PlayerId, Long> sortedMap = new LinkedHashMap<>();
            for (int i = 0; i < keys.length; i++) {
                sortedMap.put(keys[i], values[i]);
            }
            counts = sortedMap;
        }
        return counts;
    }

    /**
     * This method handles the first turn of each player
     */
    private void runFirstTurn() {
        for (Player currentPlayer : players) {
            spawnFirstTime(currentPlayer);
            if (!currentPlayer.isDisconnected()) {
                new TurnController(currentPlayer, players, virtualViews).runTurn();
                endTurnControls(currentPlayer);
            }
        }
    }

    /**
     * This method starts the match
     */
    @Override
    public void run() {
        runFirstTurn();
        int currentPlayerIndex = 0;
        while (!match.isLastTurn()) {
            currentPlayerIndex = runTurn(currentPlayerIndex);
        }
        runFinalFrenzyTurn(currentPlayerIndex);
    }

    /**
     * This method handles a player turn
     *
     * @param currentPlayerIndex Player's index whose turn is starting
     * @return next player's index
     */
    private int runTurn(int currentPlayerIndex) {
        Player currentPlayer = players.get(currentPlayerIndex);
        if (!currentPlayer.isDisconnected()) {
            new TurnController(currentPlayer, players, virtualViews).runTurn();
            endTurnControls(currentPlayer);
        }
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        return currentPlayerIndex;
    }

    /**
     * This method shall be called at end of each player's turn. Checks disconnections, restores cards, scores points, respawns dead players.
     *
     * @param currentPlayer Player whose turn just ended
     */
    private void endTurnControls(Player currentPlayer) {
        checkDisconnections();
        match.restoreCards();
        int numberOfKills = 0;
        for (Player player : players) {
            if (player.isDead()) {
                numberOfKills++;
                player.addDeaths();
                calculateTrackScores(player);
                respawn(player);
            }
        }
        if (numberOfKills > 1) {
            currentPlayer.addPoints(numberOfKills - 1);
        }
    }

    /**
     * This method handles the last turn of each player
     *
     * @param currentPlayerIndex index of the first player to play the last turn
     */
    private void runFinalFrenzyTurn(int currentPlayerIndex) {
        //player with no damage get flipped board
        giveFlippedBoards();
        for (int i = 0; i < players.size(); i++) {
            if (currentPlayerIndex == 0)
                match.firstPlayerPlayedLastTurn();
            currentPlayerIndex = runTurn(currentPlayerIndex);
        }
        calculateFinalScores();
    }

    /**
     * This method make all players with no damage (including those who were just scored) flip their boards over.
     * They keep marks and ammo.
     */
    private void giveFlippedBoards() {
        players.stream()
                .filter(player -> player.getHealth().isEmpty())
                .forEach(Player::flipBoard);
    }

    /**
     * This method calculates the leaderboard
     * Break the tie in favor of the player who got the higher score on the kill shot track
     */
    private void calculateFinalScores() {
        List<PlayerId> killShootTrackLeaderBoard = Arrays.asList(scoreAllBoards());
        Map<PlayerId, Long> leaderBoard = players.stream()
                .collect(Collectors.toMap(Player::getId, player -> (long) player.getPoints(), (a, b) -> b, LinkedHashMap::new));
        leaderBoard = sort(leaderBoard, killShootTrackLeaderBoard);
        match.setLeaderBoard(leaderBoard);
    }

    /**
     * This method is called after  the  final  turn,  score  all  boards  that  still  have  damage  tokens.
     * Score  them  as  you  usually would, except, of course, they don't have kill shots.
     * If you are playing with the final frenzy rules, don't forget that flipped boards offer no point for first blood.
     *
     * @return Kill shot Track leaderboard
     */
    private PlayerId[] scoreAllBoards() {
        for (Player player : players) {
            if (!player.isDead()) {
                calculateTrackScores(player);
            }
        }
        PlayerId[] killShotTrack = sortByPoints(match.getKillshotTrack());
        scoreKillShotTrackPoints(killShotTrack);
        return killShotTrack;
    }

    private void endMatch() {
        //TODO send end-game message
    }

    /**
     * This method adds the points for the deadPlayer's track. Adds marks to kill shot Track
     *
     * @param deadPlayer track to score owner.
     */
    private void calculateTrackScores(Player deadPlayer) {
        List<PlayerId> track = deadPlayer.getHealth();
        if (!track.isEmpty()) {
            if (!deadPlayer.isFlippedBoard())
                Objects.requireNonNull(getPlayerById(track.get(0))).addPoints(POINTS_PER_FIRST_BLOOD);
            PlayerId playerId11thDamage = track.get(track.size() - 1);
            match.addKillshot(playerId11thDamage);
            if (track.size() == Player.MAX_DAMAGE) {
                Objects.requireNonNull(getPlayerById(playerId11thDamage)).addMarks(MARKS_PER_EXTRA_DAMAGE, deadPlayer.getId());
                match.addKillshot(playerId11thDamage);
            }
            scoreTrackPoints(deadPlayer, sortByPoints(track));
        }
    }

    /**
     * This method return an array of PlayerId ordered by most points scored on this track
     *
     * @param track track to score
     * @return Array ordered by first blood
     */
    private PlayerId[] sortByPoints(List<PlayerId> track) {
        List<PlayerId> orderByFirstBlood = track.stream().distinct().collect(Collectors.toList());

        Map<PlayerId, Long> counts = track.stream()
                .collect(Collectors.groupingBy(a -> a, Collectors.counting()));

        counts = sort(counts, orderByFirstBlood);
        return counts.keySet().toArray(new PlayerId[0]);
    }

    /**
     * This method adds the points for number of damage tokens
     *
     * @param deadPlayer player dead
     * @param keys       must be sorted by rules
     */
    private void scoreTrackPoints(Player deadPlayer, PlayerId[] keys) {
        final int[] pointsPerKill;
        int offset = 0;
        if (deadPlayer.isFlippedBoard()) pointsPerKill = POINTS_PER_KILL_FLIPPED_BOARD;
        else {
            pointsPerKill = POINTS_PER_KILL;
            offset = deadPlayer.getDeaths();
        }
        for (int i = 0; i < keys.length; i++) {
            int points;
            if (i + offset >= pointsPerKill.length)
                points = pointsPerKill[pointsPerKill.length - 1];
            else
                points = pointsPerKill[i + offset];
            Objects.requireNonNull(getPlayerById(keys[i])).addPoints(points);
        }
    }

    /**
     * This method assigns points for the kill shot track
     *
     * @param keys sorted by rules
     */
    private void scoreKillShotTrackPoints(PlayerId[] keys) {
        final int[] pointsPerKill = POINTS_PER_KILL;
        for (int i = 0; i < keys.length; i++) {
            int points;
            if (i >= pointsPerKill.length)
                points = pointsPerKill[pointsPerKill.length - 1];
            else
                points = pointsPerKill[i];
            Objects.requireNonNull(getPlayerById(keys[i])).addPoints(points);
        }
    }

    /**
     * This method handles the respawn phase
     *
     * @param currentPlayer player to respawn
     */
    private void respawn(Player currentPlayer) {
        List<Command> commands = new ArrayList<>(currentPlayer.getRespawnCommands());
        if (currentPlayer.isDisconnected())
            commands.get(new Random().nextInt(commands.size())).execute();
        else {
            int selectedCommand = virtualViews.get(currentPlayer.getId()).sendCommands(commands.stream()
                    .map(Command::createCommandMessage)
                    .collect(Collectors.toList()), false);
            if (selectedCommand != -1) {
                commands.get(selectedCommand).execute();
            } else {
                commands.get(new Random().nextInt(commands.size())).execute();
            }
        }
    }

    /**
     * This method handles the spawn before the first turn starts
     *
     * @param currentPlayer player to spawn
     */
    private void spawnFirstTime(Player currentPlayer) {
        List<Command> commands = new ArrayList<>(currentPlayer.getSpawnCommands());
        if (!currentPlayer.isDisconnected()) {
            int selectedCommand = virtualViews.get(currentPlayer.getId())
                    .sendCommands(commands.stream()
                            .map(Command::createCommandMessage)
                            .collect(Collectors.toList()), false);
            if (selectedCommand == -1)
                commands.get(new Random().nextInt(commands.size())).execute();
            else
                commands.get(selectedCommand).execute();
        } else {
            commands.get(new Random().nextInt(commands.size())).execute();
        }
    }

    /**
     * This method checks if min number of players is connected or ends the match
     */
    private void checkDisconnections() {
        int counter = 0;
        for (Player player : players) {
            if (!player.isDisconnected())
                counter++;
        }
        if (counter < MIN_PLAYERS) {
            System.out.println("Too many disconnections --- ENDING MATCH");
            //end match
            calculateFinalScores();
            endMatch();
        }
    }

    private Player getPlayerById(PlayerId playerId) {
        for (Player player : players)
            if (player.getId().equals(playerId))
                return player;
        return null;
    }

}
