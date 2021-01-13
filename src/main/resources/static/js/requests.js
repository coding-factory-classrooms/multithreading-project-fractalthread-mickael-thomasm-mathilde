async function getFractal(type, { w, h }, { x, y, zoom }, complex) {
    const url = new URL("/fractal", window.location.origin);
    url.searchParams.append("type", type);
    url.searchParams.append("width", w);
    url.searchParams.append("height", h);
    if (type === FractalTypes.JULIA) {
        url.searchParams.append("i", complex.i);
        url.searchParams.append("r", complex.r);
    }
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