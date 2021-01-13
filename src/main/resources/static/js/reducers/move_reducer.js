const MOVE_OFFSET = 0.07;

const MoveActions = {
    UP: "up",
    DOWN: "down",
    LEFT: "left",
    RIGHT: "right",
};

function moveReducer(store, action) {
    switch (action) {
        case MoveActions.UP:
            return store.setState({ ...store.getState(), y: store.getState().y - MOVE_OFFSET / store.getState().zoom });
        case MoveActions.DOWN:
            return store.setState({ ...store.getState(), y: store.getState().y + MOVE_OFFSET / store.getState().zoom });
        case MoveActions.LEFT:
            return store.setState({ ...store.getState(), x: store.getState().x - MOVE_OFFSET / store.getState().zoom });
        case MoveActions.RIGHT:
            return store.setState({ ...store.getState(), x: store.getState().x + MOVE_OFFSET / store.getState().zoom });
        default:
            return store.getState();
    }
}