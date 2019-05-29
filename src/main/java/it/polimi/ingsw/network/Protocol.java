package it.polimi.ingsw.network;

public enum Protocol {

    CHOOSE_UI {
        public String getQuestion() {
            return "Quale tipo di user interface vuoi usare?";
        }
        public boolean requiresAnswer() {
            return true;
        }
    },
    CHOOSE_CONNECTION {
        public String getQuestion() {
            return "Scegli la tecnologia di connessione.";
        }
        public boolean requiresAnswer() {
            return true;
        }
    },
    INSERT_IP {
        public String getQuestion() {
            return "Inserisci l'indirizzo ip del server.";
        }
        public boolean requiresAnswer() {
            return true;
        }
    },
    INSERT_IP_AGAIN {
        public String getQuestion() {
            return "Ip non valido. Riprova.";
        }
        public boolean requiresAnswer() {
            return true;
        }
    },
    WELCOME {
        public String getQuestion() {
            return "Benvenuto su Adrenalina!\nSei stato accettato con il codice %s.\nMemorizzalo per riconnetterti a seguito di disconnessioni impreviste.";
        }
        public boolean requiresAnswer() {
            return false;
        }
    },
    LOGIN_FIRST {
        public String getQuestion() {
            return "Sei il primo giocatore; digita il tuo nickname:";
        }
        public boolean requiresAnswer() {
            return true;
        }
    },
    LOGIN_OTHERS {
        public String getQuestion() {
            return "Sono in attesa di una nuova partita: %sdigita il tuo nickname:";
        }
        public boolean requiresAnswer() {
            return true;
        }
    },
    LOGIN_REPEAT {
        public String getQuestion() {
            return "Questo nickname è già in uso, scegline un altro:";
        }
        public boolean requiresAnswer() {
            return true;
        }
    },
    NEW_ENTRY {
        public String getQuestion() {
            return "%s si è appena registrato.";
        }
        public boolean requiresAnswer() {
            return false;
        }
    },
    LOGIN_CONFIRM {
        public String getQuestion() {
            return "Iscrizione riuscita! Ora attendi che altri giocatori si connettano.";
        }
        public boolean requiresAnswer() {
            return false;
        }
    },
    COUNTDOWN {
        public String getQuestion() {
            return "Il gioco inizierà entro %s secondi.";
        }
        public boolean requiresAnswer() {
            return false;
        }
    },
    ARE_YOU_READY {
        public String getQuestion() {
            return "L'attesa è finita. Preparati a combattere!";
        }
        public boolean requiresAnswer() {
            return false;
        }
    },
    CHOOSE_BOARD {
        public String getQuestion() {
            return "Scegli l'arena di gioco:";
        }
        public boolean requiresAnswer() {
            return true;
        }
    },
    TRY {
        public String getQuestion() {
            return "Messaggio di prova.";
        }
        public boolean requiresAnswer() {
            return false;
        }
    };

    public static final String ack = Character.toString((char) 3);
    public static final String ping = Character.toString((char) 4);
    public abstract String getQuestion();
    public abstract boolean requiresAnswer();
}
