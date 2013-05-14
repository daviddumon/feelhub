define(["jquery"],

    function ($) {

        var container;
        var fill_color = "#FFFFFF";
        var base_line, left, right, width;
        var good_color = "#66CC33";
        var neutral_color = "#0033FF";
        var bad_color = "#FF3333";
        var width, height, eye_left_x, eye_left_y, eye_right_x, eye_right_y, mouth_left_x, mouth_right_x, mouth_y, thickness;

        function draw(container_name, score, size) {
            width = size;
            height = size;
            eye_left_y = size * 0.33;
            eye_left_x = size * 0.33;
            eye_right_y = size * 0.33;
            eye_right_x = size * 0.66;
            mouth_left_x = size * 0.33;
            mouth_right_x = size * 0.66;
            mouth_y = size * 0.66;
            thickness = size / 15;
            container = container_name;
            var canvas = document.getElementById(container);
            var context = canvas.getContext("2d");
            context.lineCap = "round";
            draw_background(context, score);
            draw_eyes(context);
            draw_mouth(context, score);
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

        return {
            draw: draw
        }
    });