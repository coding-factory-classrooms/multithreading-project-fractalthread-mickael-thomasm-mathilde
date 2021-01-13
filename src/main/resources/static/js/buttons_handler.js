const homeContainer = document.getElementById("home");

const upButton = document.getElementById("up-btn");
const downButton = document.getElementById("down-btn");
const leftButton = document.getElementById("left-btn");
const rightButton = document.getElementById("right-btn");
const zoomInButton = document.getElementById("zoom-in-btn");
const zoomOutButton = document.getElementById("zoom-out-btn");
const fullscreenButton = document.getElementById("fullscreen-btn");
const undoButton = document.getElementById("undo-btn");
const exportButton = document.getElementById("export-btn");
const mandelbrotButton = document.getElementById("mandelbrot");
const juliaButton = document.getElementById("julia");

const ACTION_TYPE = {
    MOVE: "move",
    ZOOM: "zoom",
    FULLSCREEN: "fullscreen",
    UNDO: "redo",
    CHANGE_TYPE: "change_type"
};

upButton.addEventListener("click", makeAction(ACTION_TYPE.MOVE, MoveActions.UP));
downButton.addEventListener("click", makeAction(ACTION_TYPE.MOVE, MoveActions.DOWN));
leftButton.addEventListener("click", makeAction(ACTION_TYPE.MOVE, MoveActions.LEFT));
rightButton.addEventListener("click", makeAction(ACTION_TYPE.MOVE, MoveActions.RIGHT));
zoomInButton.addEventListener("click", makeAction(ACTION_TYPE.ZOOM, ZoomActions.ZOOM_IN));
zoomOutButton.addEventListener("click", makeAction(ACTION_TYPE.ZOOM, ZoomActions.ZOOM_OUT));
fullscreenButton.addEventListener("click", makeAction(ACTION_TYPE.FULLSCREEN));
undoButton.addEventListener("click", makeAction(ACTION_TYPE.UNDO));
mandelbrotButton.addEventListener("change", makeAction(ACTION_TYPE.CHANGE_TYPE, FractalTypes.MANDELBROT));
juliaButton.addEventListener("change", makeAction(ACTION_TYPE.CHANGE_TYPE, FractalTypes.JULIA));

exportButton.addEventListener("click", () => {
    const data = canvas
        .toDataURL("image/jpg")
        .replace(/^data:image\/png/,'data:application/octet-stream');
    const a = document.createElement("a");
    a.setAttribute("download", "fractal.png");
    a.setAttribute("href", data);
    a.click();
});

function makeAction(type, action) {
    return () => {
        switch (type) {
            case ACTION_TYPE.MOVE: moveReducer(imageState, action); break;
            case ACTION_TYPE.ZOOM: zoomReducer(imageState, action); break;
            case ACTION_TYPE.FULLSCREEN:
                homeContainer.requestFullscreen()
                    .then(() => {
                        resizeCanvas()
                        getFractal(
                            imageState.getState().type,
                            {
                                w: canvas.width,
                                h: canvas.height,
                                x: imageState.getState().x,
                                y: imageState.getState().y,
                                zoom: imageState.getState().zoom,
                                complex: {
                                    r: imageState.getState().r,
                                    i: imageState.getState().i
                                }
                            }
                        );
                    })
                    .catch(console.error);
                break;
            case ACTION_TYPE.CHANGE_TYPE: typeReducer(imageState, action); break;
            case ACTION_TYPE.UNDO: imageState.undo(); break;
        }
    }
}