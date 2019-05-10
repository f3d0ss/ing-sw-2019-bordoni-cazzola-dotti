package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerId;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages an entire match
 */
public class MatchController {
    private Match match;
    private Player[] players;

    public MatchController(Lobby lobby, int gameBoardNumber) {
        match = new Match(gameBoardNumber);
        List<String> nicknames = lobby.getNicknames();
        PlayerId[] values = PlayerId.values();
        players = new Player[lobby.getNicknames().size()];
        for (int i = 0; i < nicknames.size(); i++) {
            String nickname = nicknames.get(i);
            Player player = new Player(match, values[i], nickname);
            match.addPlayer(player);
            players[i] = player;
        }

    }

    public static void main(String[] args) {
        MatchController matchController = new MatchController(new Lobby(), 1);
        System.out.println(matchController.getMatch().getCurrentPlayers());

    }

    public Match getMatch() {
        return match;
    }

    private void initializeMatch() {
        //TODO all cards / stuff created and ready
    }

    private void runMatch() {
        //TODO first turn ?
        int currentPlayer = 0;
        while (!match.isLastTurn()) {
            if (!players[currentPlayer].isDisconnected()) {
                new TurnController(match, players[currentPlayer]);
                endTurnControls(currentPlayer);
            }
            currentPlayer = (currentPlayer + 1) % players.length;
        }
        runLastTurn(currentPlayer);
    }

    void endTurnControls(int currentPlayer) {
        //TODO check weapons,powerups, ammotiles, deaths, respawn players
        //check if at least 2 players are connected ...
    }

    private void runLastTurn(int currentPlayer) {
        for (int i = 0; i < players.length; i++) {
            if (currentPlayer == 0)
                match.firstPlayerPlayedLastTurn();
            if (!players[currentPlayer].isDisconnected()) {
                new TurnController(match, players[currentPlayer]);
                endTurnControls(currentPlayer);
            }
            currentPlayer = (currentPlayer + 1) % players.length;
        }
        calculateFinalScores();
    }

    private void calculateFinalScores() {
        //TODO calculate and send results
        endMatch();
    }

    private void endMatch() {
        //TODO
    }

    public static class Lobby {
        List<String> nicknames = new ArrayList<>();

        Lobby() {
            addPlayer("Paolo");
            addPlayer("Paul");
            addPlayer("PierPaolo");
            addPlayer("Bordo");
            addPlayer("Paolino");
        }

        public List<String> getNicknames() {
            return nicknames;
        }

        void addPlayer(String nickname) {
            nicknames.add(nickname);
        }
    }
}
