package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.command.PayWeaponCommand;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class PendingPaymentWeaponState extends SelectedWeaponState implements PendingPaymentState {
    private final Map<Color, Integer> pendingAmmo;
    private final List<PowerUp> pendingCardPayment;

    public PendingPaymentWeaponState(AggregateAction selectedAggregateAction, Weapon selectedWeapon) {
        super(selectedAggregateAction, selectedWeapon);
        pendingAmmo = new EnumMap<>(Color.class);
        pendingCardPayment = new ArrayList<>();
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
        Map<Color, Integer> totalPending = new EnumMap<>(Color.class);
        pendingCardPayment.forEach(powerUp -> totalPending.put(powerUp.getColor(), totalPending.getOrDefault(powerUp.getColor(), 0) + 1));
        pendingAmmo.forEach((color, integer) -> totalPending.put(color, totalPending.getOrDefault(color, 0) + integer));

        if (getSelectedWeapon().getWeaponBuyCost().equals(totalPending)) {
            commands.add(new PayWeaponCommand(player, this));
            return commands;
        }

        return PendingPaymentState.generateSelectPaymentCommand(totalPending, player, getSelectedWeapon().getWeaponBuyCost(), this);

    }
}
