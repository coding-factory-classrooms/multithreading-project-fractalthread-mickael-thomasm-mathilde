const imageState = new Store({
    width: 0,
    height: 0,
    x: 0,
    y: 0,
    zoom: 1
});
let unsubscribeStateChange = null;

const canvas = document.getElementById("fractal_canvas");
const loading = document.getElementById("loading");

const canvasContext = canvas.getContext("2d");

window.onload = () => {
    unsubscribeStateChange = imageState.subscribe((state) => {
        getFractal(canvas.width,canvas.height, state.x, state.y, state.zoom);
    });

    resizeCanvas();
    getFractal(canvas.width,canvas.height);

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

window.onunload = () => {
    imageState.unsubscribe(unsubscribeStateChange);
}

async function getFractal(w,h,x,y,zoom) {
    const url = new URL("/fractal", window.location.origin);
    url.searchParams.append("width",w);
    url.searchParams.append("height",h);
    if (isDefined(x) && isDefined(y) && isDefined(zoom)) {
        url.searchParams.append("x", x);
        url.searchParams.append("y", y);
        url.searchParams.append("zoom", zoom);
    }

    const response = await fetch(url.toString());
    const image = await response.blob();

    const htmlImage = new Image(w,h)
    htmlImage.onload = () => {
        canvasContext.clearRect(0, 0, canvas.width, canvas.height);
        canvasContext.drawImage(htmlImage, 0, 0)
    };
    htmlImage.src = URL.createObjectURL(image);
    loading.hidden = true;
}

function isDefined(variable) {
    return variable !== undefined && variable !== null;
}

// resize the canvas to fill browser window dynamically
window.addEventListener('resize', () => {
    resizeCanvas();
    getFractal(canvas.width,canvas.height);
});

function resizeCanvas() {
    loading.hidden = false;
    canvas.width = window.innerWidth - 250;
    canvas.height = window.innerHeight;
}
