package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PowerUp;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.model.command.SelectAmmoPaymentCommand;
import it.polimi.ingsw.model.command.SelectPowerUpPaymentCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface PendingPaymentState {
    void addPendingAmmo(Color color);

    void addPendingCard(PowerUp powerUp);

    void removePendingAmmo(Color color);

    void removePendingCard(PowerUp powerUp);

    Map<Color, Integer> getPendingAmmoPayment();

    List<PowerUp> getPendingCardPayment();

    static List<Command> generateSelctPaymentCommand(Map<Color, Integer> totalPending, Player player, Map<Color, Integer> totalCost, PendingPaymentState state){
        List<Command> commands = new ArrayList<>();
        totalCost.forEach((color, cost) -> {
            if (cost > totalPending.getOrDefault(color, 0)) {
                if (player.getAmmo().getOrDefault(color, 0) > 0) {
                    commands.add(new SelectAmmoPaymentCommand(player, state, color));
                }
                player.getPowerUps().forEach(powerUp -> {
                    if (powerUp.getColor() == color)
                        commands.add(new SelectPowerUpPaymentCommand(player, state, powerUp));
                });

            }
        });
        return commands;
    }
}
