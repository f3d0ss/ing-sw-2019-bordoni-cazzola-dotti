package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;

import java.util.Observable;
import java.util.Observer;

public class Cli implements Observer {

    private final static int INNERWIDTH = 11;
    private int height;
    private int width;
    private Match match;
    private PlayerId client;

    public Cli(int height, int width, Match match, PlayerId client) {
        this.height = height;
        this.width = width;
        this.match = match;
        this.client = client;
    }

    private void displayBoard() {
        int squareHeight = 5;
        for (int i = 0; i < height; i++)
            for (int k = 0; k < squareHeight; k++) {
                for (int j = 0; j < width; j++) {
                    displaySquare(match.getBoard().getSquare(i, j), k);
                    System.out.printf(" ");
                }
                System.out.printf(" ");
                displayRightSideInformation(i * squareHeight + k);
                System.out.printf("%n");
            }
    }

    private void displaySquare(Square square, int squareRow) {
        boolean isMiddle = false;
        if (square == null) {
            for (int i = 0; i < INNERWIDTH + 2; i++)
                System.out.printf(" ");
            return;
        }
        switch (squareRow) {
            case 0:
                displayHorizontalConnection(square.getConnection(CardinalDirection.NORTH));
                break;
            case 2:
                isMiddle = true;
            case 1:
            case 3:
                displayVerticalConnection(square.getConnection(CardinalDirection.WEST), isMiddle);
                System.out.printf(String.format("%-" + INNERWIDTH + "s", displaySquareInformation(square, squareRow)).substring(0, INNERWIDTH));
                displayVerticalConnection(square.getConnection(CardinalDirection.EAST), isMiddle);
                break;
            case 4:
                displayHorizontalConnection(square.getConnection(CardinalDirection.SOUTH));
                break;
        }
    }

    private void displayHorizontalConnection(Connection side) {
        System.out.printf("+");
        for (int i = 0; i < INNERWIDTH; i++)
            switch (side) {
                case WALL:
                    System.out.printf("-");
                    break;
                case MAP_BORDER:
                    System.out.printf("=");
                    break;
                case SAME_ROOM:
                    System.out.printf(" ");
                    break;
                case DOOR:
                    if (i < INNERWIDTH / 2 - 1 || i > INNERWIDTH / 2 + 1)
                        System.out.printf("-");
                    else
                        System.out.printf(" ");
            }
        System.out.printf("+");
    }

    private void displayVerticalConnection(Connection side, boolean door) {
        switch (side) {
            case WALL:
                System.out.printf("|");
                break;
            case MAP_BORDER:
                System.out.printf("H");
                break;
            case SAME_ROOM:
                System.out.printf(" ");
                break;
            case DOOR:
                if (door)
                    System.out.printf(" ");
                else
                    System.out.printf("|");
        }
    }

    private String displaySquareInformation(Square square, int row) {
        switch (row) {
            case 1:
                if (square.getColor() != null)
                    return " " + square.getColor().colorName() + "Spawn";
                TurretSquare turret = (TurretSquare) square;
                return " Ammo: " + turret.getAmmoTile().toString();
            case 2:
                String out = "";
                for (Player p : square.getHostedPlayers())
                    out = out + " " + p.getId().playerIdName().substring(0, 1);
                return out;
        }
        return " ";
    }

    //TODO: implement SpawnSquare.getWeapons

    private void displayRightSideInformation(int row) {
        switch (row) {
            case 1:
                System.out.printf("BlueSpawn weapons: ");
                match.getBoard().getSpawn(Color.BLUE).getWeapons().forEach(weapon -> System.out.printf(weapon.toString() + ", "));
                break;
            case 2:
                System.out.printf("RedSpawn weapons: ");
                match.getBoard().getSpawn(Color.RED).getWeapons().forEach(weapon -> System.out.printf(weapon.toString() + ", "));
                break;
            case 3:
                System.out.printf("YellowSpawn weapons: ");
                match.getBoard().getSpawn(Color.YELLOW).getWeapons().forEach(weapon -> System.out.printf(weapon.toString() + ", "));
                break;
            case 4:
                //System.out.printf("Skulls: " + match.getDeathsCounter());
                break;
            case 5:
                System.out.printf("Killshots: ");
                match.getKillshotTrack().forEach(id -> System.out.printf(id.playerIdName().substring(0, 1) + " "));
                System.out.printf("(" + match.getDeathsCounter() + " skulls left)");
                break;
            case 7:
                System.out.printf(client.playerIdName().toUpperCase() + " (" + match.getPlayer(client).toString() + ")");
                break;
            case 8:
                System.out.printf("Dead " + match.getPlayer(client).getDeaths() + " times");
                break;
            case 9:
                System.out.printf("Weapons: ");
                match.getPlayer(client).getWeapons().forEach(weapon -> System.out.printf(weapon.toString() + (weapon.isLoaded() ? ", " : "(UNLOADED), ")));
                break;
            case 10:
                System.out.printf("Powerups: ");
                match.getPlayer(client).getPowerUps().forEach(powerUp -> System.out.printf(powerUp.toString() + " "));
                break;
            case 11:
                System.out.printf("Ammos: ");
                match.getPlayer(client).getAmmo().forEach((color, value) -> System.out.printf(value + " " + color.colorName() + ", "));
                break;
            case 12:
                System.out.printf("Damages: ");
                match.getPlayer(client).getHealth().forEach(id -> System.out.printf(id.playerIdName().substring(0, 1)));
                if (match.getPlayer(client).getHealth().size() > 2)
                    if (match.getPlayer(client).getHealth().size() > 5)
                        System.out.printf(" (azioni adrenaliniche lv 2 sbloccate)");
                    else
                        System.out.printf(" (azioni adrenaliniche lv 1 sbloccate)");
                else
                    System.out.printf(" (azioni adrenaliniche bloccate)");
                break;
            case 13:
                System.out.printf("Marks: ");
                match.getPlayer(client).getMarks().forEach((id, n) -> System.out.printf(n + " from " + id.playerIdName() + ", "));
        }
    }

    private void displayEnemiesInformation(Player enemy) {
        System.out.printf("\n" + enemy.getId().playerIdName().toUpperCase() + " (" + enemy.toString() + ") has " + enemy.getPowerUps().size() + " poweups.");
        System.out.printf(" Ammos: ");
        enemy.getAmmo().forEach((color, value) -> System.out.printf(value + " " + color.colorName() + ", "));
        System.out.printf("\n     Weapons: ");
        enemy.getWeapons().forEach(weapon -> System.out.printf(weapon.isLoaded() ? "XXXXXX, " : weapon.toString() + ", "));
        System.out.printf("\n     Damages: ");
        enemy.getHealth().forEach(id -> System.out.printf(id.playerIdName().substring(0, 1) + " "));
        System.out.printf("Marks: ");
        enemy.getMarks().forEach((id, n) -> System.out.printf(n + " from " + id.playerIdName() + " "));
        System.out.printf("(dead " + enemy.getDeaths() + " times)");
    }

    public void update(String message) {
        System.out.println(message);
        displayBoard();
        for (Player player : match.getCurrentPlayers())
            if (player != match.getPlayer(client))
                displayEnemiesInformation(player);
    }

    @Override
    public void update(Observable observable, Object o) {
        //TODO
    }

}
