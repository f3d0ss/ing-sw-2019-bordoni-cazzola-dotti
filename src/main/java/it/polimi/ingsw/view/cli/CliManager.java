package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.CardinalDirection;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Connection;
import it.polimi.ingsw.model.PlayerId;
import it.polimi.ingsw.view.PlayerView;
import it.polimi.ingsw.view.SquareView;
import it.polimi.ingsw.view.TurretSquareView;
import it.polimi.ingsw.view.*;

public class CliManager {

    private final static int INNERWIDTH = 11;
    private final static String VERTICAL_WALL = "║";
    private final static String HORIZONTAL_WALL = "═";
    private final static String CORNER_BOTTOM_RIGHT = "╝";
    private final static String CORNER_BOTTOM_LEFT = "╚";
    private final static String CORNER_TOP_RIGHT = "╗";
    private final static String CORNER_TOP_LEFT = "╔";
    private final static int SQUARE_HEIGHT = 5;

    public void displayAll(ModelView modelView) {
        for (int i = 0; i < ModelView.HEIGHT; i++)
            for (int k = 0; k < SQUARE_HEIGHT; k++) {
                for (int j = 0; j < ModelView.WIDTH; j++) {
                    displaySquare(modelView.getSquareBoard(i, j), k);
                    System.out.printf(" ");
                }
                System.out.printf(" ");
                displayRightSideInformation(i * SQUARE_HEIGHT + k, modelView);
                System.out.printf("%n");
            }
        for (PlayerView player : modelView.getEnemies().values())
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

    private void displayRightSideInformation(int row, ModelView modelView) {
        switch (row) {
            case 1:
                System.out.printf("BlueSpawn weapons: ");
                modelView.getWeaponsOnSpawn(Color.BLUE).forEach(weapon -> System.out.printf(weapon.getName() + "; "));
                break;
            case 2:
                System.out.printf("RedSpawn weapons: ");
                modelView.getWeaponsOnSpawn(Color.RED).forEach(weapon -> System.out.printf(weapon.getName() + "; "));
                break;
            case 3:
                System.out.printf("YellowSpawn weapons: ");
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
                modelView.getMe().getWeapons().forEach(weapon -> System.out.printf(weapon.getName() + (weapon.isLoaded() ? "; " : "(UNLOADED), ")));
                break;
            case 10:
                System.out.printf("Powerups: ");
                modelView.getMe().getPowerUps().forEach(powerUp -> System.out.printf(powerUp.getName() + " "));
                break;
            case 11:
                System.out.printf("Ammos: ");
                modelView.getMe().getAmmo().forEach((color, value) -> System.out.printf(value + " " + color.colorName() + "; "));
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
        System.out.printf("\n" + enemy.getId().playerIdName().toUpperCase() + " (" + enemy.getNickname() + ") has " + enemy.getPowerUps().size() + " poweups.");
        System.out.printf(" Ammos: ");
        enemy.getAmmo().forEach((color, value) -> System.out.printf(value + " " + color.colorName() + "; "));
        System.out.printf("\n     Weapons: ");
        enemy.getWeapons().forEach(weapon -> System.out.printf(weapon.isLoaded() ? "XXXXXX; " : weapon.getName() + "; "));
        System.out.printf("\n     Damages: ");
        enemy.getHealth().forEach(id -> System.out.printf(id.playerIdName().substring(0, 1) + " "));
        System.out.printf("Marks: ");
        enemy.getMarks().forEach((id, n) -> System.out.printf(n + " from " + id.playerIdName() + " "));
        System.out.println("(dead " + enemy.getDeaths() + " times)");
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}
