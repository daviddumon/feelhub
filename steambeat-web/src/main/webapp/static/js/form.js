/* Copyright Steambeat 2012 */
function Form() {
    var THIS = this;

    $("#form_button").click(function (e) {
        e.preventDefault();
        e.stopPropagation();
        if (authentificated) {
            THIS.show();
        } else {
            document.location.href = root + "/login";
        }
    });

    $("#form_close").click(function (e) {
        e.preventDefault();
        e.stopImmediatePropagation();
        THIS.close();
    });
}

Form.prototype.show = function () {
    showBlanket();
    showForm();
    $("#form .form_text:last-child").focus();

    function showBlanket() {
        $("#form_blanket").css("height", $(window).height());
        $("#form_blanket").show();
    }

    function showForm() {
        $("#form").css("top", 50);
        $("#form").css("left", ($(window).width() - $("#form").width()) / 2);
        $("#form").css("height", $(window).height() - 100);
        $("#form").show();
    }
};

Form.prototype.close = function () {
    $("#form").hide();
    $("#form_blanket").hide();
};