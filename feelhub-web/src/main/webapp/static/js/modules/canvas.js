define(["jquery"],

    function ($) {

        $("html").on("clearanddraw", ".feeling-canvas", function () {
            var context = this.getContext("2d");
            context.clearRect(0, 0, this.width, this.height);
            feeling($(this).attr("feeling-value"), $(this).attr("id"));
        });

        $("html").on("clearanddraw", ".pie-canvas", function () {
            var context = this.getContext("2d");
            context.clearRect(0, 0, this.width, this.height);
            pie();
        });

        var good_color = "#66CC33";
        var neutral_color = "#0033FF";
        var bad_color = "#FF3333";
        var fill_color = "#FFFFFF";
        var no_color = "#EDEDED";
        var base_line, left, right, width;
        var width, height, eye_left_x, eye_left_y, eye_right_x, eye_right_y, mouth_left_x, mouth_right_x, mouth_y, thickness;

        function feeling(feelingValue, selector) {
            width = $("#" + selector).width();
            height = $("#" + selector).height();
            $("#" + selector).attr("feeling-value", feelingValue);
            eye_left_y = width * 0.33;
            eye_left_x = width * 0.33;
            eye_right_y = width * 0.33;
            eye_right_x = width * 0.66;
            mouth_left_x = width * 0.33;
            mouth_right_x = width * 0.66;
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
                context.arc(eye_left_x, eye_left_y, thickness, 0, 2 * Math.PI, false);
                context.arc(eye_right_x, eye_right_y, thickness, 0, 2 * Math.PI, false);
                context.closePath();
                context.fill();
            }

            function draw_mouth(context, feelingValue) {
                var curve = 0;
                if (feelingValue == "good") {
                    curve = width / 6;
                } else if (feelingValue == "bad") {
                    curve = -width /6;
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

        function pie() {
            var canvas = document.getElementById("pie");
            canvas.width = $("#pie").width();
            canvas.height = $("#pie").height();
            var context = canvas.getContext("2d");
            centerX = canvas.width / 2;
            centerY = canvas.height / 2;
            context.lineCap = "round";
            context.lineWidth = 5;

            var statisticsTotal = statistics.good + statistics.neutral + statistics.bad;
            if (statisticsTotal > 0) {
                var totalAngle = 2 * Math.PI;

                var goodAngle = {
                    start: 0 * totalAngle,
                    end: (statistics.good / statisticsTotal) * totalAngle
                };

                var neutralAngle = {
                    start: goodAngle.end,
                    end: goodAngle.end + (statistics.neutral / statisticsTotal) * totalAngle
                };

                var badAngle = {
                    start: neutralAngle.end,
                    end: totalAngle
                };

                if (goodAngle.start != goodAngle.end) {
                    context.fillStyle = good_color;
                    context.strokeStyle = "F5F5F5";
                    context.beginPath();
                    context.moveTo(centerX, centerY);
                    context.arc(centerX, centerY, (canvas.width / 2) - 10, goodAngle.start, goodAngle.end, false);
                    context.lineTo(centerX, centerY);
                    context.stroke();
                    context.fill();
                }

                if (neutralAngle.start != neutralAngle.end) {
                    context.fillStyle = neutral_color;
                    context.strokeStyle = "F5F5F5";
                    context.beginPath();
                    context.moveTo(centerX, centerY);
                    context.arc(centerX, centerY, (canvas.width / 2) - 10, neutralAngle.start, neutralAngle.end, false);
                    context.lineTo(centerX, centerY);
                    context.stroke();
                    context.fill();
                }

                if (badAngle.start != badAngle.end) {
                    context.fillStyle = bad_color;
                    context.strokeStyle = "F5F5F5";
                    context.beginPath();
                    context.moveTo(centerX, centerY);
                    context.arc(centerX, centerY, (canvas.width / 2) - 10, badAngle.start, badAngle.end, false);
                    context.lineTo(centerX, centerY);
                    context.stroke();
                    context.fill();
                }

                context.fillStyle = "F5F5F5";
                context.beginPath();
                context.moveTo(centerX, centerY);
                context.arc(centerX, centerY, canvas.width / 10, 0, 2 * Math.PI, false);
                context.fill();
            } else {
                context.fillStyle = "F5F5F5";
                context.strokeStyle = "FFFFFF";
                context.beginPath();
                context.moveTo(centerX, centerY);
                context.arc(centerX, centerY, (canvas.width / 2) - 10, 0, 2 * Math.PI, false);
                context.stroke();
                context.fill();
            }
        }

        return {
            feeling: feeling,
            pie: pie
        }
    });