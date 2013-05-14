define(["jquery"], function($) {

    function display_messages() {

            $(".message").each(function (index, message) {
                var second_timer = parseInt($(message).data("second-timer")) * 1000;

                if (second_timer > 0) {
                    $(message).fadeIn(600).delay(second_timer).fadeOut(600);
                } else {
                    $(message).fadeIn(600);
                }

                $(message).click(function () {
                    $(message).hide();
                });

                $(message).hover(function () {
                    $(message).find(".message-close").show();
                }, function () {
                    $(message).find(".message-close").hide();
                });
            });
        }

    return {
        display_messages: display_messages
    };

});