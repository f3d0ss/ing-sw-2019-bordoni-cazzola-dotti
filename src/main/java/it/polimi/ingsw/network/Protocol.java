package it.polimi.ingsw.network;

public enum Protocol {
    CHOOSE_UI {
        public String getQuestion() {
            return "Quale tipo di user interface vuoi usare?";
        }

        public boolean requiresAnswer() {
            return true;
        }
    }, CHOOSE_CONNECTION {
        public String getQuestion() {
            return "Scegli la tecnologia di connessione.";
        }

        public boolean requiresAnswer() {
            return true;
        }
    }, INSERT_IP {
        public String getQuestion() {
            return "Inserisci l'indirizzo ip del server.";
        }

        public boolean requiresAnswer() {
            return true;
        }
    }, INSERT_IP_AGAIN {
        public String getQuestion() {
            return "Server non raggiungibile. Reimmetti l'indirizzo ip o riprova più tardi.";
        }

        public boolean requiresAnswer() {
            return true;
        }
    }, RECONNECT {
        public String getQuestion() {
            return "Vuoi iniziare un nuovo gioco o riconnetterti a un gioco esistente?";
        }

        public boolean requiresAnswer() {
            return true;
        }
    }, INSERT_OLD_CODE {
        public String getQuestion() {
            return "Inserisci il codice con cui eri stato registrato alla connessione.";
        }

        public boolean requiresAnswer() {
            return true;
        }
    }, INVALID_OLD_CODE {
        public String getQuestion() {
            return "Codice non riconosciuto. Attenzione: la partita potrebbe essere terminata!";
        }

        public boolean requiresAnswer() {
            return false;
        }
    }, UNREACHABLE_SERVER {
        public String getQuestion() {
            return "Impossibile raggiungere il server. Riprova più tardi.";
        }

        public boolean requiresAnswer() {
            return false;
        }
    }, WELCOME {
        public String getQuestion() {
            return "Benvenuto su Adrenalina!\nSei stato accettato con il codice %s.\nMemorizzalo per riconnetterti a seguito di disconnessioni impreviste.";
        }

        public boolean requiresAnswer() {
            return false;
        }
    }, WELCOME_BACK {
        public String getQuestion() {
            return "Bentornato!";
        }

        public boolean requiresAnswer() {
            return false;
        }
    }, LOGIN_FIRST {
        public String getQuestion() {
            return "Digita il tuo nickname:";
        }

        public boolean requiresAnswer() {
            return true;
        }
    }, LOGIN_REPEAT {
        public String getQuestion() {
            return "Questo nickname è già in uso, scegline un altro:";
        }

        public boolean requiresAnswer() {
            return true;
        }
    }, NEW_ENTRY {
        public String getQuestion() {
            return "%s si è appena registrato.";
        }

        public boolean requiresAnswer() {
            return false;
        }
    }, NEW_CONNECTION {
        public String getQuestion() {
            return "Nuova connessione al server.";
        }

        public boolean requiresAnswer() {
            return false;
        }
    }, LOGIN_CONFIRM {
        public String getQuestion() {
            return "Iscrizione riuscita, %s! Ora attendi che altri giocatori si connettano.";
        }

        public boolean requiresAnswer() {
            return false;
        }
    }, WAIT_FOR_PLAYERS {
        public String getQuestion() {
            return "Mancano %s giocatori per l'avvio della partita.";
        }

        public boolean requiresAnswer() {
            return false;
        }
    }, REMOVAL {
        public String getQuestion() {
            return "%s si è disconnesso.";
        }

        public boolean requiresAnswer() {
            return false;
        }
    }, COUNTDOWN {
        public String getQuestion() {
            return "Il gioco inizierà entro %s secondi.";
        }

        public boolean requiresAnswer() {
            return false;
        }
    }, ARE_YOU_READY {
        public String getQuestion() {
            return "Attendi...";
        }

        public boolean requiresAnswer() {
            return false;
        }
    }, LET_US_START {
        public String getQuestion() {
            return "Avvio del gioco in corso...";
        }

        public boolean requiresAnswer() {
            return false;
        }
    }, CHOOSE_BOARD {
        public String getQuestion() {
            return "Sei il primo giocagore: scegli l'arena di gioco.";
        }

        public boolean requiresAnswer() {
            return true;
        }
    }, TRY {
        public String getQuestion() {
            return "Messaggio di prova.";
        }

        public boolean requiresAnswer() {
            return false;
        }
    };
    //TODO: rename
    public static final String ack = Character.toString((char) 3);
    public static final String ping = Character.toString((char) 4);//to be removed
    public static final String err = Character.toString((char) 5);

    public abstract String getQuestion();

    public abstract boolean requiresAnswer();
}