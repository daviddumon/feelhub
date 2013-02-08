define(["jquery"], function ($) {

    function init() {

        $(".logout").click(function () {
            $.ajax({
                url: root + "/sessions",
                type: "DELETE",
                success: success,
                error: error
            });
        });

        $("body").on("submit", "form.sentimentform", function () {
            $.ajax({
                url: root + "/api/feeling/" + $(this).find("[name=feelingid]").val() + "/sentiment/" + $(this).find("[name=index]").val(),
                type: "PUT",
                data: $(this).serialize(),
                success: success,
                error: error
            });
            return false;
        });
    }

    function success() {
        document.location.reload();
    }

    function error() {

    }

    return {
        init: init
    };
});