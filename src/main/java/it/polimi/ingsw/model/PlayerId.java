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
    },

    GREEN {
        @Override
        public String playerIdName() {
            return "Sprog";
        }
    },

    YELLOW {
        @Override
        public String playerIdName() {
            return ":D-struct-or";
        }
    },

    VIOLET {
        @Override
        public String playerIdName() {
            return "Violet";
        }
    },

    GREY {
        @Override
        public String playerIdName() {
            return "Dozer";
        }
    };

    /**
     * Gets standard player's name
     *
     * @return Standard name of the player
     */
    public abstract String playerIdName();

}
