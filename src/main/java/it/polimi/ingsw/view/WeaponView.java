package it.polimi.ingsw.view;

public class WeaponView {
    private final String name;
    private final boolean loaded;

    public WeaponView(String name, boolean loaded) {
        this.name = name;
        this.loaded = loaded;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public String getName() {
        return name;
    }
}
