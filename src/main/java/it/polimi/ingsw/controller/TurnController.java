package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.ManageTurnState;

/**
 * This class manages a single turn
 */
public class TurnController {

    private Match match;
    private Player currentPlayer;

    public TurnController(Match match, Player player) {
        this.match = match;
        this.currentPlayer = player;
    }

    private void runTurn() {
        currentPlayer.changeState(new ManageTurnState());

    }
}
