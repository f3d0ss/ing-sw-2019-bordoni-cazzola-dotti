package it.polimi.ingsw.view.commandmessage;

public class WeaponModeCommandMessage extends CommandMessage {
    private String weaponMode;

    public WeaponModeCommandMessage(CommandType type, String weaponMode) {
        super(type);
        this.weaponMode = weaponMode;
    }
}
