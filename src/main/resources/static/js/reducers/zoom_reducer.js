const ZOOM_OFFSET = 0.5;

const ZoomActions = {
    ZOOM_IN: "zoom_in",
    ZOOM_OUT: "zoom_out",
};

function zoomReducer(store, action) {
    let zoom = store.state.zoom;
    switch (action) {
        case MoveActions.ZOOM_IN:
            zoom += 0.5
            if (zoom === 0) {
                zoom = 0.5;
            }
            return store.setState({ ...store.state, zoom });
        case MoveActions.ZOOM_OUT:
            zoom -= 0.5
            if (zoom === 0) {
                zoom = -0.5;
            }
            return store.setState({ ...store.state, zoom });
        default:
            return store.state;
    }
}