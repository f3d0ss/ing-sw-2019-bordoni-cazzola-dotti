package it.polimi.ingsw.network.client;

import it.polimi.ingsw.utils.Lock;
import it.polimi.ingsw.view.ModelView;
import it.polimi.ingsw.view.commandmessage.CommandMessage;
import it.polimi.ingsw.view.gui.GuiManager;
import it.polimi.ingsw.view.gui.MainGuiController;
import javafx.application.Application;
import javafx.application.Platform;

import java.util.List;
import java.util.concurrent.locks.LockSupport;

import static java.lang.Thread.sleep;

public class Gui implements Ui, Runnable {

    private String answer = "";
    private boolean ready = false;
    private boolean inputReady = false;
    private boolean initializationDone = false;
    private static final int TIME_TO_SLEEP = 100;
    private MainGuiController controller;

    public String showMessage(String toBeShown, List<String> possibleAnswers, boolean isAnswerRequired) {
        inputReady = false;
        System.out.println(toBeShown);//it show message even on cli
        Platform.runLater(() -> GuiManager.setMessageAndShow(toBeShown, possibleAnswers, isAnswerRequired));
        while (!inputReady) {
            try {
                sleep(TIME_TO_SLEEP);
            } catch (InterruptedException e) {
            }
        }
        GuiManager.setInputReady(false);
        System.out.println(answer);//it show answer even on cli
        return answer;
    }

    public void refreshView(ModelView modelView) {
        controller.updateModelView(modelView);
    }

    public void setViewInitializationDone(ModelView modelView) {
        Platform.runLater(() -> {
            GuiManager.startMainGui();
            controller = GuiManager.getController();
            controller.setModelView(modelView);
            initializationDone = true;
        });
    }

    public boolean isViewInitializationDone() {
        return initializationDone;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isReady() {
        return ready;
    }

    public void setGuiReady(boolean ready) {
        this.ready = ready;
    }

    @Override
    public void run() {
        Application.launch(GuiManager.class);
    }

    public void setInputReady(boolean inputReady) {
        this.inputReady = inputReady;
    }

    public int manageCommandChoice(List<CommandMessage> commands, boolean undo) {
        Lock lock = new Lock();
        try {
            lock.lock();
            Platform.runLater(() -> controller.showCommand(commands, lock));
            lock.lock();
            return controller.getSelectedCommand();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
