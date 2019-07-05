package it.polimi.ingsw.view;

import it.polimi.ingsw.model.PlayerId;

import java.util.List;
import java.util.Map;

/**
 * This class represent an {@link it.polimi.ingsw.model.Match} on view side.
 */
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

    /**
     * Gets the number of left skulls of the current match.
     *
     * @return the number of left skulls
     */
    public int getDeathsCounter() {
        return deathsCounter;
    }

    /**
     * Gets the track of killshots carried out during the current match.
     *
     * @return the ordered list of playerId who carried out killshots
     */
    public List<PlayerId> getKillshotTrack() {
        return killshotTrack;
    }

    /**
     * Gets the leader board (set only on game over).
     *
     * @return the leader board
     */
    public Map<PlayerId, Long> getLeaderBoard() {
        return leaderBoard;
    }

    /**
     * Tells if the current turn is the last one.
     *
     * @return true if the current turn is the last one
     */
    public boolean isLastTurn() {
        return lastTurn;
    }

    /**
     * Gets the player who is on duty.
     *
     * @return the playerId of who is on duty
     */
    public PlayerId getPlayerOnDuty() {
        return playerOnDuty;
    }

    /**
     * Gets the id of the chosen game board.
     *
     * @return the id of the chosen game board
     */
    public int getGameBoardId() {
        return gameBoardId;
    }
}
