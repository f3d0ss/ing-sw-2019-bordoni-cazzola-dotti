package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;

import java.util.List;
import java.util.Map;

public class MatchView {

    private final List<PlayerId> killshotTrack;
    private final int deathsCounter;
    private final int gameBoardId;
    private final Map<PlayerId, Long> leaderBoard;

    public MatchView(List<PlayerId> killshotTrack, int deathsCounter, int gameBoardId, Map<PlayerId, Long> leaderBoard) {
        this.killshotTrack = killshotTrack;
        this.deathsCounter = deathsCounter;
        this.gameBoardId = gameBoardId;
        this.leaderBoard = leaderBoard;
    }

    public int getDeathsCounter() {
        return deathsCounter;
    }

    public List<PlayerId> getKillshotTrack() {
        return killshotTrack;
    }

    public int getGameBoardId() {
        return gameBoardId;
    }

    public Map<PlayerId, Long> getLeaderBoard() {
        return leaderBoard;
    }
}
