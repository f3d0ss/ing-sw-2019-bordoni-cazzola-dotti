package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.*;

import java.util.List;
import java.util.Map;

public class CliManager {

    private final static int INNERWIDTH = 11;
    private final static String VERTICAL_WALL = "║";
    private final static String HORIZONTAL_WALL = "═";
    private final static String CORNER_BOTTOM_RIGHT = "╝";
    private final static String CORNER_BOTTOM_LEFT = "╚";
    private final static String CORNER_TOP_RIGHT = "╗";
    private final static String CORNER_TOP_LEFT = "╔";
    private final static int SQUARE_HEIGHT = 5;

    public void displayAll(SquareView[][] board, MatchView match, PlayerView me, Map<PlayerId, PlayerView> enemies) {
        int width = board[0].length;
        int height = board.length/width;
        for (int i = 0; i < height; i++)
            for (int k = 0; k < SQUARE_HEIGHT; k++) {
                for (int j = 0; j < width; j++) {
                    displaySquare(board[i][j], k);
                    System.out.printf(" ");
                }
                System.out.printf(" ");
                displayRightSideInformation(i * SQUARE_HEIGHT + k, match, me);
                System.out.printf("%n");
            }
        for (PlayerView player : enemies.values())
            displayEnemiesInformation(player);
    }

    private void displaySquare(SquareView square, int squareRow) {
        boolean isMiddle = false;
        if (square == null) {
            for (int i = 0; i < INNERWIDTH + 2; i++)
                System.out.printf(" ");
            return;
        }
        switch (squareRow) {
            case 0:
                displayTopHorizontalConnection(square.getConnection(CardinalDirection.NORTH));
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
                displayBottomHorizontalConnection(square.getConnection(CardinalDirection.SOUTH));
                break;
        }
    }

    private void displayTopHorizontalConnection(Connection side) {
        System.out.printf(CORNER_TOP_LEFT);
        displayHorizontalConnection(side);
        System.out.printf(CORNER_TOP_RIGHT);
    }

    private void displayBottomHorizontalConnection(Connection side) {
        System.out.printf(CORNER_BOTTOM_LEFT);
        displayHorizontalConnection(side);
        System.out.printf(CORNER_BOTTOM_RIGHT);
    }

    private void displayHorizontalConnection(Connection side) {
        for (int i = 0; i < INNERWIDTH; i++)
            switch (side) {
                case WALL:
                    System.out.printf(HORIZONTAL_WALL);
                    break;
                case MAP_BORDER:
                    System.out.printf(HORIZONTAL_WALL);
                    break;
                case SAME_ROOM:
                    System.out.printf(" ");
                    break;
                case DOOR:
                    if (i < INNERWIDTH / 2 - 1 || i > INNERWIDTH / 2 + 1)
                        System.out.printf(HORIZONTAL_WALL);
                    else
                        System.out.printf(" ");
            }
    }

    private void displayVerticalConnection(Connection side, boolean door) {
        switch (side) {
            case WALL:
            case MAP_BORDER:
                System.out.printf(VERTICAL_WALL);
                break;
            case SAME_ROOM:
                System.out.printf(" ");
                break;
            case DOOR:
                if (door)
                    System.out.printf(" ");
                else
                    System.out.printf(VERTICAL_WALL);
        }
    }

    private String displaySquareInformation(SquareView square, int row) {
        switch (row) {
            case 1:
                if (square.getColor() != null)
                    return " " + square.getColor().colorName() + "Spawn";
                TurretSquareView turret = (TurretSquareView) square;
                return " Ammo: " + turret.getAmmoTile().toString();
            case 2:
                String out = "";
                for (PlayerId p : square.getHostedPlayers())
                    out = out + " " + p.playerIdName().substring(0, 1);
                return out;
        }
        return " ";
    }

    //TODO: implement SpawnSquare.getWeapons

    private void displayRightSideInformation(int row, MatchView match, PlayerView me) {
        Map<Color,List<WeaponView>> weaponsOnSpawn = match.getWeaponsOnSpawn();
        switch (row) {
            case 1:
                System.out.printf("BlueSpawn weapons: ");
                weaponsOnSpawn.get(Color.BLUE).forEach(weapon -> System.out.printf(weapon.toString() + "; "));
                break;
            case 2:
                System.out.printf("RedSpawn weapons: ");
                weaponsOnSpawn.get(Color.RED).forEach(weapon -> System.out.printf(weapon.toString() + "; "));
                break;
            case 3:
                System.out.printf("YellowSpawn weapons: ");
                weaponsOnSpawn.get(Color.YELLOW).forEach(weapon -> System.out.printf(weapon.toString() + "; "));
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
                System.out.printf(me.getId().playerIdName().toUpperCase() + " (" + me.getNickname() + ")");
                break;
            case 8:
                System.out.printf("Dead " + me.getDeaths() + " times");
                break;
            case 9:
                System.out.printf("Weapons: ");
                me.getWeapons().forEach(weapon -> System.out.printf(weapon.toString() + (weapon.isLoaded() ? "; " : "(UNLOADED), ")));
                break;
            case 10:
                System.out.printf("Powerups: ");
                me.getPowerUps().forEach(powerUp -> System.out.printf(powerUp.toString() + " "));
                break;
            case 11:
                System.out.printf("Ammos: ");
                me.getAmmo().forEach((color, value) -> System.out.printf(value + " " + color.colorName() + "; "));
                break;
            case 12:
                System.out.printf("Damages: ");
                me.getHealth().forEach(id -> System.out.printf(id.playerIdName().substring(0, 1)));
                //TODO: how remove hardcoded information about adrenalinic actions?
                if (me.getHealth().size() > 2)
                    if (me.getHealth().size() > 5)
                        System.out.printf(" (azioni adrenaliniche lv 2 sbloccate)");
                    else
                        System.out.printf(" (azioni adrenaliniche lv 1 sbloccate)");
                else
                    System.out.printf(" (azioni adrenaliniche bloccate)");
                break;
            case 13:
                System.out.printf("Marks: ");
                me.getMarks().forEach((id, n) -> System.out.printf(n + " from " + id.playerIdName() + "; "));
        }
    }

    private void displayEnemiesInformation(PlayerView enemy) {
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

    public void displayMessage(String message){
        System.out.println(message);
    }
}
