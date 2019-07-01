package it.polimi.ingsw.model;

/**
 * Contains every standard power up type
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
    },

    /**
     * Targeting Scope
     */
    TARGETING_SCOPE {
        @Override
        public String powerUpName() {
            return "TargetingScope";
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
    },

    /**
     * Newton
     */
    NEWTON {
        @Override
        public String powerUpName() {
            return "Newton";
        }
    };

    /**
     * @return Standard power up name
     */
    public abstract String powerUpName();
}
