const MOVE_OFFSET = 0.07;

const MoveActions = {
    UP: "up",
    DOWN: "down",
    LEFT: "left",
    RIGHT: "right",
    SET: "set",
};

function moveReducer(store, action, payload) {
    switch (action) {
        case MoveActions.UP:
            return store.setState({ ...store.getState(), y: store.getState().y - MOVE_OFFSET / store.getState().zoom });
        case MoveActions.DOWN:
            return store.setState({ ...store.getState(), y: store.getState().y + MOVE_OFFSET / store.getState().zoom });
        case MoveActions.LEFT:
            return store.setState({ ...store.getState(), x: store.getState().x - MOVE_OFFSET / store.getState().zoom });
        case MoveActions.RIGHT:
            return store.setState({ ...store.getState(), x: store.getState().x + MOVE_OFFSET / store.getState().zoom });
        case MoveActions.SET:
            return store.setState({ ...store.getState(), type: payload.type ,x: payload.x, y: payload.y, zoom: payload.zoom, r: payload.complex.r, i: payload.complex.i });
        default:
            return store.getState();
    }
}