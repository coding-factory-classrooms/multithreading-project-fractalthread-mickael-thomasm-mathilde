function isDefined(variable) {
    return variable !== undefined && variable !== null;
}

function resizeCanvas() {
    loading.hidden = false;
    canvas.width = window.innerWidth - 250;
    canvas.height = window.innerHeight;
}