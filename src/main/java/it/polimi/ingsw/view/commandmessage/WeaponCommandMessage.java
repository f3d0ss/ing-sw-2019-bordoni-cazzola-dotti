package it.polimi.ingsw.view.commandmessage;

public class WeaponCommandMessage extends CommandMessage {
    private String weapon;

    public WeaponCommandMessage(CommandType type, String weapon) {
        super(type);
        this.weapon = weapon;
    }
}
