define(["jquery"],

    function ($) {

        var container;
        var fill_color = "#FFFFFF";
        var base_line, left, right, width;
        var good_color = "#66CC33";
        var neutral_color = "#0033FF";
        var bad_color = "#FF3333";

        function draw(container_name, score, size) {
            width = size;
            right = size * 10;
            left = size * 5;
            base_line = size * 10;
            container = container_name;
            var canvas = document.getElementById(container);
            var context = canvas.getContext("2d");
            context.lineCap = "round";
            draw_background(context,score);
            draw_eyes(context);
            draw_mouth(context, score);
        }

        function draw_background(context, score) {
            //score ranges between -100 and 100
            if(score > 20) {
                context.fillStyle = good_color;
            } else if (score < -20) {
                context.fillStyle = bad_color;
            } else {
                context.fillStyle = neutral_color;
            }
            context.fillRect(0, 0, 60, 60);
        }

        function draw_eyes(context) {
            context.fillStyle = fill_color;
            context.beginPath();
            context.arc(left, base_line/2, width, 0, 2 * Math.PI, false);
            context.arc(right, base_line/2, width, 0, 2 * Math.PI, false);
            context.closePath();
            context.fill();
        }

        function draw_mouth(context, score) {
            context.strokeStyle = fill_color;
            context.lineWidth = width;
            context.beginPath();
            context.bezierCurveTo(
                left - (width/2) - (Math.abs(score/4)), base_line - (score / 8),
                (left + right) / 2, base_line + (score * 0.4),
                right + (width/2) + (Math.abs(score/4)), base_line - (score / 8));
            context.stroke();
        }

        return {
            draw: draw
        }
    });