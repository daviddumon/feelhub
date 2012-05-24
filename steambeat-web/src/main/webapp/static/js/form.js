function Form() {
    var THIS = this;
    this.id = 1;

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

Form.prototype.show = function () {
    showBlanket();
    showRelated();
    showHelp();
    showForm();
    $("#form .form_text:last-child").focus();

    function showBlanket() {
        $("#form_blanket").css("height", $(window).height());
        $("#form_blanket").show();
    }

    function showRelated() {
        $("#form_related").css("top", 50);
        $("#form_related").css("height", $(window).height() - 100);
        $("#form_related").css("left", 100);
        $("#form_related").show();
    }

    function showHelp() {
        $("#form_help").css("top", 50);
        $("#form_help").css("height", $(window).height() - 100);
        $("#form_help").css("left", 1100);
        $("#form_help").show();
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
    $("#form_related").hide();
    $("#form_help").hide();
};

Form.prototype.createFirstBlock = function () {
    var form_block = ich.form_block({id:this.id});
    $("#form").append(form_block);
    this.addBehaviorFor();
};

Form.prototype.addBehaviorFor = function () {
    var THIS = this;
    $("#form_text_" + this.id).keypress(function (event) {
        var code = event.keyCode || event.which;
        THIS.detect(this, code);
        if (code == 13) {
            event.preventDefault();
            event.stopImmediatePropagation();
            THIS.createBlockFrom(this);
        }
    });
};

Form.prototype.detect = function (div, code) {
    var text = $(div).html();
    var positiv = /\+([a-zA-Z0-9\s]*)\+/g;
    var negativ = /\-([a-zA-Z0-9\s]*)\-/g;
    var neutral = /\=([a-zA-Z0-9\s]*)\=/g;
    var positivKeyword = text.match(positiv);
    var negativKeyword = text.match(negativ);
    var neutralKeyword = text.match(neutral);
    console.log(text);
    console.log(positivKeyword);
    console.log(negativKeyword);
    console.log(neutralKeyword);

};

Form.prototype.createBlockFrom = function (element) {
    this.id++;
    var data = {id:this.id};
    var form_block = ich.form_block(data);
    $(element).parent().after(form_block);
    this.addBehaviorFor(this.id);
    $("#form_text_" + this.id).focus();
};