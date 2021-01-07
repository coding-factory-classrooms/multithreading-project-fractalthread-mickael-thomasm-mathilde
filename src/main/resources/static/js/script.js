 const canvas = document.getElementById("fractal_canvas");
        const canvasContext = canvas.getContext("2d");

        let x = 0;
        let y = 0;
        let zoom = 1;

        window.onload = () => {
            getFractal();

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
                    getFractal(x, y, zoom);
                }

                if (event.code === "NumpadAdd") {
                    zoom += 0.5
                    if (zoom === 0) {
                        zoom = 0.5;
                    }
                    getFractal(x, y, zoom);
                } else if (event.code === "NumpadSubtract") {
                    zoom -= 0.5
                    if (zoom === 0) {
                        zoom = -0.5;
                    }
                    getFractal(x, y, zoom);
                }
            });
        }

        async function getFractal(x, y, zoom) {
            const url = new URL("/fractal", window.location.origin);
            if (isDefined(x) && isDefined(y) && isDefined(zoom)) {
                url.searchParams.append("x", x);
                url.searchParams.append("y", y);
                url.searchParams.append("zoom", zoom);
            }

            const response = await fetch(url.toString());
            const image = await response.blob();

            const htmlImage = new Image(1000, 1000)
            htmlImage.onload = () => {
                canvasContext.clearRect(0, 0, canvas.width, canvas.height);
                canvasContext.drawImage(htmlImage, 0, 0)
            };
            htmlImage.src = URL.createObjectURL(image);
        }

        function isDefined(variable) {
            return variable !== undefined && variable !== null;
        }