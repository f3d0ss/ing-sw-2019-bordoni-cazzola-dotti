package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerId;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.command.SelectPowerUpToDiscardCommand;
import it.polimi.ingsw.view.ViewInterface;
import it.polimi.ingsw.view.VirtualView;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This class manages an entire match
 */
public class MatchController {
    private static final int[] POINTS_PER_KILL = {8, 6, 4, 2, 1};
    private static final int[] POINTS_PER_KILL_FLIPPED_BOARD = {2, 1};
    private static final int MIN_PLAYERS = 3;
    private static final int POINTS_PER_FIRST_BLOOD = 1;
    private Match match;
    private Map<PlayerId, ViewInterface> virtualViews;
    private List<Player> players;

    public MatchController(Map<Integer, String> lobby, int gameBoardNumber) {
        match = new Match(gameBoardNumber);
        PlayerId[] values = PlayerId.values();
        List<String> nicknames = lobby.values().stream().collect(Collectors.toList());
        for (int i = 0; i < nicknames.size(); i++) {
            //Vv vv = new Vv()
            ViewInterface vv = new VirtualView();
            String nickname = nicknames.get(i);
            Player player = new Player(match, values[i], nickname);
            match.addPlayer(player); // and his vv
            //match add vv
        }
        players = match.getCurrentPlayers();
    }

    private void initializeMatch() {
        //TODO all cards / stuff created and ready
    }

    private void runMatch() {
        //TODO first turn ?
        int currentPlayer = 0;
        while (!match.isLastTurn()) {
            if (!players.get(currentPlayer).isDisconnected()) {
                new TurnController(match, players.get(currentPlayer), virtualViews);
                endTurnControls(currentPlayer);
            }
            currentPlayer = (currentPlayer + 1) % players.size();
        }
        runLastTurn(currentPlayer);
    }


    void endTurnControls(int currentPlayer) {
        //TODO check weapons ammotiles, deaths, respawn players
        //check if at least 2 players are connected ...
        match.restoreCards();
        for (Player player : players) {
            if (player.isDead()) {
                player.addDeaths();
                calculateScores(player);
            }
            respawn(currentPlayer);
        }


    }

    private void runLastTurn(int currentPlayer) {
        //player with no damage get flipped board
        for (int i = 0; i < players.size(); i++) {
            if (currentPlayer == 0)
                match.firstPlayerPlayedLastTurn();
            if (!players.get(currentPlayer).isDisconnected()) {
                new TurnController(match, players.get(currentPlayer), virtualViews);
                //if it's not the last player
                endTurnControls(currentPlayer);
            }
            currentPlayer = (currentPlayer + 1) % players.size();
        }
        calculateFinalScores();
    }

    private void calculateFinalScores() {
        //TODO calculate and send results
        //break the tie in favor of the player who got the higher score on the killshot track

        List<Player> collect = players.stream()
                .sorted((p1, p2) -> Integer.compare(p2.getPoints(), p1.getPoints()))
                .collect(Collectors.toList());

        endMatch();
    }

    private void endMatch() {
        //TODO
    }

    private List<Player> calculateKillShootTrackScores() {

        PlayerId firstBlood;

        match.getKillshotTrack();
        return null;
    }

    private void calculateScores(Player deadPlayer) {
        List<PlayerId> track = deadPlayer.getHealth();
        //firstblood is 1point (NO 4 FLIPPED BOARD in final)
        //TODO check for flipp board? (other method)maybe
        getPlayer(track.get(0)).addPoints(POINTS_PER_FIRST_BLOOD);
        //TODO 2+ kill 1 point per extra kill
        //marks for 12th dmg
        //extra token on killshottrack for 12th dmg
        PlayerId playerId12thDamage = track.get(Player.MAX_DAMAGE - 1);
        if (playerId12thDamage != null) {
            getPlayer(playerId12thDamage).addMarks(1, deadPlayer.getId());
            match.addKillshot(playerId12thDamage);
            match.addKillshot(playerId12thDamage);
        }

        List<PlayerId> orderByFirstBlood = track.stream().distinct().collect(Collectors.toList());

        Map<PlayerId, Long> counts = track.stream()
                .collect(Collectors.groupingBy(a -> a, Collectors.counting()));

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
        PlayerId[] keys = (PlayerId[]) counts.keySet().toArray();
        if (!duplicates.isEmpty()) {
            keys = new PlayerId[counts.size()];
            Long[] values = new Long[counts.size()];
            int index = 0;
            for (Map.Entry<PlayerId, Long> mapEntry : counts.entrySet()) {
                keys[index] = mapEntry.getKey();
                values[index] = mapEntry.getValue();
                index++;
            }
            for (Long duplicate : duplicates) {
                int duplicateFrequency = Collections.frequency(Arrays.asList(values), duplicate);
                for (int j = 1; j < duplicateFrequency; j++) {
                    for (int i = 1; i < values.length; i++) {
                        if (values[i].equals(values[i - 1]) && orderByFirstBlood.indexOf(keys[i - 1]) > orderByFirstBlood.indexOf(keys[i])) {
                            PlayerId tempId = keys[i];
                            keys[i] = keys[i - 1];
                            keys[i - 1] = tempId;
                        }
                    }
                }
            }
        }
        //8 6 4 2 1..1 (should be defined somewhere as Const)
        //give points
        int offset = deadPlayer.getDeaths();
        for (int i = 0; i < keys.length; i++) {
            int points;
            if (i + offset >= POINTS_PER_KILL.length)
                points = POINTS_PER_KILL[POINTS_PER_KILL.length - 1];
            else
                points = POINTS_PER_KILL[i + offset];
            getPlayer(keys[i]).addPoints(points);
        }
    }

    private void respawn(int playerNumber) {
        //TODO playerstate for respawn?
        Player currentPlayer = players.get(playerNumber);
        //draw 1 pu (even if u have 3)
        currentPlayer.drawPowerUpForRespawn();
        List<Command> commands = new ArrayList<>();
        currentPlayer.getPowerUps()
                .forEach(powerUp -> commands.add(new SelectPowerUpToDiscardCommand(currentPlayer, powerUp)));
        int selectedCommand = virtualViews.get(currentPlayer.getId()).sendCommands(commands);
        commands.get(selectedCommand).execute();
    }

    private void checkDisconnections() {
        int counter = 0;
        for (Player player : players) {
            if (!player.isDisconnected())
                counter++;
        }
        if (counter < MIN_PLAYERS) {
            //end match
            calculateFinalScores();
            endMatch();
        }


    }

    private Player getPlayer(PlayerId playerId) {
        for (Player player : players)
            if (player.getId().equals(playerId))
                return player;
        return null;
    }

    public static void main(String[] args) {
        Map<Integer, String> lobby = new HashMap<>();
        lobby.put(1, "Paolo");
        lobby.put(2, "DDE$&T");
        lobby.put(3, "LOLL");
        lobby.put(4, "Paolo");
        lobby.put(5, "Marco");
        MatchController matchController = new MatchController(lobby, 1);
        System.out.println(matchController.match.getCurrentPlayers());
        matchController.match.getCurrentPlayers().stream().map(Player::getId).forEach(System.out::println);
        List<Integer> l = new ArrayList<>();
        l.addAll(Arrays.asList(1, 2, 5, 6, 5, 1, 22, 22, 1, 0, 0));
        //l.stream().sorted((a, b) -> Integer.compare(b, a)).forEach(System.out::println);
        List<PlayerId> track = new ArrayList<>();
        track.add(PlayerId.BLUE);
        track.add(PlayerId.GREEN);
        track.add(PlayerId.BLUE);
        track.add(PlayerId.GREY);
        track.add(PlayerId.GREY);
        track.add(PlayerId.GREY);
        track.add(PlayerId.GREY);
        track.add(PlayerId.YELLOW);
        track.add(PlayerId.VIOLET);
        track.add(PlayerId.VIOLET);
        track.add(PlayerId.GREEN);
        track.add(PlayerId.VIOLET);
        track.add(PlayerId.VIOLET);
        track.add(PlayerId.GREEN);
        track.add(PlayerId.BLUE);
        track.add(PlayerId.BLUE);
        track.add(PlayerId.YELLOW);
        track.add(PlayerId.YELLOW);
        track.add(PlayerId.YELLOW);
        track.add(PlayerId.GREEN);
        Map<PlayerId, Long> counts = track.stream()
                .collect(Collectors.groupingBy(a -> a, Collectors.counting()));
        System.out.println(counts.toString());
        counts = counts.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        System.out.println(counts.toString());
        List<PlayerId> orderByFirstBlood = track.stream().distinct().collect(Collectors.toList());
        System.out.println(orderByFirstBlood);
        List<Long> duplicates = counts.values().stream().collect(Collectors.groupingBy(Function.identity()))
                .entrySet()
                .stream()
                .filter(e -> e.getValue().size() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        System.out.println(duplicates);
        if (!duplicates.isEmpty()) {
                /*Map.Entry<PlayerId, Long> prev = null;
                for (Map.Entry<PlayerId, Long> entry : counts.entrySet()) {
                    if (prev != null) {
                        //same damage -> first blood is first
                        if (entry.getValue() == prev.getValue() && orderByFirstBlood.indexOf(prev.getKey()) > orderByFirstBlood.indexOf(entry.getKey())) {
                            //swap
                            prev.setValue(entry.setValue(prev.getValue()));
                        }
                    }
                    prev = entry;
                }*/
            PlayerId[] keys = new PlayerId[counts.size()];
            Long[] values = new Long[counts.size()];
            int index = 0;
            for (Map.Entry<PlayerId, Long> mapEntry : counts.entrySet()) {
                keys[index] = mapEntry.getKey();
                values[index] = mapEntry.getValue();
                index++;
            }

            System.out.println(Arrays.stream(keys).collect(Collectors.toList()));
            System.out.println(Arrays.stream(values).collect(Collectors.toList()));
            for (Long duplicate : duplicates) {
                int duplicateFrequency = Collections.frequency(Arrays.asList(values), duplicate);
                for (int j = 1; j < duplicateFrequency; j++) {
                    for (int i = 1; i < values.length; i++) {
                        if (values[i].equals(values[i - 1]) && orderByFirstBlood.indexOf(keys[i - 1]) > orderByFirstBlood.indexOf(keys[i])) {
                            //swap
                            //Long temp = values[i];
                            PlayerId tempId = keys[i];
                            // values[i] = values[i - 1];
                            keys[i] = keys[i - 1];
                            //  values[i - 1] = temp;
                            keys[i - 1] = tempId;
                        }
                    }
                }
            }

            Map map = new LinkedHashMap();
            for (int i = 0; i < keys.length; i++) {
                map.put(keys[i], values[i]);
            }

            System.out.println(Arrays.stream(keys).collect(Collectors.toList()));
            System.out.println(Arrays.stream(values).collect(Collectors.toList()));
            System.out.println(map);
        }

    }

}
