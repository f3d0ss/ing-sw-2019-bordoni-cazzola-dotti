package it.polimi.ingsw.model;

/**
 * Contains all the possible ammo-cubes' colors
 * <li>{@link #BLUE}</li>
 * <li>{@link #RED}</li>
 * <li>{@link #YELLOW}</li>
 */
public enum Color {
    /**
     * Blue color
     */
    BLUE {
        @Override
        public String colorName() {
            return "Blue";
        }
        public String colorShortName() {
            return "B";
        }
    },

    /**
     * Red color
     */
    RED {
        @Override
        public String colorName() {
            return "Red";
        }
        public String colorShortName() {
            return "R";
        }
    },

    /**
     * Yellow color
     */
    YELLOW {
        @Override
        public String colorName() {
            return "Yellow";
        }
        public String colorShortName() {
            return "Y";
        }
    };

    public abstract String colorName();
    public abstract String colorShortName();
}
