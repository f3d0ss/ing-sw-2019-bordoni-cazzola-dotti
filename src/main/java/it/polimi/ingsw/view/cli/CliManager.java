package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.CardinalDirection;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Connection;
import it.polimi.ingsw.model.PlayerId;
import it.polimi.ingsw.view.ModelView;
import it.polimi.ingsw.view.PlayerView;
import it.polimi.ingsw.view.SquareView;
import it.polimi.ingsw.view.TurretSquareView;

public class CliManager {

    private static final int INNERWIDTH = 11;
    private static final int SEPARATOR_LENGTH = 100;
    private static final String SEPARATOR = "█";
    private static final String VERTICAL_WALL = "║";
    private static final String HORIZONTAL_WALL = "═";
    private static final String CORNER_BOTTOM_RIGHT = "╝";
    private static final String CORNER_BOTTOM_LEFT = "╚";
    private static final String CORNER_TOP_RIGHT = "╗";
    private static final String CORNER_TOP_LEFT = "╔";
    private static final String SPACE = " ";
    private static final int SQUARE_HEIGHT = 5;
    private static final int ASCII_A_CODE = 65;
    private static final int FIRST_ROW_NUMBER = 1;
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[91m";
    private static final String ANSI_YELLOW = "\u001B[93m";
    private static final String ANSI_BLUE = "\u001B[94m";

    public void displayAll(ModelView modelView) {
        for (int i = 0; i < SEPARATOR_LENGTH; i++)
            System.out.printf(SEPARATOR);
        System.out.println(SEPARATOR);
        for (int i = 0; i < ModelView.HEIGHT; i++)
            for (int k = 0; k < SQUARE_HEIGHT; k++) {
                for (int j = 0; j < ModelView.WIDTH; j++) {
                    displaySquare(modelView.getSquareBoard(i, j), k);
                }
                System.out.printf(SPACE);
                displayRightSideInformation(i * SQUARE_HEIGHT + k, modelView);
                System.out.printf("%n");
            }
        for (PlayerView player : modelView.getEnemies().values())
            displayEnemiesInformation(player);
    }

    private void displaySquare(SquareView square, int printingRow) {
        boolean isMiddle = false;
        if (square == null) {
            for (int i = 0; i < INNERWIDTH + 2; i++)
                System.out.printf(SPACE);
            return;
        }
        switch (printingRow) {
            case 0:
                displayTopHorizontalConnection(square);
                break;
            case 2:
                isMiddle = true;
            case 1:
            case 3:
                displayVerticalConnection(square.getConnection(CardinalDirection.WEST), isMiddle, square.getRow());
                System.out.printf(String.format(displaySquareInformation(square, printingRow)));
                displayVerticalConnection(square.getConnection(CardinalDirection.EAST), isMiddle, square.getRow());
                System.out.printf(SPACE);
                break;
            case 4:
                displayBottomHorizontalConnection(square);
                break;
        }
    }

    private void displayTopHorizontalConnection(SquareView square) {
        displayCorner(square.getConnection(CardinalDirection.WEST), square.getConnection(CardinalDirection.NORTH), CORNER_TOP_LEFT);
        displayHorizontalConnection(square.getConnection(CardinalDirection.NORTH), square.getCol());
        displayCorner(square.getConnection(CardinalDirection.EAST), square.getConnection(CardinalDirection.NORTH), CORNER_TOP_RIGHT);
        displayAdditionalSpace(square.getConnection(CardinalDirection.EAST), square.getConnection(CardinalDirection.NORTH));
    }

    private void displayBottomHorizontalConnection(SquareView square) {
        displayCorner(square.getConnection(CardinalDirection.WEST), square.getConnection(CardinalDirection.SOUTH), CORNER_BOTTOM_LEFT);
        displayHorizontalConnection(square.getConnection(CardinalDirection.SOUTH), square.getCol());
        displayCorner(square.getConnection(CardinalDirection.EAST), square.getConnection(CardinalDirection.SOUTH), CORNER_BOTTOM_RIGHT);
        displayAdditionalSpace(square.getConnection(CardinalDirection.EAST), square.getConnection(CardinalDirection.SOUTH));
    }

    private void displayCorner(Connection vertical, Connection horizontal, String corner) {
        if (vertical == Connection.SAME_ROOM) {
            if (horizontal == Connection.SAME_ROOM) System.out.printf(SPACE);
            else System.out.printf(HORIZONTAL_WALL);
        } else {
            if (horizontal == Connection.SAME_ROOM) System.out.printf(VERTICAL_WALL);
            else System.out.printf(corner);
        }
    }

    private void displayAdditionalSpace(Connection vertical, Connection horizontal) {
        System.out.printf((vertical == Connection.SAME_ROOM && horizontal != Connection.SAME_ROOM) ? HORIZONTAL_WALL : SPACE);
    }

    private void displayHorizontalConnection(Connection side, int column) {
        for (int i = 0; i < INNERWIDTH; i++)
            switch (side) {
                case WALL:
                    System.out.printf(HORIZONTAL_WALL);
                    break;
                case MAP_BORDER:
                    System.out.printf((i < INNERWIDTH / 2 || i > INNERWIDTH / 2) ? HORIZONTAL_WALL : getHorizontalCoordinateName(column));
                    break;
                case SAME_ROOM:
                    System.out.printf(SPACE);
                    break;
                case DOOR:
                    System.out.printf((i < INNERWIDTH / 2 - 1 || i > INNERWIDTH / 2 + 1) ? HORIZONTAL_WALL : SPACE);
            }
    }

    private void displayVerticalConnection(Connection side, boolean isMiddle, int row) {
        switch (side) {
            case WALL:
                System.out.printf(VERTICAL_WALL);
                break;
            case MAP_BORDER:
                System.out.printf(isMiddle ? getVerticalCoordinateName(row) : VERTICAL_WALL);
                break;
            case SAME_ROOM:
                System.out.printf(SPACE);
                break;
            case DOOR:
                System.out.printf(isMiddle ? SPACE : VERTICAL_WALL);
        }
    }

    public String getHorizontalCoordinateName(int column) {
        return "" + (char) (column + ASCII_A_CODE);
    }

    public String getVerticalCoordinateName(int column) {
        return String.valueOf(column + FIRST_ROW_NUMBER);
    }


    private String displaySquareInformation(SquareView square, int row) {
        switch (row) {
            case 1:
                if (square.getColor() != null)
                    return " " + setDisplayColored(square.getColor()) + square.getColor().colorName().substring(0, 3) + "Spawn  " + ANSI_RESET;
                TurretSquareView turret = (TurretSquareView) square;
                return " Ammo: " + displayColoredAmmoTile(turret.getAmmoTile().toString()) + " ";
            case 2:
                String out = "";
                for (PlayerId p : square.getHostedPlayers())
                    out = out + " " + p.playerIdName().substring(0, 1);
                return String.format("%-" + INNERWIDTH + "s", out).substring(0, INNERWIDTH);
        }
        return String.format("%-" + INNERWIDTH + "s", " ").substring(0, INNERWIDTH);
    }

    //TODO: implement SpawnSquare.getWeapons

    private void displayRightSideInformation(int row, ModelView modelView) {
        switch (row) {
            case 1:
                System.out.printf(ANSI_BLUE + "BlueSpawn weapons: " + ANSI_RESET);
                modelView.getWeaponsOnSpawn(Color.BLUE).forEach(weapon -> System.out.printf(weapon.getName() + "; "));
                break;
            case 2:
                System.out.printf(ANSI_RED + "RedSpawn weapons: " + ANSI_RESET);
                modelView.getWeaponsOnSpawn(Color.RED).forEach(weapon -> System.out.printf(weapon.getName() + "; "));
                break;
            case 3:
                System.out.printf(ANSI_YELLOW + "YellowSpawn weapons: " + ANSI_RESET);
                modelView.getWeaponsOnSpawn(Color.YELLOW).forEach(weapon -> System.out.printf(weapon.getName() + "; "));
                break;
            case 4:
                //System.out.printf("Skulls: " + match.getDeathsCounter());
                break;
            case 5:
                System.out.printf("Killshots: ");
                modelView.getMatch().getKillshotTrack().forEach(id -> System.out.printf(id.playerIdName().substring(0, 1) + " "));
                System.out.printf("(" + modelView.getMatch().getDeathsCounter() + " skulls left)");
                break;
            case 7:
                System.out.printf(modelView.getMe().getId().playerIdName().toUpperCase() + " (" + modelView.getMe().getNickname() + ")");
                break;
            case 8:
                System.out.printf("Dead " + modelView.getMe().getDeaths() + " times");
                break;
            case 9:
                System.out.printf("Weapons: ");
                modelView.getMe().getWeapons().forEach(weapon -> System.out.printf(weapon.getName() + (weapon.isLoaded() ? "; " : " (UNLOADED); ")));
                break;
            case 10:
                System.out.printf("Powerups: ");
                modelView.getMe().getPowerUps().forEach(powerUp -> System.out.printf(setDisplayColored(powerUp.getColor()) + powerUp.getName() + " " + powerUp.getColor().colorName() + ANSI_RESET + "; "));
                break;
            case 11:
                System.out.printf("Ammos: ");
                modelView.getMe().getAmmo().forEach((color, value) -> {
                    if (value > 0)
                        System.out.printf(setDisplayColored(color) + value + " " + color.colorName() + ANSI_RESET + "; ");
                });
                break;
            case 12:
                System.out.printf("Damages: ");
                modelView.getMe().getHealth().forEach(id -> System.out.printf(id.playerIdName().substring(0, 1)));
                //TODO: how remove hardcoded information about adrenalinic actions?
                if (modelView.getMe().getHealth().size() > 2)
                    if (modelView.getMe().getHealth().size() > 5)
                        System.out.printf(" (azioni adrenaliniche lv 2 sbloccate)");
                    else
                        System.out.printf(" (azioni adrenaliniche lv 1 sbloccate)");
                else
                    System.out.printf(" (azioni adrenaliniche bloccate)");
                break;
            case 13:
                System.out.printf("Marks: ");
                modelView.getMe().getMarks().forEach((id, n) -> System.out.printf(n + " from " + id.playerIdName() + "; "));
        }
    }

    private void displayEnemiesInformation(PlayerView enemy) {
        System.out.printf("\n" + enemy.getId().playerIdName().toUpperCase() + " (" + enemy.getNickname() + (enemy.isDisconnected() ? " - DISCONNESSO" : "") + ") has " + enemy.getPowerUps().size() + " poweups.");
        System.out.printf(" Ammos: ");
        enemy.getAmmo().forEach((color, value) -> {
            if (value > 0)
                System.out.printf(setDisplayColored(color) + value + " " + color.colorName() + ANSI_RESET + "; ");
        });
        System.out.printf("\n     Weapons: ");
        enemy.getWeapons().forEach(weapon -> System.out.printf(weapon.isLoaded() ? "XXXXXX; " : weapon.getName() + "; "));
        System.out.printf("\n     Damages: ");
        enemy.getHealth().forEach(id -> System.out.printf(id.playerIdName().substring(0, 1) + " "));
        System.out.printf("Marks: ");
        enemy.getMarks().forEach((id, n) -> System.out.printf(n + " from " + id.playerIdName() + " "));
        System.out.println("(dead " + enemy.getDeaths() + " times)");
    }

    private String setDisplayColored(Color color) {
        switch (color) {
            case BLUE:
                return ANSI_BLUE;
            case RED:
                return ANSI_RED;
            case YELLOW:
                return ANSI_YELLOW;
            default:
                return ANSI_RESET;
        }
    }

    private String displayColoredAmmoTile(String ammoTile) {
        String out = "";
        for (int i = 0; i < ammoTile.length(); i++) {
            if (ammoTile.substring(i, i + 1).equals(Color.BLUE.colorName().substring(0, 1)))
                out = out + ANSI_BLUE + ammoTile.substring(i, i + 1) + ANSI_RESET;
            else if (ammoTile.substring(i, i + 1).equals(Color.RED.colorName().substring(0, 1)))
                out = out + ANSI_RED + ammoTile.substring(i, i + 1) + ANSI_RESET;
            else if (ammoTile.substring(i, i + 1).equals(Color.YELLOW.colorName().substring(0, 1)))
                out = out + ANSI_YELLOW + ammoTile.substring(i, i + 1) + ANSI_RESET;
            else out = out + ammoTile.substring(i, i + 1);
        }
        return out;
    }
}
