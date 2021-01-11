const homeContainer = document.getElementById("home");

const upButton = document.getElementById("up-btn");
const downButton = document.getElementById("down-btn");
const leftButton = document.getElementById("left-btn");
const rightButton = document.getElementById("right-btn");
const zoomInButton = document.getElementById("zoom-in-btn");
const zoomOutButton = document.getElementById("zoom-out-btn");
const fullscreenButton = document.getElementById("fullscreen-btn");
const undoButton = document.getElementById("undo-btn");

const ACTION_TYPE = {
    MOVE: "move",
    ZOOM: "zoom",
    FULLSCREEN: "fullscreen",
    UNDO: "redo"
};

upButton.addEventListener("click", makeAction(ACTION_TYPE.MOVE, MoveActions.UP));
downButton.addEventListener("click", makeAction(ACTION_TYPE.MOVE, MoveActions.DOWN));
leftButton.addEventListener("click", makeAction(ACTION_TYPE.MOVE, MoveActions.LEFT));
rightButton.addEventListener("click", makeAction(ACTION_TYPE.MOVE, MoveActions.RIGHT));
zoomInButton.addEventListener("click", makeAction(ACTION_TYPE.ZOOM, ZoomActions.ZOOM_IN));
zoomOutButton.addEventListener("click", makeAction(ACTION_TYPE.ZOOM, ZoomActions.ZOOM_OUT));
fullscreenButton.addEventListener("click", makeAction(ACTION_TYPE.FULLSCREEN));
undoButton.addEventListener("click", makeAction(ACTION_TYPE.UNDO));

function makeAction(type, action) {
    return () => {
        switch (type) {
            case ACTION_TYPE.MOVE: moveReducer(imageState, action); break;
            case ACTION_TYPE.ZOOM: zoomReducer(imageState, action); break;
            case ACTION_TYPE.FULLSCREEN:
                homeContainer.requestFullscreen()
                    .then(() => {
                        resizeCanvas()
                        getFractal(canvas.width, canvas.height, imageState.state.x, imageState.state.y, imageState.state.zoom)
                    })
                    .catch(console.error);
                break;
            case ACTION_TYPE.UNDO: imageState.undo(); break;
        }
    }
}