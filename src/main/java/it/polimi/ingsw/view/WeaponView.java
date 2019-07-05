package it.polimi.ingsw.view;

/**
 * This class represent an {@link it.polimi.ingsw.model.Weapon} on view side.
 */
public class WeaponView {
    private final String name;
    private final boolean loaded;

    public WeaponView(String name, boolean loaded) {
        this.name = name;
        this.loaded = loaded;
    }

    /**
     * Tells if the weapon is loaded.
     *
     * @return true if the weapon is loaded
     */
    public boolean isLoaded() {
        return loaded;
    }

    /**
     * Gets the weapon's name.
     *
     * @return a string containing the weapon's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the weapon's id.
     *
     * @return a string containing the weapon's id
     */
    public String getID() {
        return name.toLowerCase().replace(' ', '_');
    }
}
