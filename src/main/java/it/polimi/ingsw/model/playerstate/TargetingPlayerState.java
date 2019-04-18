package it.polimi.ingsw.model.playerstate;

import it.polimi.ingsw.model.Player;

public interface TargetingPlayerState extends PlayerState {
    void addTargetPlayer(Player player);

    void removeTargetPlayer(Player player);
}
