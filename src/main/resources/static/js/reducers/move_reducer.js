const MOVE_OFFSET = 1;

const MoveActions = {
    UP: "up",
    DOWN: "down",
    LEFT: "left",
    RIGHT: "right",
};

function moveReducer(store, action) {
    switch (action) {
        case MoveActions.UP:
            return store.setState({ ...store.state, y: store.state.y - MOVE_OFFSET });
        case MoveActions.DOWN:
            return store.setState({ ...store.state, y: store.state.y + MOVE_OFFSET });
        case MoveActions.LEFT:
            return store.setState({ ...store.state, x: store.state.x - MOVE_OFFSET });
        case MoveActions.RIGHT:
            return store.setState({ ...store.state, x: store.state.x + MOVE_OFFSET });
        default:
            return store.state;
    }
}