const MOVE_OFFSET = 6;

const MoveActions = {
    UP: "up",
    DOWN: "down",
    LEFT: "left",
    RIGHT: "right",
};

function moveReducer(store, action) {
    switch (action) {
        case MoveActions.UP:
            return store.setState({ ...store.getState(), y: store.getState().y - MOVE_OFFSET });
        case MoveActions.DOWN:
            return store.setState({ ...store.getState(), y: store.getState().y + MOVE_OFFSET });
        case MoveActions.LEFT:
            return store.setState({ ...store.getState(), x: store.getState().x - MOVE_OFFSET });
        case MoveActions.RIGHT:
            return store.setState({ ...store.getState(), x: store.getState().x + MOVE_OFFSET });
        default:
            return store.getState();
    }
}