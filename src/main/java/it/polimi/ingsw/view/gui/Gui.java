package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.PlayerId;
import it.polimi.ingsw.utils.Lock;
import it.polimi.ingsw.view.*;
import it.polimi.ingsw.view.commandmessage.CommandMessage;
import javafx.application.Application;
import javafx.application.Platform;

import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

/**
 * This class manages Graphical User Interface as User Interface.
 */
public class Gui implements Ui, Runnable {

    private static final int TIME_TO_SLEEP = 100;
    private String answer = "";
    private boolean ready = false;
    private boolean inputReady = false;
    private boolean initializationDone = false;
    private MainGuiController controller;

    /**
     * Shows a message coming from server on the window.
     *
     * @param toBeShown        is a string represent a question or a statement
     * @param possibleAnswers  is the list of the possible answers that user can choose
     * @param isAnswerRequired specify if an answer is required (the message is a question)
     *                         or not (the message is a notice)
     * @return the user's answer or a reception acknowledgement
     */
    public String showMessage(String toBeShown, List<String> possibleAnswers, boolean isAnswerRequired) {
        inputReady = false;
        System.out.println(toBeShown);//it show message even on cli
        Platform.runLater(() -> GuiManager.setMessageAndShow(toBeShown, possibleAnswers, isAnswerRequired));
        while (!inputReady) {
            try {
                sleep(TIME_TO_SLEEP);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(answer);//it show answer even on cli
        return answer;
    }

    /**
     * Sets that view initialization is not already done, which means that view-side model is not ready.
     */
    public void setViewInitializationUndone() {
        initializationDone = false;
    }

    /**
     * Gets if the view initialization is done.
     *
     * @return true if the view initialization is done
     */
    public boolean isViewInitializationDone() {
        return initializationDone;
    }

    /**
     * Notify that the view initialization is done updating the instance of ModelView,
     * then calls a refresh of entire view.
     *
     * @param modelView is the ModelView instance to be updated
     */
    public void setViewInitializationDone(ModelView modelView) {
        Lock lock = new Lock();
        try {
            lock.lock();
            Platform.runLater(() -> {
                GuiManager.startMainGui();
                controller = GuiManager.getController();
                controller.setModelView(modelView);
                lock.unlock();
            });
            lock.lock();
            initializationDone = true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Sets the answer coming from the window.
     *
     * @param answer is the string containing the answer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * Tells if an answer coming from the window is ready.
     *
     * @return true if an answer from
     */
    public boolean isReady() {
        return ready;
    }

    /**
     * Sets when the gui classes are ready.
     *
     * @param ready tells if the gui is ready
     */
    public void setGuiReady(boolean ready) {
        this.ready = ready;
    }

    /**
     * Starts the application managing the gui
     */
    @Override
    public void run() {
        Application.launch(GuiManager.class);
    }

    /**
     * Refreshes view after a PlayerView instance update.
     *
     * @param pw is the PlayerView instance to be updated then shown
     */
    @Override
    public void refreshView(PlayerView pw) {
        Platform.runLater(() -> controller.updatePlayer(pw));
    }

    /**
     * Refreshes view after a SquareView instance update.
     *
     * @param sw is the SquareView instance to be updated then shown
     */
    @Override
    public void refreshView(SquareView sw) {
        Platform.runLater(() -> controller.updateSquare(sw));
    }

    /**
     * Refreshes view after a MatchView instance update.
     *
     * @param mw is the MatchView instance to be updated then shown
     */
    @Override
    public void refreshView(MatchView mw) {
        Platform.runLater(() -> controller.updateMatchView(mw));
    }

    /**
     * Sets when the input coming from the window is ready.
     *
     * @param inputReady tells if the input is ready
     */
    public void setInputReady(boolean inputReady) {
        this.inputReady = inputReady;
    }

    /**
     * Manages multiple options coming from server during game.
     *
     * @param commands is the list of command user can choose from
     * @param undo     specify if user can come back to the last choice
     * @return the number correspond to the user choice
     */
    public int manageCommandChoice(List<CommandMessage> commands, boolean undo) {
        Lock lock = new Lock();
        try {
            lock.lock();
            Platform.runLater(() -> controller.showCommand(commands, undo, lock));
            lock.lock();
            return controller.getSelectedCommand();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return -1;
    }

    /**
     * Shows leader board at the end of game.
     *
     * @param leaderBoard is the leader board sent by server
     */
    public void showLeaderBoard(Map<PlayerId, Long> leaderBoard) {
        Platform.runLater(() -> controller.showLeaderBoard(leaderBoard));
    }
}
