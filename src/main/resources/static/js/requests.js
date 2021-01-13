/**
 * @typedef FractalConfiguration
 * @property w {number} The canvas width
 * @property h {number} The canvas height
 * @property x {number} The movement on the X axis
 * @property y {number} The movement on the Y axis
 * @property zoom {number} The value of the zoom
 * @property complex {{ r: number, i: number }} The complex number used to generate the fractal
 */

/**
 * Fetch the fractal fom the server
 * @param type {FractalTypes}
 * @param configuration {FractalConfiguration}
 * @returns {Promise<void>} Resolve when the image is loaded in the canvas
 */
async function getFractal(type, { w, h, x, y, zoom, complex, easterEgg = false }) {
    const url = buildUrl(type, w, h, x, y, zoom, complex, easterEgg);

    loading.hidden = false;
    const response = await fetch(url.toString());
    const image = await response.blob();

    return new Promise((resolve => {
        const htmlImage = new Image(w,h)
        htmlImage.onload = () => {
            canvasContext.clearRect(0, 0, canvas.width, canvas.height);
            canvasContext.drawImage(htmlImage, 0, 0)
            resolve();
            loading.hidden = true;
        };
        htmlImage.src = URL.createObjectURL(image);
    }));
}

function buildUrl(type, w, h, x, y, zoom, complex, isEasterrEgg) {
    const url = new URL("/fractal", window.location.origin);
    url.searchParams.append("type", type);
    url.searchParams.append("width", w);
    url.searchParams.append("height", h);
    if (type === FractalTypes.JULIA) {
        url.searchParams.append("r", complex.r);
        url.searchParams.append("i", complex.i);
    }
    if (isDefined(x) && isDefined(y) && isDefined(zoom)) {
        url.searchParams.append("x", x);
        url.searchParams.append("y", y);
        url.searchParams.append("zoom", zoom);
    }
    url.searchParams.append("isEasterEgg", isEasterrEgg);
    return url;
}