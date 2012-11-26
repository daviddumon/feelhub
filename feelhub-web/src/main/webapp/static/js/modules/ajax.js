define(['jquery'], function ($) {

    function call(url, type, contentType, data, callbackSuccess, callbackError) {
        $.ajax({
            url: url,
            type: type,
            contentType: contentType,
            data: JSON.stringify(data),
            processData:false,
            success:function (data, textStatus, jqXHR) {
                callbackSuccess(data, textStatus, jqXHR);
            },
            error:function () {
                callbackError();
            }
        });
    }

    return {
        call: call
    };
});