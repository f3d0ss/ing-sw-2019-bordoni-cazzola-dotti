package it.polimi.ingsw.model.command;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.exception.IllegalUndoException;
import it.polimi.ingsw.model.playerstate.AfterShotState;
import it.polimi.ingsw.model.playerstate.ManageTurnState;
import it.polimi.ingsw.model.playerstate.ReadyToShootState;
import it.polimi.ingsw.model.playerstate.ScopeState;

import java.util.List;
import java.util.stream.Collectors;

public class ShootCommand implements WeaponCommand {
    private List<EffectCommand> effects;
    private Player player;
    private ReadyToShootState currentState;

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
        if (player.hasScope()){
            List<Player> shootedPlayer = effects.stream()
                    .filter(EffectCommand::hasDamage)
                    .map(EffectCommand::getPlayer).collect(Collectors.toList());
            if (!shootedPlayer.isEmpty()) {
                player.changeState(new ScopeState(currentState.getSelectedAggregateAction(), currentState.getSelectedWeapon(), shootedPlayer));
                return;
            }
        }
        if (currentState.getSelectedWeapon().hasExtraMove())
            player.changeState(new AfterShotState(currentState.getSelectedAggregateAction(), currentState.getSelectedWeapon()));
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
}
