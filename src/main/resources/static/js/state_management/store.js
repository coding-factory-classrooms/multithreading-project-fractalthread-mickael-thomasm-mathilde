/**
 * @class Store
 * @classdesc This class will keep the history of the state update (pattern Memento)
 */
class Store {
    /**
     * @private
     */
    state;

    /**
     * @private
     * @type {number}
     */
    currentIndex = 0;

    /**
     * @private
     * @type {StateSnapshot[]}
     */
    snapshots = [];

    constructor(initialState) {
        this.state = new State(initialState);
    }

    setState(newState) {
        this.snapshots.push(this.state.save());
        this.currentIndex++;
        this.state.setState(newState);
    }

    getState = () => this.state.state;

    undo() {
        if (this.currentIndex === 0) return;
        this.currentIndex--;
        this.state.restore(this.snapshots[this.currentIndex]);
        this.snapshots = this.snapshots.slice(0, this.currentIndex);
    }

    subscribe = (observer) => {
        this.state.subscribe(observer);
    }

    unsubscribe = (observer) => {
        this.state.unsubscribe(observer);
    }
}