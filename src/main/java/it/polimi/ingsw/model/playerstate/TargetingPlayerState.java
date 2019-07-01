package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.Player;

public interface TargetingPlayerState extends PlayerState {
    /**
     * Adds a target player
     *
     * @param player target to add
     */
    void addTargetPlayer(Player player);

    /**
     * Removes a target player
     *
     * @param player target to remove
     */
    void removeTargetPlayer(Player player);
}
