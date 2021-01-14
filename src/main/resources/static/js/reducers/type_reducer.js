const FractalTypes = {
    MANDELBROT: "mandelbrot",
    JULIA: "julia",
};

function typeReducer(store, action) {
    return store.setState({ ...store.getState(), ...initialState, type: action })
}