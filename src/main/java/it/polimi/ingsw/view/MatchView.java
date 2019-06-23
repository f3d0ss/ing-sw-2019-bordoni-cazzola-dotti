package it.polimi.ingsw.view;

import it.polimi.ingsw.model.PlayerId;

import java.util.List;

public class MatchView {

    private final List<PlayerId> killshotTrack;
    private final int deathsCounter;
    private final int gameBoardId;

    public MatchView(List<PlayerId> killshotTrack, int deathsCounter, int gameBoardId) {
        this.killshotTrack = killshotTrack;
        this.deathsCounter = deathsCounter;
        this.gameBoardId = gameBoardId;
    }

    public int getDeathsCounter() {
        return deathsCounter;
    }

    public List<PlayerId> getKillshotTrack() {
        return killshotTrack;
    }
}
