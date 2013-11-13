define(["jquery"],

    function ($) {

        $("html").on("clearanddraw", ".feeling-canvas", function () {
            reDrawFeeling(this);
        });

        $("html").on("clearanddraw", ".pie-canvas", function () {
            pie(this.id);
        });

        $("html").on("mouseover", "#feeling-value-good", function () {
            animateFeeling(this);
            $(this).next(".canvas-help-text").html("I like");
        });

        $("html").on("mouseover", "#feeling-value-neutral", function () {
            animateFeeling(this);
            $(this).next(".canvas-help-text").html("I don't care");
        });

        $("html").on("mouseover", "#feeling-value-bad", function () {
            animateFeeling(this);
            $(this).next(".canvas-help-text").html("I dislike");
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

        $("html").on("mouseout", "#feeling-value-good", function () {
            reDrawFeeling(this);
            $(this).next(".canvas-help-text").html("&nbsp;");
        });

        $("html").on("mouseout", "#feeling-value-neutral", function () {
            reDrawFeeling(this);
            $(this).next(".canvas-help-text").html("&nbsp;");
        });

        $("html").on("mouseout", "#feeling-value-bad", function () {
            reDrawFeeling(this);
            $(this).next(".canvas-help-text").html("&nbsp;");
        });

        function reDrawFeeling(canvas) {
            var context = canvas.getContext("2d");
            context.clearRect(0, 0, canvas.width, canvas.height);
            feeling($(canvas).attr("feeling-value"), $(canvas).attr("id"));
        }

        var good_color = "#2ECC71";
        var neutral_color = "#F1C40F";
        var bad_color = "#E74C3C";
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
                if (feelingValue == "good") {
                    context.fillStyle = good_color;
                } else if (feelingValue == "bad") {
                    context.fillStyle = bad_color;
                } else {
                    context.fillStyle = neutral_color;
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
                if (feelingValue == "good") {
                    curve = width / 8;
                } else if (feelingValue == "bad") {
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
            var good = $("#" + selector).data("good");
            var neutral = $("#" + selector).data("neutral");
            var bad = $("#" + selector).data("bad");
            var total = good + neutral + bad;
            if (total > 0) {
                var totalAngle = 2 * Math.PI;

                var goodAngle = {
                    start: 0 * totalAngle,
                    end: (good / total) * totalAngle
                };

                var neutralAngle = {
                    start: goodAngle.end,
                    end: goodAngle.end + (neutral / total) * totalAngle
                };

                var badAngle = {
                    start: neutralAngle.end,
                    end: totalAngle
                };

                if (goodAngle.start != goodAngle.end) {
                    context.fillStyle = good_color;
                    context.strokeStyle = "rgb(245,245,245)";
                    context.beginPath();
                    context.moveTo(centerX, centerY);
                    context.arc(centerX, centerY, (canvas.width / 2) - 10, goodAngle.start, goodAngle.end, false);
                    context.lineTo(centerX, centerY);
                    context.stroke();
                    context.fill();
                }

                if (neutralAngle.start != neutralAngle.end) {
                    context.fillStyle = neutral_color;
                    context.strokeStyle = "rgb(245,245,245)";
                    context.beginPath();
                    context.moveTo(centerX, centerY);
                    context.arc(centerX, centerY, (canvas.width / 2) - 10, neutralAngle.start, neutralAngle.end, false);
                    context.lineTo(centerX, centerY);
                    context.stroke();
                    context.fill();
                }

                if (badAngle.start != badAngle.end) {
                    context.fillStyle = bad_color;
                    context.strokeStyle = "rgb(245,245,245)";
                    context.beginPath();
                    context.moveTo(centerX, centerY);
                    context.arc(centerX, centerY, (canvas.width / 2) - 10, badAngle.start, badAngle.end, false);
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