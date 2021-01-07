 const canvas = document.getElementById("fractal_canvas");
 const loading = document.getElementById("loading");

        const canvasContext = canvas.getContext("2d");

        let x = 0;
        let y = 0;
        let zoom = 1;

        window.onload = () => {
            resizeCanvas();
            getFractal(canvas.width,canvas.height);
            window.addEventListener("keyup", (event) => {
                if (event.code.startsWith("Arrow")) {
                    switch (event.code) {
                        case "ArrowUp":
                            y--;
                            break;
                        case "ArrowDown":
                            y++;
                            break;
                        case "ArrowLeft":
                            x--;
                            break;
                        case "ArrowRight":
                            x++;
                            break;
                    }
                    getFractal(canvas.width,canvas.height,x,y,zoom,);
                }

                if (event.code === "NumpadAdd") {
                    zoom += 0.5
                    if (zoom === 0) {
                        zoom = 0.5;
                    }
                    getFractal(canvas.width,canvas.height,x,y,zoom,);
                } else if (event.code === "NumpadSubtract") {
                    zoom -= 0.5
                    if (zoom === 0) {
                        zoom = -0.5;
                    }
                    getFractal(canvas.width,canvas.height,x,y,zoom,);
                }
            });
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

            console.log(w,h)
        }

        function isDefined(variable) {
            return variable !== undefined && variable !== null;
        }

// resize the canvas to fill browser window dynamically
window.addEventListener('resize', () => {
    resizeCanvas();
    getFractal(canvas.width,canvas.height);
}
);

function resizeCanvas() {
    loading.hidden = false;
    canvas.width = window.innerWidth - 250;
    canvas.height = window.innerHeight;
}
