define(["jquery", "view/messages-view"], function ($, view) {

    var message_index = 0;
    var container = "#messages";

    function init() {
        view.clear();
        add_messages_from_storage();
        add_messages_from_head();
        add_messages_from_cookies();
        append_message_behavior();
    }

    function add_messages_from_storage() {
        var messages = JSON.parse(localStorage.getItem("messages"));
        $(messages).each(function (index, message) {
            view.render({
                feeling: message.feeling,
                text: message.text,
                timer: message.timer,
                index: message_index++
            });
        });
        localStorage.removeItem("messages");
    }

    function add_messages_from_head() {
        $(initial_messages).each(function (index, message) {
            view.render({
                feeling: message.feeling,
                text: message.text,
                timer: message.timer,
                index: message_index++
            });
        });
    }

    function add_messages_from_cookies() {
        var message = JSON.parse(getCookie("message"));
        if (message) {
            view.render({
                feeling: message.feeling,
                text: message.text,
                timer: message.timer,
                index: message_index++
            });
        }
        document.cookie = "message=;expires=Thu, 01 Jan 1970 00:00:00 GMT;path=/;domain=" + cookie;

        function getCookie(sKey) {
            return unescape(document.cookie.replace(new RegExp("(?:(?:^|.*;)\\s*" + escape(sKey).replace(/[\-\.\+\*]/g, "\\$&") + "\\s*\\=\\s*([^;]*).*$)|^.*$"), "$1")) || null;
        }
    }


    function append_message_behavior() {
        $(container).on("click", ".message", function () {
            $(this).hide();
        });

        $(container).on("mouseover", ".message", function () {
            $(this).find(".message-close").show();
        });

        $(container).on("mouseout", ".message", function () {
            $(this).find(".message-close").hide();
        });
    }

    function store_message(feeling, text, timer) {
        var messages = JSON.parse(localStorage.getItem("messages"));
        if (messages == null) {
            messages = new Array();
        }
        messages.push({
            feeling: feeling,
            text: text,
            timer: timer,
        });
        localStorage.setItem("messages", JSON.stringify(messages));
    }

    function draw_message(feeling, text, timer) {
        view.render({
            feeling: feeling,
            text: text,
            timer: timer,
            index: message_index++
        });
    }

    return {
        init: init,
        store_message: store_message,
        draw_message: draw_message
    };
});