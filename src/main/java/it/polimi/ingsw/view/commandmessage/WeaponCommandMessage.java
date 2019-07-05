package it.polimi.ingsw.view.commandmessage;

/**
 * This class wraps a string containing the name of a weapon during transfer from server to client.
 */
public class WeaponCommandMessage extends CommandMessage {
    private final String weapon;

    public WeaponCommandMessage(CommandType type, String weapon) {
        super(type);
        this.weapon = weapon;
    }

    /**
     * Gets the name of the weapon.
     *
     * @return the name of the weapon
     */
    public String getWeapon() {
        return weapon;
    }
}
