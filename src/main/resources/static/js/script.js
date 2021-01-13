const imageState = new Store({
    type: FractalTypes.JULIA,
    x: 0,
    y: 0,
    zoom: 1,
    r: -0.59999999999999,
    i: 0.42999999999
});
let unsubscribeStateChange = null;

const canvas = document.getElementById("fractal_canvas");
const loading = document.getElementById("loading");

const canvasContext = canvas.getContext("2d");

window.onload = () => {
    unsubscribeStateChange = imageState.subscribe((state) => {
        console.log(state)
        getFractal(
            state.type,
            { w: canvas.width, h: canvas.height },
            { x: state.x, y: state.y, zoom: state.zoom },
            { r: state.r, i: state.i }
        );
    });

    resizeCanvas();
    getFractal(
        imageState.getState().type,
        { w: canvas.width, h: canvas.height },
        { x: 0, y: 0, zoom: 1 },
        { r: imageState.getState().r, i: imageState.getState().i }
    );

    window.addEventListener("keyup", (event) => {
        switch (event.code) {
            case "ArrowUp":
                moveReducer(imageState, MoveActions.UP)
                break;
            case "ArrowDown":
                moveReducer(imageState, MoveActions.DOWN)
                break;
            case "ArrowLeft":
                moveReducer(imageState, MoveActions.LEFT)
                break;
            case "ArrowRight":
                moveReducer(imageState, MoveActions.RIGHT)
                break;
            case "NumpadAdd":
                zoomReducer(imageState, MoveActions.ZOOM_IN);
                break;
            case "NumpadSubstract":
                zoomReducer(imageState, MoveActions.ZOOM_OUT);
                break;
        }
    });
}

// resize the canvas to fill browser window dynamically
window.addEventListener('resize', () => {
    resizeCanvas();
    getFractal(
        imageState.getState().type,
        { w: canvas.width, h: canvas.height },
        { x: 0, y: 0, zoom: 1 },
        { r: imageState.getState().r, i: imageState.getState().i }
    );
});

window.onunload = () => {
    imageState.unsubscribe(unsubscribeStateChange);
}
