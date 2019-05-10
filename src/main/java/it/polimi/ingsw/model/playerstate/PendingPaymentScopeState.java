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

public class PendingPaymentScopeState extends SelectedWeaponState implements PendingPaymentState {

    private Map<Color, Integer> pendingAmmo;
    private List<PowerUp> pendingCardPayment;
    private List<Player> shootedPlayers;

    public PendingPaymentScopeState(AggregateAction selectedAggregateAction, Weapon selectedWeapon, List<Player> shootedPlayers) {
        super(selectedAggregateAction, selectedWeapon);
        pendingCardPayment = new ArrayList<>();
        pendingAmmo = new EnumMap<>(Color.class);
        this.shootedPlayers = shootedPlayers;
    }

    @Override
    public void addPendingAmmo(Color color) {
        pendingAmmo.put(color, pendingAmmo.getOrDefault(color, 0) + 1);
    }

    @Override
    public void addPendingCard(PowerUp powerUp) {
        pendingCardPayment.add(powerUp);
    }

    @Override
    public void removePendingAmmo(Color color) {
        if (pendingAmmo.getOrDefault(color, 0) <= 0)
            throw new IllegalStateException();
        pendingAmmo.put(color, pendingAmmo.get(color) - 1);
    }

    @Override
    public void removePendingCard(PowerUp powerUp) {
        if (!pendingCardPayment.contains(powerUp))
            throw new IllegalStateException();
        pendingCardPayment.remove(powerUp);
    }

    @Override
    public Map<Color, Integer> getPendingAmmoPayment() {
        return pendingAmmo;
    }

    @Override
    public List<PowerUp> getPendingCardPayment() {
        return pendingCardPayment;
    }

    public List<Player> getShootedPlayers() {
        return shootedPlayers;
    }

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
