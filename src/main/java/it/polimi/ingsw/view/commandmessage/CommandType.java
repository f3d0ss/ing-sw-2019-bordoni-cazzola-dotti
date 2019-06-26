package it.polimi.ingsw.view.commandmessage;

public enum CommandType {
    DONE {
        public String getString() {
            return "Conferma";
        }
    }, GRAB_TILE {
        public String getString() {
            return "Raccogli le munizioni";
        }
    }, MOVE {
        public String getString() {
            return "Muovi in ";
        }
    }, PAY {
        public String getString() {
            return "Conferma pagamento";
        }
    }, RESPAWN {
        public String getString() {
            return "Rigenera usando ";
        }
    }, SELECT_AGGREGATE_ACTION {
        public String getString() {
            return "";
        }
    }, SELECT_AMMO_PAYMENT {
        public String getString() {
            return "Paga con la munizione ";
        }
    }, SELECT_BUYING_WEAPON {
        public String getString() {
            return "Acquista l'arma ";
        }
    }, SELECT_DISCARD_WEAPON {
        public String getString() {
            return "Scegli l'arma che vuoi scartare";
        }
    }, SELECT_POWER_UP {
        public String getString() {
            return "Usa il potenziamento ";
        }
    }, SELECT_POWER_UP_PAYMENT {
        public String getString() {
            return "Paga con il pontenziamento ";
        }
    }, SELECT_RELOADING_WEAPON {
        public String getString() {
            return "Seleziona l'arma che vuoi ricaricare";
        }
    }, SELECT_SCOPE {
        public String getString() {
            return "Seleziona un Mirino";
        }
    }, SELECT_SHOOT_ACTION {
        public String getString() {
            return "Spara ad un avversario";
        }
    }, SELECT_TARGET_PLAYER {
        public String getString() {
            return "Spara a ";
        }
    }, SELECT_TARGET_SQUARE {
        public String getString() {
            return "Scegli un riquadro";
        }
    }, SELECT_WEAPON {
        public String getString() {
            return "Scegli l'arma ";
        }
    }, SELECT_WEAPON_MODE {
        public String getString() {
            return "Spara in modalità ";
        }
    }, SHOOT {
        public String getString() {
            return "Spara";
        }
    }, USE_NEWTON {
        public String getString() {
            return "Usa il Raggio Cinetico su ";
        }
    }, USE_SCOPE {
        public String getString() {
            return "Usa il Mirino contro ";
        }
    }, USE_TAGBACK_GRENADE {
        public String getString() {
            return "Usa Granata Venom contro ";
        }
    }, USE_TELEPORT {
        public String getString() {
            return "Teletrasportati in ";
        }
    }, UNDO {
        public String getString() {
            return "Annulla";
        }
    };

    public abstract String getString();
}
