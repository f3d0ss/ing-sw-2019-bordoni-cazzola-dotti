package it.polimi.ingsw.view;

import it.polimi.ingsw.model.PlayerId;
import it.polimi.ingsw.model.command.Command;
import it.polimi.ingsw.network.client.Ui;

import java.util.List;

public class ConcreteView implements ViewInterface {

    private Ui ui;
    private ModelView modelView;

    public ConcreteView(Ui ui) {
        this.ui = ui;
        modelView = new ModelView();
    }

    public void setMyId(PlayerId myId) {
        modelView.setMyId(myId);
    }

    @Override
    public void update(MatchView mw) {
        modelView.setMatch(mw);
    }

    @Override
    public void update(SquareView sw) {
        int row = sw.getRow();
        int col = sw.getCol();
        modelView.setSquareBoard(row, col, sw);
        if (sw.getColor() != null)
            modelView.setWeaponsOnSpawn(sw.getColor(), ((SpawnSquareView) sw).getWeapons());
        ui.showGame(modelView);
    }

    @Override
    public void update(PlayerView pw) {
        if (pw.getId() == modelView.getMyId())
            modelView.setMe(pw);
        else
            modelView.setEnemie(pw.getId(), pw);
    }

    @Override
    public int sendCommands(List<Command> commands, boolean undo) {
        return 0;
    }
}
