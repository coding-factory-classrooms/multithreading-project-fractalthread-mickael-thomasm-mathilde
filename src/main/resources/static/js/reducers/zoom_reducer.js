const ZOOM_OFFSET = 0.5;

const ZoomActions = {
    ZOOM_IN: "zoom_in",
    ZOOM_OUT: "zoom_out",
};

function zoomReducer(store, action) {
    let zoom = store.state.zoom;
    switch (action) {
        case MoveActions.ZOOM_IN:
            zoom += ZOOM_OFFSET
            if (zoom === 0) {
                zoom = ZOOM_OFFSET;
            }
            return store.setState({ ...store.getState(), zoom });
        case MoveActions.ZOOM_OUT:
            zoom -= ZOOM_OFFSET;
            if (zoom === 0) {
                zoom = -ZOOM_OFFSET;
            }
            return store.setState({ ...store.getState(), zoom });
        default:
            return store.state;
    }
}