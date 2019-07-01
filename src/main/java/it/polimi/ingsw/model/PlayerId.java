package it.polimi.ingsw.model;

/**
 * Contains all standard {@link PlayerId} types
 */
public enum PlayerId {
    BLUE {
        @Override
        public String playerIdName() {
            return "Banshee";
        }

        @Override
        public String playerId() {
            return "blue";
        }
    },

    GREEN {
        @Override
        public String playerIdName() {
            return "Sprog";
        }

        @Override
        public String playerId() {
            return "green";
        }
    },

    YELLOW {
        @Override
        public String playerIdName() {
            return ":D-struct-or";
        }

        @Override
        public String playerId() {
            return "yellow";
        }
    },

    VIOLET {
        @Override
        public String playerIdName() {
            return "Violet";
        }

        @Override
        public String playerId() {
            return "violet";
        }
    },

    GREY {
        @Override
        public String playerIdName() {
            return "Dozer";
        }

        @Override
        public String playerId() {
            return "grey";
        }
    };

    /**
     * Gets standard player's name
     *
     * @return Standard name of the player
     */
    public abstract String playerIdName();

    public abstract String playerId();

}
