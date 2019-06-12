package it.polimi.ingsw.model;

public enum AggregateActionID {
    MOVE_MOVE_MOVE {
        @Override
        public AggregateAction create() {
            return new AggregateAction(3, false, false, false);
        }
    },
    MOVE_GRAB {
        @Override
        public AggregateAction create() {
            return new AggregateAction(1, true, false, false);
        }
    },
    SHOOT {
        @Override
        public AggregateAction create() {
            return new AggregateAction(0, false, true, false);
        }
    },
    MOVE_MOVE_GRAB {
        @Override
        public AggregateAction create() {
            return new AggregateAction(2, true, false, false);
        }
    },
    MOVE_SHOOT {
        @Override
        public AggregateAction create() {
            return new AggregateAction(1, false, true, false);
        }
    },
    MOVE_RELOAD_SHOOT {
        @Override
        public AggregateAction create() {
            return new AggregateAction(1, false, true, true);
        }
    },
    MOVE_MOVE_MOVE_MOVE {
        @Override
        public AggregateAction create() {
            return new AggregateAction(4, false, false, false);
        }
    },
    MOVE_MOVE_RELOAD_SHOOT {
        @Override
        public AggregateAction create() {
            return new AggregateAction(2, false, true, true);
        }
    },
    MOVE_MOVE_MOVE_GRAB {
        @Override
        public AggregateAction create() {
            return new AggregateAction(3, true, false, false);
        }
    };

    public abstract AggregateAction create();
}
