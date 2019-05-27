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

    public int getIntValue() {
        if (this == BLUE) return 0;
        if (this == GREEN) return 1;
        if (this == YELLOW) return 2;
        if (this == VIOLET) return 3;
        if (this == GREY) return 4;
        return -1;
    }
}
