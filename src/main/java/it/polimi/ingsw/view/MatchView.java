package it.polimi.ingsw.view;

import it.polimi.ingsw.model.PlayerId;

import java.util.List;
import java.util.Map;

public class MatchView {

    private final List<PlayerId> killshotTrack;
    private final int deathsCounter;
    private final Map<PlayerId, Long> leaderBoard;
    private final boolean lastTurn;
    private final PlayerId playerOnDuty;
    private final int gameBoardId;

    public MatchView(List<PlayerId> killshotTrack, int deathsCounter, int gameBoardId, Map<PlayerId, Long> leaderBoard, boolean lastTurn, PlayerId playerOnDuty) {
        this.killshotTrack = killshotTrack;
        this.deathsCounter = deathsCounter;
        this.leaderBoard = leaderBoard;
        this.gameBoardId = gameBoardId;
        this.lastTurn = lastTurn;
        this.playerOnDuty = playerOnDuty;
    }

    public int getDeathsCounter() {
        return deathsCounter;
    }

    public List<PlayerId> getKillshotTrack() {
        return killshotTrack;
    }

    public Map<PlayerId, Long> getLeaderBoard() {
        return leaderBoard;
    }

    public boolean isLastTurn() {
        return lastTurn;
    }

    public PlayerId getPlayerOnDuty() {
        return playerOnDuty;
    }

    public int getGameBoardId() {
        return gameBoardId;
    }
}
