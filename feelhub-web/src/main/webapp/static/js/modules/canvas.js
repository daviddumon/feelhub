define(["jquery"],

    function ($) {

        $("html").on("clearanddraw", "canvas", function () {
            var context = this.getContext("2d");
            context.clearRect(0, 0, this.width, this.height);
            youfeel($(this).attr("id"), $(this).attr("score"));
        });

        var container;
        var fill_color = "#FFFFFF";
        var base_line, left, right, width;
        var good_color = "#66CC33";
        var neutral_color = "#0033FF";
        var bad_color = "#FF3333";
        var no_color = "#EDEDED";
        var width, height, eye_left_x, eye_left_y, eye_right_x, eye_right_y, mouth_left_x, mouth_right_x, mouth_y, thickness;

        function youfeel(container_name, score) {
            width = $("#" + container_name).width();
            height = $("#" + container_name).height();
            $("#" + container_name).attr("score", score);
            if (score) {
                eye_left_y = width * 0.33;
                eye_left_x = width * 0.33;
                eye_right_y = width * 0.33;
                eye_right_x = width * 0.66;
                mouth_left_x = width * 0.33;
                mouth_right_x = width * 0.66;
                mouth_y = width * 0.66;
                thickness = width / 15;
                var canvas = document.getElementById(container_name);
                canvas.width = width;
                canvas.height = height;
                var context = canvas.getContext("2d");
                context.lineCap = "round";
                draw_background(context, score);
                draw_eyes(context);
                draw_mouth(context, score);
            } else {
                draw_question_mark(context, width, container_name);
            }
        }

        function draw_background(context, score) {
            //score ranges between -100 and 100
            if (score > 20) {
                context.fillStyle = good_color;
            } else if (score < -20) {
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

        function draw_mouth(context, score) {
            context.strokeStyle = fill_color;
            context.lineWidth = thickness;
            context.beginPath();
            context.bezierCurveTo(
                mouth_left_x - (thickness / 2) - (Math.abs(score / 4)), mouth_y - (score / 8),
                width / 2, mouth_y + (score * 0.4),
                mouth_right_x + (thickness / 2) + (Math.abs(score / 4)), mouth_y - (score / 8));
            context.stroke();
        }

        function draw_question_mark(context, size, container_name) {
            container = container_name;
            var canvas = document.getElementById(container);
            canvas.width = size;
            canvas.height = size;
            var context = canvas.getContext("2d");
            context.lineCap = "round";
            context.fillStyle = no_color;
            context.strokeStyle = fill_color;
            context.lineWidth = size / 15;

            context.fillRect(0, 0, size, size);

            context.beginPath();
            context.arc(size * 0.5, size * 0.3, size / 5, 1.1 * Math.PI, 0.5 * Math.PI, false);
            context.lineTo(size * 0.5, size * 0.6);
            context.stroke();

            context.beginPath();
            context.fillStyle = fill_color;
            context.arc(size * 0.5, size * 0.8, size / 12, 0, 2 * Math.PI, false);
            context.closePath();
            context.fill();
        }

        return {
            youfeel: youfeel
        }
    });