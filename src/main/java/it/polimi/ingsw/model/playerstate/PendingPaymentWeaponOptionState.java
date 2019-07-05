package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.command.PayWeaponOptionCommand;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * State when the player is paying to use a weapon mode
 */
public class PendingPaymentWeaponOptionState extends SelectedWeaponState implements PendingPaymentState {

    /**
     * Those are the ammo selected for pay the weapon mode
     */
    private final Map<Color, Integer> pendingAmmo;
    /**
     * This are the power up selected for pay the weapon mode
     */
    private final List<PowerUp> pendingCardPayment;
    /**
     * This is the cost of the weapon mode selected
     */
    private final Map<Color, Integer> modeCost;

    /**
     * This constructor create the state of the player when he is paying to use a weapon mode
     *
     * @param selectedAggregateAction This is the aggregate action the player is executing
     * @param selectedWeapon          This is the weapon selected in the action
     * @param optionalModeCost        This is the cost of the selected optional mode of the weapon
     */
    public PendingPaymentWeaponOptionState(AggregateAction selectedAggregateAction, Weapon selectedWeapon, Map<Color, Integer> optionalModeCost) {
        super(selectedAggregateAction, selectedWeapon);
        pendingAmmo = new EnumMap<>(Color.class);
        pendingCardPayment = new ArrayList<>();
        this.modeCost = optionalModeCost;
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
     * {@inheritDoc}
     */
    @Override
    public List<Command> getPossibleCommands(Player player) {
        List<Command> commands = new ArrayList<>();
        Map<Color, Integer> totalPending = PendingPaymentState.getTotalPendingPayment(pendingCardPayment, pendingAmmo);

        if (modeCost.equals(totalPending)) {
            commands.add(new PayWeaponOptionCommand(player, this));
            return commands;
        }
        return PendingPaymentState.generateSelectPaymentCommand(totalPending, player, modeCost, this);

    }
}
