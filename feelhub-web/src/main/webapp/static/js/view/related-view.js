define(["jquery","plugins/hgn!templates/related"], function($, template) {

    function render(data) {
        var related = template(data);
        $("#related").append(related);
    }

    return {
        render: render
    }
});