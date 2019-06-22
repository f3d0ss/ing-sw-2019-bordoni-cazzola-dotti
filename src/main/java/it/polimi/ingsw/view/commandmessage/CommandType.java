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
            return "Muovi";
        }
    }, PAY {
        public String getString() {
            return "Conferma pagamento";
        }
    }, RESPAWN {
        public String getString() {
            return "Rigenera";
        }
    }, SELECT_AGGREGATE_ACTION {
        public String getString() {
            return "Scegli la tua mossa";
        }
    }, SELECT_AMMO_PAYMENT {
        public String getString() {
            return "Seleziona le munizioni con cui vuoi pagare";
        }
    }, SELECT_BUYING_WEAPON {
        public String getString() {
            return "Seleziona l'arma che vuoi acquistare";
        }
    }, SELECT_DISCARD_WEAPON {
        public String getString() {
            return "Scegli l'arma che vuoi scartare";
        }
    }, SELECT_POWER_UP {
        public String getString() {
            return "Seleziona il potenziamento che vuoi usare";
        }
    }, SELECT_POWER_UP_PAYMENT {
        public String getString() {
            return "Seleziona un potenziamento per pagare";
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
            return "Scegli come bersaglio";
        }
    }, SELECT_TARGET_SQUARE {
        public String getString() {
            return "Scegli un riquadro";
        }
    }, SELECT_WEAPON {
        public String getString() {
            return "Scegli un'arma";
        }
    }, SELECT_WEAPON_MODE {
        public String getString() {
            return "Scegli con quale modalità con cui vuoi usare l'arma";
        }
    }, SHOOT {
        public String getString() {
            return "Spara";
        }
    }, USE_NEWTON {
        public String getString() {
            return "Usa il Raggio Cinetico";
        }
    }, USE_SCOPE {
        public String getString() {
            return "Usa il Mirino";
        }
    }, USE_TAGBACK_GRENADE {
        public String getString() {
            return "Usa la Granata Venom";
        }
    }, USE_TELEPORT {
        public String getString() {
            return "Usa il teletrasporto";
        }
    };

    public abstract String getString();
}
