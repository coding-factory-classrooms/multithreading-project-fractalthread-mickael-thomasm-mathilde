const initialState = {
    type: FractalTypes.MANDELBROT,
    x: 0,
    y: 0,
    zoom: 1,
    r: -0.59999999999999,
    i: 0.42999999999
};
const imageState = new Store(initialState);
let unsubscribeStateChange = null;
let keyPressed = {};

const canvas = document.getElementById("fractal_canvas");
const loading = document.getElementById("loading");

const canvasContext = canvas.getContext("2d");

window.onload = () => {
    unsubscribeStateChange = imageState.subscribe((state) => {
        const isJuliaFractal = state.type === FractalTypes.JULIA
        mandelbrotButton.checked = !isJuliaFractal;
        juliaButton.checked = isJuliaFractal;

        getFractal(
            state.type,
            {
                w: canvas.width,
                h: canvas.height,
                x: state.x,
                y: state.y,
                zoom: state.zoom,
                complex: {
                    r: state.r,
                    i: state.i
                },
                easterEgg: state.easterEgg
            }
        );
    });

    resizeCanvas();
    getFractal(
        imageState.getState().type,
        {
            w: canvas.width,
            h: canvas.height,
            x: 0,
            y: 0,
            zoom: 1,
            complex: {
                r: imageState.getState().r,
                i: imageState.getState().i
            }
        }
    );

    window.addEventListener("keydown", (event) => {
        keyPressed[event.code] = true;
        if (keyPressed["ControlLeft"] && keyPressed["ShiftLeft"] && keyPressed["Digit2"]) {
            moveReducer(imageState, MoveActions.SET, {
                x: -0.006015625000000002,
                y: -0.06617187499999999,
                zoom: 128,
                complex: {
                    r: 0.285,
                    i: 0.0105
                },
                easterEgg: true
            });
            keyPressed = {};
        }
    });

    window.addEventListener("keyup", (event) => {
        keyPressed[event.code] = false;
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
                zoomReducer(imageState, ZoomActions.ZOOM_IN);
                break;
            case "NumpadSubstract":
                zoomReducer(imageState, ZoomActions.ZOOM_OUT);
                break;
        }
    });
}

// resize the canvas to fill browser window dynamically
window.addEventListener('resize', () => {
    resizeCanvas();
    getFractal(
        imageState.getState().type,
        {
            w: canvas.width,
            h: canvas.height,
            x: 0, y: 0, zoom: 1,
            complex: {
                r: initialState.r,
                i: initialState.i
            }
        }
    )
});

window.onunload = () => {
    imageState.unsubscribe(unsubscribeStateChange);
}
