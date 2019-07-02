package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.command.PayScopeCommand;
import it.polimi.ingsw.model.command.SelectAmmoPaymentCommand;
import it.polimi.ingsw.model.command.SelectPowerUpPaymentCommand;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * State when the player is paying to use a Scope power up
 */
public class PendingPaymentScopeState extends SelectedWeaponState implements PendingPaymentState {

    private final Map<Color, Integer> pendingAmmo;
    private final List<PowerUp> pendingCardPayment;
    private final List<Player> shootedPlayers;

    public PendingPaymentScopeState(AggregateAction selectedAggregateAction, Weapon selectedWeapon, List<Player> shootedPlayers) {
        super(selectedAggregateAction, selectedWeapon);
        pendingCardPayment = new ArrayList<>();
        pendingAmmo = new EnumMap<>(Color.class);
        this.shootedPlayers = shootedPlayers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPendingAmmo(Color color) {
        pendingAmmo.put(color, pendingAmmo.getOrDefault(color, 0) + 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPendingCard(PowerUp powerUp) {
        pendingCardPayment.add(powerUp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePendingAmmo(Color color) {
        if (pendingAmmo.getOrDefault(color, 0) <= 0)
            throw new IllegalStateException();
        pendingAmmo.put(color, pendingAmmo.get(color) - 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePendingCard(PowerUp powerUp) {
        if (!pendingCardPayment.contains(powerUp))
            throw new IllegalStateException();
        pendingCardPayment.remove(powerUp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Color, Integer> getPendingAmmoPayment() {
        return pendingAmmo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PowerUp> getPendingCardPayment() {
        return pendingCardPayment;
    }


    /**
     * Gets a list of the players that have just been shot by the players
     *
     * @return list of the shoot players
     */
    public List<Player> getShootedPlayers() {
        return shootedPlayers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Command> getPossibleCommands(Player player) {

        List<Command> commands = new ArrayList<>();
        if (pendingAmmo.isEmpty() && pendingCardPayment.isEmpty()) {
            player.getAmmo().forEach((color, amount) -> {
                if (amount > 0)
                    commands.add(new SelectAmmoPaymentCommand(player, this, color));
            });
            player.getPowerUps().forEach(powerUp -> commands.add(new SelectPowerUpPaymentCommand(player, this, powerUp)));
        } else {
            commands.add(new PayScopeCommand(player, this));
        }
        return commands;
    }
}
