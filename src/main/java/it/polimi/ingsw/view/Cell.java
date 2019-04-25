package it.polimi.ingsw.view;

import it.polimi.ingsw.model.CardinalDirection;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Connection;
import it.polimi.ingsw.model.PlayerId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cell {
    private Map<CardinalDirection, Connection> connection;
    private Color color;
    private ArrayList<PlayerId> hostedPlayers;
    private ArrayList<Object> cards;

    public Cell(Connection northConnection, Connection eastConnection, Connection southConnection, Connection westConnection, Color color) {
        connection = new HashMap<CardinalDirection, Connection>() {{
            put(CardinalDirection.NORTH, northConnection);
            put(CardinalDirection.EAST, eastConnection);
            put(CardinalDirection.SOUTH, southConnection);
            put(CardinalDirection.WEST, westConnection);
        }};
        this.color = color;
        hostedPlayers = new ArrayList<>();
        cards = new ArrayList<>();
    }

    public void addPlayer(PlayerId player){
        hostedPlayers.add(player);
    }

    public void removePlayer(PlayerId player){
        hostedPlayers.remove(player);
    }

    public ArrayList<PlayerId> getHostedPlayer(){
        return hostedPlayers;
    }

    public Color getColor() {
        return color;
    }

    public Connection getConnection(CardinalDirection dir) {
        return connection.get(dir);
    }
}
