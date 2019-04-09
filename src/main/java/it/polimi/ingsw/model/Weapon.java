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
    private boolean selectedFirstOptionalFireMode;
    private boolean selectedSecondOptionalFireMode;
    private boolean selectedAlternativeFireMode;
    private int extraMove = 0;

    public Weapon(String name, Map<Color, Integer> reloadingCost, Map<Color, Integer> firstOptionalModeCost, Map<Color, Integer> secondOptionalModeCost, Map<Color, Integer> alternativeFireModeCost) {
        this.name = name;
        this.reloadingCost = reloadingCost;
        this.firstOptionalModeCost = firstOptionalModeCost;
        this.secondOptionalModeCost = secondOptionalModeCost;
        this.alternativeFireModeCost = alternativeFireModeCost;
        this.loaded = true;
        this.selectedFirstOptionalFireMode = false;
        this.selectedSecondOptionalFireMode = false;
        this.selectedAlternativeFireMode = false;
    }

    public Weapon() {
    }

    public abstract List<WeaponCommand> getPossibleCommands(GameBoard gameboard, Player player);

    public abstract List<SelectWeaponOptionCommand> getSelectOptionCommands(Player player, ChoosingWeaponOptionState state);

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

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected Map<Color, Integer> getReloadingCost() {
        return reloadingCost;
    }

    protected void setReloadingCost(Map<Color, Integer> reloadingCost) {
        this.reloadingCost = reloadingCost;
    }

    protected void setFirstOptionalModeCost(Map<Color, Integer> firstOptionalModeCost) {
        this.firstOptionalModeCost = firstOptionalModeCost;
    }

    protected void setSecondOptionalModeCost(Map<Color, Integer> secondOptionalModeCost) {
        this.secondOptionalModeCost = secondOptionalModeCost;
    }

    protected void setAlternativeFireModeCost(Map<Color, Integer> alternativeFireModeCost) {
        this.alternativeFireModeCost = alternativeFireModeCost;
    }

    protected boolean isLoaded() {
        return loaded;
    }

    protected void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    protected boolean isSelectedFirstOptionalFireMode() {
        return selectedFirstOptionalFireMode;
    }

    protected boolean isSelectedSecondOptionalFireMode() {
        return selectedSecondOptionalFireMode;
    }

    protected boolean isSelectedAlternativeFireMode() {
        return selectedAlternativeFireMode;
    }

    protected int getExtraMove() {
        return extraMove;
    }

    protected setExtraMove(int extraMove) {
        this.extraMove = extraMove;
    }
}
