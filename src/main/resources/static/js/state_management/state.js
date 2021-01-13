/**
 * @class State
 * @classdesc This class will store the state of the app and notify all registered callbacks
 */
class State {
    /**
     * @private
     */
    state;

    /**
     * @private
     * @type {Set<Function>}
     */
    observers = new Set();

    constructor(initialState) {
        this.state = initialState;
    }

    /**
     * @param newState
     * @returns {{width: number, x: number, y: number, zoom: number, height: number}}
     */
    setState = (newState) => {
        this.state = newState;
        this.notify();
        return this.state;
    }

    subscribe = (callback) => {
        this.observers.add(callback);
        return callback;
    }

    unsubscribe = (callback) => {
        this.observers.delete(callback);
    }

    /**
     * @private
     */
    notify = () => {
        this.observers.forEach((observer) => {
            observer(this.state);
        });
    }

    save = () => new StateSnapshot(this.state);

    restore = (snapshot) => {
        this.setState(snapshot.getState());
    }
}