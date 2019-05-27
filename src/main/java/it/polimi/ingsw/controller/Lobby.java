package it.polimi.ingsw.controller;

import java.util.ArrayList;
import java.util.List;

public class Lobby {
    List<String> nicknames = new ArrayList<>();

    public List<String> getNicknames() {
        return nicknames;
    }

    boolean addPlayer(String nickname) {
        if (nicknames.contains(nickname))
            return false;
        nicknames.add(nickname);
        return true;
    }
}
