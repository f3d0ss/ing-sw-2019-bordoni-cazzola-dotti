package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.PlayerId;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Contains tests for {@link MatchController}'s methods
 */
class MatchControllerTest {
    /**
     * Tests the scoring of a track. The player with the most tokens is first.
     * If multiple players dealt the same amount of damage, break the tie in favor of the player whose damage landed first.
     */
    @Test
    void sortTest() {
        List<PlayerId> track = new ArrayList<>();
        track.add(PlayerId.GREEN);
        track.add(PlayerId.BLUE);
        track.add(PlayerId.GREY);
        track.add(PlayerId.GREY);
        track.add(PlayerId.GREY);
        track.add(PlayerId.GREY);
        track.add(PlayerId.YELLOW);
        track.add(PlayerId.VIOLET);
        track.add(PlayerId.VIOLET);
        track.add(PlayerId.GREEN);
        track.add(PlayerId.VIOLET);
        track.add(PlayerId.VIOLET);
        track.add(PlayerId.GREEN);
        track.add(PlayerId.BLUE);
        track.add(PlayerId.BLUE);
        track.add(PlayerId.YELLOW);
        track.add(PlayerId.YELLOW);
        track.add(PlayerId.YELLOW);
        track.add(PlayerId.GREEN);
        track.add(PlayerId.BLUE);
        List<PlayerId> orderByFirstBlood = track.stream().distinct().collect(Collectors.toList());
        Map<PlayerId, Long> counts = track.stream()
                .collect(Collectors.groupingBy(a -> a, Collectors.counting()));
        counts = MatchController.sort(counts, orderByFirstBlood);
        List<PlayerId> actual = new ArrayList<>(counts.keySet());
        List<PlayerId> expected = new ArrayList<>();
        expected.add(PlayerId.GREEN);
        expected.add(PlayerId.BLUE);
        expected.add(PlayerId.GREY);
        expected.add(PlayerId.YELLOW);
        expected.add(PlayerId.VIOLET);
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }
}