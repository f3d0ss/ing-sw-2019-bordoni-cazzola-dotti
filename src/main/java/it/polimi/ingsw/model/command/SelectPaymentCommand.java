package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.PendingPaymentState;

/**
 * This command represent the action of select an item for pay something
 */
abstract class SelectPaymentCommand implements Command {
    final Player player;
    final PendingPaymentState currentState;

    SelectPaymentCommand(Player player, PendingPaymentState currentState) {
        this.player = player;
        this.currentState = currentState;
    }
}
