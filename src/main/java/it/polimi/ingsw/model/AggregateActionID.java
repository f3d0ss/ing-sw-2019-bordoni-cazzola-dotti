package it.polimi.ingsw.model;

/**
 * Contains every legal type of {@link AggregateAction}
 */
public enum AggregateActionID {
    MOVE_MOVE_MOVE {
        @Override
        public AggregateAction create() {
            return new AggregateAction(3, false, false, false);
        }

        public String toString() {
            return "Muovi fino a 3 quadrati";
        }
    },
    MOVE_GRAB {
        @Override
        public AggregateAction create() {
            return new AggregateAction(1, true, false, false);
        }

        public String toString() {
            return "Muovi fino a 1 quadrato e raccogli";
        }
    },
    SHOOT {
        @Override
        public AggregateAction create() {
            return new AggregateAction(0, false, true, false);
        }

        public String toString() {
            return "Spara a un avversario";
        }
    },
    MOVE_MOVE_GRAB {
        @Override
        public AggregateAction create() {
            return new AggregateAction(2, true, false, false);
        }

        public String toString() {
            return "Muovi fino a 2 quadrati e raccogli";
        }
    },
    MOVE_SHOOT {
        @Override
        public AggregateAction create() {
            return new AggregateAction(1, false, true, false);
        }

        public String toString() {
            return "Muovi fino a 1 quadrato e spara";
        }
    },
    MOVE_RELOAD_SHOOT {
        @Override
        public AggregateAction create() {
            return new AggregateAction(1, false, true, true);
        }

        public String toString() {
            return "Muovi fino a 1 quadrato, ricarica e spara";
        }
    },
    MOVE_MOVE_MOVE_MOVE {
        @Override
        public AggregateAction create() {
            return new AggregateAction(4, false, false, false);
        }

        public String toString() {
            return "Muovi fino a 4 quadrati";
        }
    },
    MOVE_MOVE_RELOAD_SHOOT {
        @Override
        public AggregateAction create() {
            return new AggregateAction(2, false, true, true);
        }

        public String toString() {
            return "Muovi fino a 2 quadrati, ricarica e spara";
        }
    },
    MOVE_MOVE_MOVE_GRAB {
        @Override
        public AggregateAction create() {
            return new AggregateAction(3, true, false, false);
        }

        public String toString() {
            return "Muovi fino a 3 quadrati e raccogli";
        }
    };

    /**
     * @return Aggregate action associated with this ID
     */
    public abstract AggregateAction create();

    @Override
    public abstract String toString();
}
