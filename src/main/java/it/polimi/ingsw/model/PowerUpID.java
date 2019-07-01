package it.polimi.ingsw.model;

/**
 * Contains every standard {@link PowerUp} types
 */
public enum PowerUpID {
    /**
     * Tagback Grenade
     */
    TAGBACK_GRENADE {
        @Override
        public String powerUpName() {
            return "TagbackGrenade";
        }

        @Override
        public String powerUpID() {
            return "grenade";
        }
    },

    /**
     * Targeting Scope
     */
    TARGETING_SCOPE {
        @Override
        public String powerUpName() {
            return "TargetingScope";
        }

        @Override
        public String powerUpID() {
            return "scope";
        }
    },

    /**
     * Teleporter
     */
    TELEPORTER {
        @Override
        public String powerUpName() {
            return "Teleporter";
        }

        @Override
        public String powerUpID() {
            return "teleporter";
        }
    },

    /**
     * Newton
     */
    NEWTON {
        @Override
        public String powerUpName() {
            return "Newton";
        }

        @Override
        public String powerUpID() {
            return "newton";
        }
    };

    /**
     * Gets standard power up name
     *
     * @return Standard power up name
     */
    public abstract String powerUpName();

    public abstract String powerUpID();
}
