define(['jquery', './ajax'], function ($, ajax) {

    function init() {
        $("#createtopic input").click(function (event) {
            event.stopImmediatePropagation();
            event.preventDefault();
            $.ajax({
                url:root + '/api/topics',
                type:'POST',
                contentType:'application/json',
                data:JSON.stringify($("#createtopic").serializeArray()),
                processData:false,
                success:function (data, textStatus, jqXHR) {
                    console.log("succes creation topic");
                },
                error:function () {
                    console.log("erreur creation topic");
                }
            });
        });
    }

    return {
        init:init
    };
});
