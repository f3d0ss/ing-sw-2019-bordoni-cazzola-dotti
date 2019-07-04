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
    }, INSERT_PORT {
        public String getQuestion() {
            return "Inserisci il numero della porta del server.";
        }

        public boolean requiresAnswer() {
            return true;
        }
    }, INSERT_IP_AGAIN {
        public String getQuestion() {
            return "Indirizzo ip non valido. Riprova.";
        }

        public boolean requiresAnswer() {
            return true;
        }
    }, INSERT_PORT_AGAIN {
        public String getQuestion() {
            return "Numero di porta non valido. Riprova.";
        }

        public boolean requiresAnswer() {
            return true;
        }
    }, INVALID_CONNECTION_PARAMETERS {
        public String getQuestion() {
            return "Server non raggiungibile. Verifica che siano corretti indirizzo ip e porta o riprova più tardi.";
        }

        public boolean requiresAnswer() {
            return false;
        }
    }, CONNECTING {
        public String getQuestion() {
            return "Connessione al server (%s) in corso...";
        }

        public boolean requiresAnswer() {
            return false;
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
            return true;
        }
    }, WELCOME {
        public String getQuestion() {
            return "Benvenuto su Adrenalina!\nSei stato accettato con il codice %s.\nMemorizzalo per riconnetterti a seguito di disconnessioni impreviste.\nDigita il tuo nickname:";
        }

        public boolean requiresAnswer() {
            return true;
        }
    }, WELCOME_BACK {
        public String getQuestion() {
            return "Bentornato %s!";
        }

        public boolean requiresAnswer() {
            return false;
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
    }, ARE_YOU_ALIVE {
        public String getQuestion() {
            return "";
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
            return "Sei il primo giocatore: scegli l'arena di gioco.";
        }

        public boolean requiresAnswer() {
            return true;
        }
    }, TIME_EXCEEDED {
        public String getQuestion() {
            return "Tempo scaduto. Disconnessione in corso. Riconnettiti al server per riprendere il gioco.";
        }

        public boolean requiresAnswer() {
            return false;
        }
    }, UPDATE_MATCH {
        public String getQuestion() {
            return "";
        }

        public boolean requiresAnswer() {
            return false;
        }
    }, UPDATE_PLAYER {
        public String getQuestion() {
            return "";
        }

        public boolean requiresAnswer() {
            return false;
        }
    }, UPDATE_SQUARE {
        public String getQuestion() {
            return "";
        }

        public boolean requiresAnswer() {
            return false;
        }
    }, INITIALIZATION_DONE {
        public String getQuestion() {
            return "";
        }

        public boolean requiresAnswer() {
            return false;
        }
    }, SEND_COMMANDS {
        public String getQuestion() {
            return "";
        }

        public boolean requiresAnswer() {
            return true;
        }
    };

    public static final String ACK = (char) 3 + "ack";
    public static final String AFK = (char) 4 + "afk";
    public static final String ERR = (char) 5 + "err";

    public abstract String getQuestion();

    public abstract boolean requiresAnswer();
}