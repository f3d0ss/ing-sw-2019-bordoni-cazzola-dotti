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

        @Override
        public String colorID() {
            return "blue";
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

        @Override
        public String colorID() {
            return "red";
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

        @Override
        public String colorID() {
            return "yellow";
        }
    };

    public abstract String colorName();

    public abstract String colorID();
}
