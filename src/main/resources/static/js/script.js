const imageState = new Store({
    type: FractalTypes.MANDELBROT,
    x: 0,
    y: 0,
    zoom: 1,
    move_step: 5,
    zoom_step: 5,
});
let unsubscribeStateChange = null;

const canvas = document.getElementById("fractal_canvas");
const loading = document.getElementById("loading");

const canvasContext = canvas.getContext("2d");

window.onload = () => {
    unsubscribeStateChange = imageState.subscribe((state) => {
        getFractal(state.type, canvas.width,canvas.height, state.x, state.y, state.zoom);
    });

    resizeCanvas();
    getFractal(imageState.getState().type, canvas.width,canvas.height);

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
    getFractal(imageState.getState().type, canvas.width,canvas.height);
});

window.onunload = () => {
    imageState.unsubscribe(unsubscribeStateChange);
}
