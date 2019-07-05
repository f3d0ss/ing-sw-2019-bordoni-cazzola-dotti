package it.polimi.ingsw.view.commandmessage;

/**
 * This class wraps a string containing the name of a weapon mode during transfer from server to client.
 */
public class WeaponModeCommandMessage extends CommandMessage {
    private final String weaponMode;

    public WeaponModeCommandMessage(CommandType type, String weaponMode) {
        super(type);
        this.weaponMode = weaponMode;
    }

    /**
     * Gets the name of the weapon mode.
     *
     * @return the name of the weapon mode
     */
    public String getWeaponMode() {
        return weaponMode;
    }
}
