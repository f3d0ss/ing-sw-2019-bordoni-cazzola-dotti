package it.polimi.ingsw.model;

public enum PlayerId {
    BLUE {
        @Override
        public String toString() {
            return "Banshee";
        }

        @Override
        public String playerId() {
            return "blue";
        }
    },

    GREEN {
        @Override
        public String toString() {
            return "Sprog";
        }

        @Override
        public String playerId() {
            return "green";
        }
    },

    YELLOW {
        @Override
        public String toString() {
            return ":D-struct-or";
        }

        @Override
        public String playerId() {
            return "yellow";
        }
    },

    VIOLET {
        @Override
        public String toString() {
            return "Violet";
        }

        @Override
        public String playerId() {
            return "violet";
        }
    },

    GREY {
        @Override
        public String toString() {
            return "Dozer";
        }

        @Override
        public String playerId() {
            return "grey";
        }
    };

    @Override
    public abstract String toString();

    public abstract String playerId();

}
