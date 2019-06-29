package it.polimi.ingsw.view;

import it.polimi.ingsw.model.PlayerId;

import java.util.List;
import java.util.Map;

public class MatchView {

    private final List<PlayerId> killshotTrack;
    private final int deathsCounter;
    private final Map<PlayerId, Long> leaderBoard;
    private final boolean lastTurn;

    public MatchView(List<PlayerId> killshotTrack, int deathsCounter, Map<PlayerId, Long> leaderBoard, boolean lastTurn) {
        this.killshotTrack = killshotTrack;
        this.deathsCounter = deathsCounter;
        this.leaderBoard = leaderBoard;
        this.lastTurn = lastTurn;
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
}
