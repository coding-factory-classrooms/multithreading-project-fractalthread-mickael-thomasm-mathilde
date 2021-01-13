let ZOOM_OFFSET = 1;

const ZoomActions = {
    ZOOM_IN: "zoom_in",
    ZOOM_OUT: "zoom_out",
};

function zoomReducer(store, action) {
    let zoom = store.getState().zoom;
    switch (action) {
        case ZoomActions.ZOOM_IN:
            zoom *= ZOOM_OFFSET;
            ZOOM_OFFSET *= 2;
            // if (zoom === 0) {
            //     zoom = ZOOM_OFFSET;
            // }
            console.log(zoom)
            return store.setState({ ...store.getState(), zoom });
        case ZoomActions.ZOOM_OUT:
            zoom /= ZOOM_OFFSET;
            ZOOM_OFFSET *= 2;
            // if (zoom === 0) {
            //     zoom = -ZOOM_OFFSET;
            // }
            console.log(zoom)
            return store.setState({ ...store.getState(), zoom });
        default:
            return store.state;
    }
}