package it.polimi.ingsw.model.weapons;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.command.SelectWeaponFirstOptionCommand;
import it.polimi.ingsw.model.command.SelectWeaponOptionCommand;
import it.polimi.ingsw.model.command.WeaponCommand;
import it.polimi.ingsw.model.playerstate.ChoosingWeaponOptionState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LockRifle extends Weapon {

    /**
     * Hardcoded constructor
     */
    public LockRifle() {
        this.setName("Lock Rifle");
        Map<Color, Integer> realoadingCost = new HashMap<>();
        realoadingCost.put(Color.BLUE, 2);
        this.setFirstOptionalModeCost(realoadingCost);
        Map<Color, Integer> firstOptionCost = new HashMap<>();
        firstOptionCost.put(Color.RED, 1);
        this.setFirstOptionalModeCost(firstOptionCost);
    }

    @Override
    public List<WeaponCommand> getPossibleCommands(GameBoard gameboard, Player player) {
        List<WeaponCommand> possibleCommands = new ArrayList<>();
        return possibleCommands;
    }

    @Override
    public List<SelectWeaponOptionCommand> getSelectOptionCommands(Player player, ChoosingWeaponOptionState state) {
        List<SelectWeaponOptionCommand> selectWeaponOptionCommandList = new ArrayList<>();
        if (!isSelectedFirstOptionalFireMode())
            selectWeaponOptionCommandList.add(new SelectWeaponFirstOptionCommand(player, state));
        return selectWeaponOptionCommandList;
    }

    @Override
    public void addTargetPlayer(Player targetPlayer) {

    }

    @Override
    public void addTargetSquare(Square targetSquare) {

    }
}
