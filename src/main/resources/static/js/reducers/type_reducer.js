const FractalTypes = {
    MANDELBROT: "mandelbrot",
    JULIA: "julia",
};

function typeReducer(store, action) {
    return store.setState({ ...store.getState(), type: action, x: 0, y: 0, zoom: 1 })
}