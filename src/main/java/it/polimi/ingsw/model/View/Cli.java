package it.polimi.ingsw.model.View;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.playerstate.PlayerState;

public class Cli {

    private final static int INNERWIDTH = 11;
    private Match match;
    private PlayerState state;

    public void setMatch(Match match) {
        this.match = match;
    }

    public void displayBoard() {
        GameBoard board = match.getBoard();
        int squareHeight = 5;
        for (int i = 0; i < board.getHeight(); i++)
            for (int k = 0; k < squareHeight; k++) {
                for (int j = 0; j < board.getWidth(); j++) {
                    displaySquare(board.getSquare(i, j), k);
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
                System.out.printf(String.format("%-" + INNERWIDTH + "s", getSquareInformation(square, squareRow)).substring(0, INNERWIDTH));
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
/*
    private String displaySquareInformation(Square square, int row) {
        switch (row) {
            case 1:
                System.out.printf(" Players:  ");
                break;
            case 2:
                if (square.getClass() == SpawnSquare.class)
                    System.out.printf(" Spawn:    ");
                else
                    System.out.printf(" Ammos:    ");
                break;
            case 3:
                for (int i = 0; i < INNERWIDTH; i++)
                    System.out.printf(" ");
                break;
        }
    }*/

    private String getSquareInformation(Square square, int row) {
        switch (row) {
            case 2:
                String out = new String();
                //square.getHostedPlayers().forEach(player -> System.out.printf(player.getId().playerIdName().substring(0,1)));
                for (Player p : square.getHostedPlayers())
                    out = out + " " + p.getId().playerIdName().substring(0, 1);
                return out;
            case 1:
                if (square.getClass() == SpawnSquare.class)
                    for (Color c : Color.values())
                        if (match.getBoard().getSpawn(c) == square)
                            return " " + c.colorName() + "Spawn";
                return " Ammo: RYB";
        }
        return " ";
    }

    //TODO: implement SpawnSquare.getWeapons

    private void displayRightSideInformation(int row) {
        switch (row) {
            case 1:
                System.out.printf("BlueSpawn weapons:");
                //match.getBoard().getSpawn(Color.BLUE);
                //Weapon weapon = match.getBoard().getSpawn(Color.BLUE).getWeapons;
                //weapon.forEach(weapon w -> );
                //System.out.println("BlueSpawn weapon 1: " + match.getBoard().getSpawn(Color.BLUE).getWeapon(0));
                break;
            case 2:
                System.out.printf("RedSpawn weapons: weapon1 - weapon2 - weapon3 ");
                break;
            case 3:
                System.out.printf("YellowSpawn weapons: weapon1 - weapon2 - weapon3 ");
                break;
            case 5:
                System.out.printf("Info: ");
                break;
            case 6:
                System.out.printf("Info: ");
                break;
            case 7:
                System.out.printf("Info: ");
                break;
            case 10:
                System.out.printf("Info: ");
                break;
            case 11:
                System.out.printf("Info: ");
                break;
            case 12:
                System.out.printf("Info: ");
                break;
        }
    }
}
