function Form() {
    var THIS = this;

    this.createFirstBlock();

    if (typeof subjectId !== 'undefined') {
        $("#form_button").show();
    }

    $("#form_button").click(function (e) {
        e.preventDefault();
        e.stopPropagation();
        THIS.show();
    });

    $("#form_close").click(function (e) {
        e.preventDefault();
        e.stopImmediatePropagation();
        THIS.close();
    });
}

Form.prototype.createFirstBlock = function () {
    var data = {id:1};
    var form_block = ich.form_block(data);
    $("#form").append(form_block);
    this.addBehaviorFor(1);
};

Form.prototype.addBehaviorFor = function(id) {
    var THIS = this;
    $("#" + id).keypress(function (event) {
        var code = event.keyCode || event.which;
        if (code == 13) {
            event.preventDefault();
            event.stopImmediatePropagation();
            THIS.createBlockFrom(this, $(this).attr("id"));
        }
    });
};

Form.prototype.createBlockFrom = function(element, id) {
    id++;
    var data = {id:id};
    var form_block = ich.form_block(data);
    $(element).parent().after(form_block);
    this.addBehaviorFor(id);
    this.setFocusOn($("#" + id));
};


Form.prototype.setFocusOn = function(element) {
    $(element).focus();
};

Form.prototype.show = function () {
    showBlanket();
    showForm();
    setFocusOnLastFormText();

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

    function setFocusOnLastFormText() {
        $("#form .form_text:last-child").focus();
    }
};

Form.prototype.close = function () {
    $("#form").hide();
    $("#form_blanket").hide();
};

//$.getJSON(root + "/related?&fromId=" + subjectId + "&limit=6", function (data) {
//    var related_data = {"judgments":[]};
//
//    $.each(data, function (index, subject) {
//        var judgment_data = {
//            id:subject.id,
//            shortDescription:subject.shortDescription,
//            description:subject.description,
//            url:subject.url
//        };
//        related_data.judgments.push(judgment_data);
//    });
//
//    var subjects = ich.form_judgment(related_data);
//    $("#opinion_form_subject").append(subjects);
//
//    $("#opinion_form .judgment").click(function () {
//        $(this).children(".judgment_tag").toggleClass("good_border");
//        $(this).children(".judgment_tag").toggleClass("bad_border");
//        $(this).children(".feeling_smiley").toggleClass("good");
//        $(this).children(".feeling_smiley").toggleClass("bad");
//        if ($(this).children(".judgment_tag").hasClass("bad_border")) {
//            $(this).find(".judgment_tag > [name='feeling']").attr("value", "bad");
//        } else {
//            $(this).find(".judgment_tag > [name='feeling']").attr("value", "good");
//        }
//    });
//
//    $("#opinion_form_submit").click(function (event) {
//        event.preventDefault();
//        event.stopImmediatePropagation();
//        var form = $("#opinion_form").serializeArray();
//        $.post(root + "/opinions", form, function (data, textStatus, jqXHR) {
//            location.href = jqXHR.getResponseHeader("Location");
//        });
//        return false;
//    });
//});