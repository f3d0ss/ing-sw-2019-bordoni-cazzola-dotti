package it.polimi.ingsw.view.commandmessage;

public class WeaponModeCommandMessage extends CommandMessage {
    private String weaponMode;
    private String jsonType = getClass().getSimpleName();

    public WeaponModeCommandMessage(CommandType type, String weaponMode) {
        super(type);
        this.weaponMode = weaponMode;
    }

    public String getWeaponMode() {
        return weaponMode;
    }
}
