package it.polimi.ingsw.model;

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

    public abstract String playerIdName();
}
