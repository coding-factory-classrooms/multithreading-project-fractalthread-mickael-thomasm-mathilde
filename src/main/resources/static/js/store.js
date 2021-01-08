class Store {
    state;
    observers = new Set();

    constructor(initialState) {
        this.state = initialState;
    }

    /**
     * @private
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
}
