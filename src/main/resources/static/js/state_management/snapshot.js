/**
 * @class StateSnapshot
 * @classdesc This class is used to store a snapshot of the state (pattern Memento)
 */
class StateSnapshot {
    /**
     * @private
     */
    state

    constructor(state) {
        this.state = state;
    }

    getState = () => this.state;
}