package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.playerstate.ChoosingWeaponOptionState;
import it.polimi.ingsw.model.playerstate.ChoosingWeaponState;
import it.polimi.ingsw.view.commandmessage.CommandMessage;
import it.polimi.ingsw.view.commandmessage.CommandType;
import it.polimi.ingsw.view.commandmessage.WeaponCommandMessage;

/**
 * This command represent the action of select a weapon
 */
public class SelectWeaponCommand implements Command {
    /**
     * This is the player doing the command
     */
    private final Player player;
    /**
     * This is the weapon selected
     */
    private final Weapon weapon;
    /**
     * This is the current state of the player
     */
    private final ChoosingWeaponState currentState;

    /**
     * This constructor create a command for select a weapon
     *
     * @param player       is the player who select the weapon
     * @param weapon       is the weapon selected
     * @param currentState is the current state
     */
    public SelectWeaponCommand(Player player, Weapon weapon, ChoosingWeaponState currentState) {
        this.player = player;
        this.weapon = weapon;
        this.currentState = currentState;
    }

    /**
     * This method select the weapon for the shoot
     */
    @Override
    public void execute() {
        player.changeState(new ChoosingWeaponOptionState(currentState.getSelectedAggregateAction(), weapon));
    }

    /**
     * This method deselect the weapon
     */
    @Override
    public void undo() {
        player.changeState(currentState);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUndoable() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandMessage createCommandMessage() {
        return new WeaponCommandMessage(CommandType.SELECT_WEAPON, weapon.getName());
    }
}
