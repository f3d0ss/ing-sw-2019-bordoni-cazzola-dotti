package it.polimi.ingsw.model;

import it.polimi.ingsw.model.command.SelectWeaponOptionCommand;
import it.polimi.ingsw.model.command.WeaponCommand;
import it.polimi.ingsw.model.playerstate.ChoosingWeaponOptionState;

import java.util.List;
import java.util.Map;

/**
 * This abstract class represents a weapon
 */
public abstract class Weapon {
    private String name;
    private Map<Color, Integer> reloadingCost;
    private Map<Color, Integer> firstOptionalModeCost;
    private Map<Color, Integer> secondOptionalModeCost;
    private Map<Color, Integer> alternativeFireModeCost;
    private boolean loaded = true;
    private boolean selectedFirstOptionalFireMode = false;
    private boolean selectedSecondOptionalFireMode = false;
    private boolean selectedAlternativeFireMode = false;
    private int extraMove = 0;
    private Color color;

    public Weapon(String name, Map<Color, Integer> reloadingCost, Map<Color, Integer> firstOptionalModeCost, Map<Color, Integer> secondOptionalModeCost, Map<Color, Integer> alternativeFireModeCost) {
        this.name = name;
        this.reloadingCost = reloadingCost;
        this.firstOptionalModeCost = firstOptionalModeCost;
        this.secondOptionalModeCost = secondOptionalModeCost;
        this.alternativeFireModeCost = alternativeFireModeCost;
    }

    public Weapon() {
    }

    public abstract List<WeaponCommand> getPossibleCommands(GameBoard gameboard, Player player);

    public abstract List<SelectWeaponOptionCommand> getSelectOptionCommands(Player player, ChoosingWeaponOptionState state);

    public abstract void addTargetPlayer(Player targetPlayer);

    public abstract void addTargetSquare(Square targetSquare);

    public abstract Map<Color, Integer> getWeaponBuyCost();

    public void setSelectedFirstOptionalFireMode(boolean selectedFirstOptionalFireMode) {
        this.selectedFirstOptionalFireMode = selectedFirstOptionalFireMode;
    }

    public void setSelectedSecondOptionalFireMode(boolean selectedSecondOptionalFireMode) {
        this.selectedSecondOptionalFireMode = selectedSecondOptionalFireMode;
    }

    public void setSelectedAlternativeFireMode(boolean selectedAlternativeFireMode) {
        this.selectedAlternativeFireMode = selectedAlternativeFireMode;
    }

    public Map<Color, Integer> getFirstOptionalModeCost() {
        return null;
    }

    public Map<Color, Integer> getSecondOptionalModeCost() {
        return null;
    }

    public Map<Color, Integer> getAlternativeFireModeCost() {
        return null;
    }

    protected String getName() {
        return name;
    }

    public Map<Color, Integer> getReloadingCost() {
        return reloadingCost;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public int getExtraMove() {
        return extraMove;
    }

    protected Color getColor() {
        return color;
    }

    public abstract boolean hasExtraMove();

    public abstract void useExtraMoves();

    public abstract void resetMoves();

    public abstract void reload();

    public abstract void unload();

    public abstract void removeTargetPlayer(Player targetPlayer);

    public abstract void removeTargetSquare(Square targetSquare);
}
