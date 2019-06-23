package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;

import java.util.List;
import java.util.Map;

public class MatchView {

    private final List<PlayerId> killshotTrack;
    private final int deathsCounter;

    public MatchView(List<PlayerId> killshotTrack, int deathsCounter) {
        this.killshotTrack = killshotTrack;
        this.deathsCounter = deathsCounter;
    }

    public int getDeathsCounter() {
        return deathsCounter;
    }

    public List<PlayerId> getKillshotTrack() {
        return killshotTrack;
    }
}
