define(["jquery"],

    function ($) {

        $("html").on("clearanddraw", ".feeling-canvas", function () {
            reDrawFeeling(this);
        });

        $("html").on("clearanddraw", ".pie-canvas", function () {
            pie(this.id);
        });

        $("html").on("mouseover", "#feeling-value-happy", function () {
            animateFeeling(this);
            $(this).next(".canvas-help-text").html("Happy");
        });

        $("html").on("mouseover", "#feeling-value-bored", function () {
            animateFeeling(this);
            $(this).next(".canvas-help-text").html("Bored");
        });

        $("html").on("mouseover", "#feeling-value-sad", function () {
            animateFeeling(this);
            $(this).next(".canvas-help-text").html("Sad");
        });

        function animateFeeling(canvas) {
            var spread = 0;
            var interval = setInterval(function () {
                spread += 0.005;
                var context = canvas.getContext("2d");
                context.clearRect(0, 0, canvas.width, canvas.height);
                feeling($(canvas).attr("feeling-value"), $(canvas).attr("id"), spread);
            }, 20);
            setTimeout(function () {
                clearInterval(interval);
            }, 200);
        }

        $("html").on("mouseout", "#feeling-value-happy", function () {
            reDrawFeeling(this);
            $(this).next(".canvas-help-text").html("&nbsp;");
        });

        $("html").on("mouseout", "#feeling-value-bored", function () {
            reDrawFeeling(this);
            $(this).next(".canvas-help-text").html("&nbsp;");
        });

        $("html").on("mouseout", "#feeling-value-sad", function () {
            reDrawFeeling(this);
            $(this).next(".canvas-help-text").html("&nbsp;");
        });

        function reDrawFeeling(canvas) {
            var context = canvas.getContext("2d");
            context.clearRect(0, 0, canvas.width, canvas.height);
            feeling($(canvas).attr("feeling-value"), $(canvas).attr("id"));
        }

        var happy_color = "#2ECC71";
        var bored_color = "#F1C40F";
        var sad_color = "#E74C3C";
        var fill_color = "#FFFFFF";
        var no_color = "#EDEDED";
        var base_line, left, right, width;
        var width, height, eye_left_x, eye_left_y, eye_right_x, eye_right_y, mouth_left_x, mouth_right_x, mouth_y, thickness;

        function feeling(feelingValue, selector, spread) {
            spread = typeof spread !== 'undefined' ? spread : 0;
            width = $("#" + selector).width();
            height = $("#" + selector).height();
            $("#" + selector).attr("feeling-value", feelingValue);
            eye_left_y = width * 0.33;
            eye_left_x = width * 0.33;
            eye_right_y = width * 0.33;
            eye_right_x = width * 0.66;
            mouth_left_x = width * (0.45 - spread);
            mouth_right_x = width * (0.55 + spread);
            mouth_y = width * 0.66;
            thickness = width / 15;
            var canvas = document.getElementById(selector);
            canvas.width = width;
            canvas.height = height;
            var context = canvas.getContext("2d");
            context.lineCap = "round";
            draw_background(context, feelingValue);
            draw_eyes(context);
            draw_mouth(context, feelingValue);

            function draw_background(context, feelingValue) {
                if (feelingValue == "happy") {
                    context.fillStyle = happy_color;
                } else if (feelingValue == "sad") {
                    context.fillStyle = sad_color;
                } else {
                    context.fillStyle = bored_color;
                }
                context.fillRect(0, 0, width, height);
            }

            function draw_eyes(context) {
                context.fillStyle = fill_color;
                context.beginPath();
                context.arc(eye_left_x, eye_left_y, thickness + (20 * spread), 0, 2 * Math.PI, false);
                context.arc(eye_right_x, eye_right_y, thickness + (20 * spread), 0, 2 * Math.PI, false);
                context.closePath();
                context.fill();
            }

            function draw_mouth(context, feelingValue) {
                var curve = 0;
                if (feelingValue == "happy") {
                    curve = width / 8;
                } else if (feelingValue == "sad") {
                    curve = -width / 8;
                }

                context.strokeStyle = fill_color;
                context.lineWidth = thickness;
                context.beginPath();
                context.bezierCurveTo(
                    mouth_left_x - (thickness / 2) - (Math.abs(width / 6)), mouth_y - curve / 2,
                    width / 2, mouth_y + curve * 2,
                    mouth_right_x + (thickness / 2) + (Math.abs(width / 6)), mouth_y - curve / 2);
                context.stroke();
            }
        }

        function pie(selector) {
            var canvas = document.getElementById(selector);
            canvas.width = $("#" + selector).width();
            canvas.height = $("#" + selector).width();
            var context = canvas.getContext("2d");
            context.clearRect(0, 0, this.width, this.height);
            centerX = canvas.width / 2;
            centerY = canvas.height / 2;
            context.lineCap = "round";
            context.lineWidth = 5;
            var happy = $("#" + selector).data("happy");
            var bored = $("#" + selector).data("bored");
            var sad = $("#" + selector).data("sad");
            var total = happy + bored + sad;
            if (total > 0) {
                var totalAngle = 2 * Math.PI;

                var happyAngle = {
                    start: 0 * totalAngle,
                    end: (happy / total) * totalAngle
                };

                var boredAngle = {
                    start: happyAngle.end,
                    end: happyAngle.end + (bored / total) * totalAngle
                };

                var sadAngle = {
                    start: boredAngle.end,
                    end: totalAngle
                };

                if (happyAngle.start != happyAngle.end) {
                    context.fillStyle = happy_color;
                    context.strokeStyle = "rgb(245,245,245)";
                    context.beginPath();
                    context.moveTo(centerX, centerY);
                    context.arc(centerX, centerY, (canvas.width / 2) - 10, happyAngle.start, happyAngle.end, false);
                    context.lineTo(centerX, centerY);
                    context.stroke();
                    context.fill();
                }

                if (boredAngle.start != boredAngle.end) {
                    context.fillStyle = bored_color;
                    context.strokeStyle = "rgb(245,245,245)";
                    context.beginPath();
                    context.moveTo(centerX, centerY);
                    context.arc(centerX, centerY, (canvas.width / 2) - 10, boredAngle.start, boredAngle.end, false);
                    context.lineTo(centerX, centerY);
                    context.stroke();
                    context.fill();
                }

                if (sadAngle.start != sadAngle.end) {
                    context.fillStyle = sad_color;
                    context.strokeStyle = "rgb(245,245,245)";
                    context.beginPath();
                    context.moveTo(centerX, centerY);
                    context.arc(centerX, centerY, (canvas.width / 2) - 10, sadAngle.start, sadAngle.end, false);
                    context.lineTo(centerX, centerY);
                    context.stroke();
                    context.fill();
                }

                context.fillStyle = "rgb(245,245,245)";
                context.beginPath();
                context.moveTo(centerX, centerY);
                context.arc(centerX, centerY, canvas.width / 10, 0, 2 * Math.PI, false);
                context.fill();
            } else {
                context.fillStyle = "rgb(255,255,255)";
                context.strokeStyle = "rgb(255,255,255)";
                context.beginPath();
                context.moveTo(centerX, centerY);
                context.arc(centerX, centerY, (canvas.width / 2) - 10, 0, 2 * Math.PI, false);
                context.stroke();
                context.fill();

                context.fillStyle = "rgb(245,245,245)";
                context.beginPath();
                context.moveTo(centerX, centerY);
                context.arc(centerX, centerY, canvas.width / 10, 0, 2 * Math.PI, false);
                context.fill();
            }
        }

        return {
            feeling: feeling,
            pie: pie
        }
    });