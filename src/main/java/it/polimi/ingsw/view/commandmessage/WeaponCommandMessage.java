package it.polimi.ingsw.view.commandmessage;

public class WeaponCommandMessage extends CommandMessage {
    private String weapon;
    private String jsonType = getClass().getSimpleName();

    public WeaponCommandMessage(CommandType type, String weapon) {
        super(type);
        this.weapon = weapon;
    }

    public String getWeapon() {
        return weapon;
    }
}
