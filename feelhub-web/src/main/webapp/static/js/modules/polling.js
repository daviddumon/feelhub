define(["jquery"], function ($) {

    var polling_data = {
        thumbnail: {
            needed: false,
            time: 100,
            uri: root + "/api/topic/" + topicData.id,
            stop: 102400,
            end: function (response) {
                return response.thumbnail != "";
            },
            success: function (response) {
                $("#current-topic img.loading").remove();
                $("#current-topic img.illustration").attr("src", response.thumbnail);
            }
        },
        related: {
            needed: false,
            time: 1000,
            uri: root + "/api/topic/" + topicData.id + "/related",
            stop: 128000,
            end: function (response) {

            },
            success: function(response) {

            }
        }
    };

    function start() {
        if (typeof topicData !== 'undefined') {
            checkNeededData();

            if (polling_data.thumbnail.needed) {
                poll(polling_data.thumbnail);
            }

            //if(polling_data.related.needed) {
            //    poll(polling_data.related);
            //}
        }
    }

    function checkNeededData() {
        console.log(topicData.creationDate);
        console.log(new Date());
        console.log(new Date() - topicData.creationDate);

        if ($("#current-topic img.loading").length > 0) {
            polling_data.thumbnail.needed = true;
        }

        if($("#related").length == 0) {
            polling_data.related.needed = true;
        }
    }

    function poll(data) {
        if (data.time <= data.stop) {
            setTimeout(function () {
                $.getJSON(data.uri,function (response) {

                    if ((data.end)(response)) {
                        (data.success)(response);
                    } else {
                        data.time *= 2;
                        poll(data);
                    }
                }).fail(function () {
                        console.log("error while polling for data.");
                    })
            }, data.time);
        }
    }

    return {
        start: start
    }
});