package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PowerUp;
import it.polimi.ingsw.model.Weapon;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.command.PayReloadWeaponCommand;
import it.polimi.ingsw.model.command.SelectAmmoPaymentCommand;
import it.polimi.ingsw.model.command.SelectPowerUpPaymentCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PendingPaymentReloadWeaponState implements PendingPaymentState, PlayerState {

    private Map<Color, Integer> pendingAmmo;
    private List<PowerUp> pendingCardPayment;
    private Weapon selectedReloadingWeapon;

    public PendingPaymentReloadWeaponState(Weapon selectedWeapon) {
        pendingCardPayment = new ArrayList<>();
        pendingAmmo = new HashMap<>();
        this.selectedReloadingWeapon = selectedWeapon;
    }

    public Weapon getSelectedReloadingWeapon() {
        return selectedReloadingWeapon;
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
        Map<Color, Integer> totalPending = new HashMap<>();
        pendingCardPayment.forEach(powerUp -> totalPending.put(powerUp.getColor(), totalPending.getOrDefault(powerUp.getColor(), 0) + 1));
        selectedReloadingWeapon.getReloadingCost().forEach((color, cost) -> {
            if (cost > pendingAmmo.getOrDefault(color, 0) + totalPending.getOrDefault(color, 0)) {
                if (player.getAmmo().getOrDefault(color, 0) > 0) {
                    commands.add(new SelectAmmoPaymentCommand(player, this, color));
                }
                player.getPowerUps().forEach(powerUp -> {
                    if (powerUp.getColor() == color)
                        commands.add(new SelectPowerUpPaymentCommand(player, this, powerUp));
                });
            }
        });
        if (commands.isEmpty()) {
            commands.add(new PayReloadWeaponCommand(player, this));
        }
        return commands;
    }
}
