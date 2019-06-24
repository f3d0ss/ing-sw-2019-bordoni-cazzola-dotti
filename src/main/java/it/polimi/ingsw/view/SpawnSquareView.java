package it.polimi.ingsw.view;

import it.polimi.ingsw.model.CardinalDirection;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Connection;
import it.polimi.ingsw.model.PlayerId;

import java.util.List;
import java.util.Map;

public class SpawnSquareView extends SquareView {
    private List<WeaponView> weapons;

    public SpawnSquareView(int row, int col, Map<CardinalDirection, Connection> connection, Color color, List<WeaponView> weapons, List<PlayerId> hostedPlayers) {
        super(row, col, connection, color, hostedPlayers, "spawn");
        this.weapons = weapons;
    }

    public List<WeaponView> getWeapons() {
        return weapons;
    }
}
