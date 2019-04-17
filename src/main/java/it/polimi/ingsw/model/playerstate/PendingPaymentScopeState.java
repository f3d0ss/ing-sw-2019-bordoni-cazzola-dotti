package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.command.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PendingPaymentScopeState extends SelectedWeaponState implements PendingPaymentState {

    private Map<Color, Integer> pendingAmmo;
    private List<PowerUp> pendingCardPayment;
    private PowerUp scope;

    public PendingPaymentScopeState(AggregateAction selectedAggregateAction, Weapon selectedWeapon, PowerUp scope) {
        super(selectedAggregateAction, selectedWeapon);
        pendingCardPayment = new ArrayList<>();
        pendingAmmo = new HashMap<>();
    }

    @Override
    public void addPendingAmmo(Color color) {
        pendingAmmo.put(color, pendingAmmo.getOrDefault(color, 0));
    }

    @Override
    public void addPendingCard(PowerUp powerUp) {
        pendingCardPayment.add(powerUp);
    }

    @Override
    public Map<Color, Integer> getPendingAmmoPayment() {
        return pendingAmmo;
    }

    @Override
    public List<PowerUp> getPendingCardPayment() {
        return pendingCardPayment;
    }

    @Override
    public List<Command> getPossibleCommands(Player player) {

        List<Command> commands = new ArrayList<>();
        if (pendingAmmo.isEmpty() && pendingCardPayment.isEmpty()) {
            player.getAmmo().forEach((color, integer) -> commands.add(new SelectAmmoPaymentCommand(player, this, color)));
            player.getPowerUps().forEach(powerUp -> commands.add(new SelectPowerUpPaymentCommand(player, this, powerUp)));
        } else {
            commands.add(new PayScopeCommand(player, this));
        }
        return commands;
    }
}
