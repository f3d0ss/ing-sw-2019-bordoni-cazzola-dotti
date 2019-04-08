package it.polimi.ingsw.model;

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
    private boolean loaded;
    private boolean selectedFirstOptionalFireMode;
    private boolean selectedSecondOptionalFireMode;
    private boolean selectedAlternativeFireMode;

    public Weapon(String name, Map<Color, Integer> reloadingCost, Map<Color, Integer> firstOptionalModeCost, Map<Color, Integer> secondOptionalModeCost, Map<Color, Integer> alternativeFireModeCost) {
        this.name = name;
        this.reloadingCost = reloadingCost;
        this.firstOptionalModeCost = firstOptionalModeCost;
        this.secondOptionalModeCost = secondOptionalModeCost;
        this.alternativeFireModeCost = alternativeFireModeCost;
        loaded = true;
        selectedFirstOptionalFireMode = false;
        selectedSecondOptionalFireMode = false;
        selectedAlternativeFireMode = false;
    }

    public abstract List<WeaponCommand> getPossibleCommands(Gameboard gameboard, Player player);

    public abstract List<SelectWeaponOptionCommand> getSelectOptionCommands();

    public abstract void addTargetPlayer(Player targetPlayer);

    public abstract void addTargetSquare(Square targetSquare);

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
}
