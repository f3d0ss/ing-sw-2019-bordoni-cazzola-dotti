package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.command.PayWeaponCommand;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * State when the player is paying to buy a Weapon
 */
public class PendingPaymentWeaponState extends SelectedWeaponState implements PendingPaymentState {
    /**
     * Those are the ammo selected for pay the weapon
     */
    private final Map<Color, Integer> pendingAmmo;
    /**
     * This are the power up selected for pay the weapon
     */
    private final List<PowerUp> pendingCardPayment;

    /**
     * This constructor create the state of the player paying to buy a weapon
     *
     * @param selectedAggregateAction This is the aggregate action the player is executing
     * @param selectedWeapon          This is the weapon selected in the action
     */
    public PendingPaymentWeaponState(AggregateAction selectedAggregateAction, Weapon selectedWeapon) {
        super(selectedAggregateAction, selectedWeapon);
        pendingAmmo = new EnumMap<>(Color.class);
        pendingCardPayment = new ArrayList<>();
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
        pendingCardPayment.add(powerUp);
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
     * {@inheritDoc}
     */
    @Override
    public List<Command> getPossibleCommands(Player player) {
        List<Command> commands = new ArrayList<>();
        Map<Color, Integer> totalPending = PendingPaymentState.getTotalPendingPayment(pendingCardPayment, pendingAmmo);

        if (getSelectedWeapon().getWeaponBuyCost().equals(totalPending)) {
            commands.add(new PayWeaponCommand(player, this));
            return commands;
        }

        return PendingPaymentState.generateSelectPaymentCommand(totalPending, player, getSelectedWeapon().getWeaponBuyCost(), this);

    }
}
