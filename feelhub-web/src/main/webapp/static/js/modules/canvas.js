define([],

    function () {

        var container = "canvas-sentiment";
        var fill_color = "#FFFFFF";
        var base_line = 80;
        var left = 40;
        var right = 80;
        var width = 8;

        function draw(data, score) {
            var canvas = document.getElementById(container);
            var context = canvas.getContext('2d');
            context.lineCap = 'round';
            draw_background(score);
            draw_eyes(context);
            draw_mouth(context, score);
        }

        function draw_background(score) {
            //score ranges between -100 and 100
            var background;
            if(score > 20) {
                background = "good";
            } else if (score < -20) {
                background = "bad";
            } else {
                background = "neutral";
            }
            $("#" + container).addClass(background);
            $("#" + container + " img").addClass(background);
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