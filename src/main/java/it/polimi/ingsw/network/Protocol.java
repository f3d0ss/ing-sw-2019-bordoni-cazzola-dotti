package it.polimi.ingsw.network;

public enum Protocol {

    ACK{
        public String getQuestion(){
            return Character.toString((char)3);
        }
    },

    WELCOME {
        public String getQuestion(){
            return "Benvenuto su Adrenalina!\nSei stato accettato con il codice %s. Memorizzalo per riconnetterti a seguito di disconnessioni impreviste.";
        }
    },
    LOGIN_FIRST {
        public String getQuestion(){
            return "Sei il primo giocatore; digita il tuo nickname:";
        }
    },
    LOGIN_OTHERS {
        public String getQuestion(){
            return "Sono in attesa di una nuova partita: %sdigita il tuo nickname:";
        }
    },
    LOGIN_REPEAT {
        public String getQuestion(){
            return "Questo nickname è già in uso, scegline un altro:";
        }
    },
    NEW_ENTRY {
        public String getQuestion(){
            return "%s si è appena registrato.";
        }
    },
    LOGIN_CONFIRM {
        public String getQuestion(){
            return "Iscrizione riuscita! Ora attendi che altri giocatori si connettano.";
        }
    },
    COUNTDOWN {
        public String getQuestion(){
            return "Il gioco inizierà entro %s secondi.";
        }
    },
    ARE_YOU_READY {
        public String getQuestion(){
            return "L'attesa è finita. Preparati a combattere!";
        }
    },
    CHOOSE_BOARD {
        public String getQuestion(){
            return "Scegli l'arena di gioco:";
        }
    },
    TRY {
        public String getQuestion(){
            return "Messaggio di prova.";
        }
    };

    public abstract String getQuestion();
}
