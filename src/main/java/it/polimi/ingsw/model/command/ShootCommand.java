package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.playerstate.ManageTurnState;
import it.polimi.ingsw.model.playerstate.ReadyToShootState;
import it.polimi.ingsw.model.playerstate.ScopeState;
import it.polimi.ingsw.view.commandmessage.CommandMessage;
import it.polimi.ingsw.view.commandmessage.CommandType;
import it.polimi.ingsw.view.commandmessage.EffectCommandMessage;
import it.polimi.ingsw.view.commandmessage.ShootCommandMessage;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This command represent the shoot action
 */
public class ShootCommand implements WeaponCommand {
    private final List<EffectCommand> effects;
    private final Player player;
    private final ReadyToShootState currentState;

    /**
     * This constructor create a command for shoot
     *
     * @param currentState is the current state
     * @param effects      is the list of single effects
     * @param player       is the player who shoot
     */
    public ShootCommand(ReadyToShootState currentState, List<EffectCommand> effects, Player player) {
        this.currentState = currentState;
        this.effects = effects;
        this.player = player;
    }

    /**
     * This method execute the shoot
     */
    @Override
    public void execute() {
        effects.forEach(EffectCommand::execute);
        currentState.getSelectedWeapon().shoot();
        if (player.hasScope()) {
            List<Player> shootedPlayer = effects.stream()
                    .filter(EffectCommand::hasDamage)
                    .map(EffectCommand::getPlayer).collect(Collectors.toList());
            if (!shootedPlayer.isEmpty()) {
                player.changeState(new ScopeState(currentState.getSelectedAggregateAction(), currentState.getSelectedWeapon(), shootedPlayer));
                return;
            }
        }
        if (currentState.getSelectedWeapon().hasExtraMove())
            player.changeState(new ReadyToShootState(currentState.getSelectedAggregateAction(), currentState.getSelectedWeapon()));
        else
            player.changeState(new ManageTurnState());
    }

    /**
     * This method throw an exception because after a shoot you can't go back
     */
    @Override
    public void undo() {
        throw new IllegalUndoException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUndoable() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandMessage createCommandMessage() {
        List<EffectCommandMessage> effectCommandMessageList = effects
                .stream()
                .map(EffectCommand::createCommandMessage)
                .collect(Collectors.toList());
        return new ShootCommandMessage(CommandType.SHOOT, effectCommandMessageList);
    }
}
