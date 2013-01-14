define(["jquery", "plugins/hgn!templates/flow/flow_feeling"],

    function ($, template) {

        function render(data, container) {
            data.root = root;
            var element = template(prepare_data(data));
            $(container).append(element);
        }

        function prepare_data(feeling) {
            var text = feeling.text.replace(/[\#\+\-\=][^ ]+/g, function (match) {
                match = match.replace(/[\#\+\-\=]/g, "");
                return "<span>" + match + "</span>";
            });
            text = text.replace(/[\#\+\-\=]+/g, "");

            var topicDatas = [];
            var known_topics = [];
            for (var i = 0; i < feeling.topicDatas.length; i++) {
                if (feeling.topicDatas[i].id !== topicData.id) {
                    var current = feeling.topicDatas[i].id;
                    if (!(current in known_topics)) {
                        var topic_data = {
                            topicId: feeling.topicDatas[i].id,
                            sentimentValue: feeling.topicDatas[i].sentimentValue,
                            name: feeling.topicDatas[i].name,
                            url: root + "/topic/" + feeling.topicDatas[i].id,
                            illustration: feeling.topicDatas[i].illustration,
                            classes:"topic_medium topic_stack"
                        };
                        if (topic_data.illustration == "") {
                            topic_data.illustration = root + "/static/images/unknown.png";
                        }
                        topicDatas.push(topic_data);
                        known_topics[current] = true;
                    }
                } else {
                    var feeling_sentiment_value = feeling.topicDatas[i].sentimentValue;
                }
            }

            shuffleAndMakeFirstLarge();

            var feelingData = {
                id: feeling.id,
                text: text.split(/\r\n|\r|\n/),
                topicDatas: topicDatas,
                height: (topicDatas.length != 0 ? 40 : 0) + 146 * (Math.floor(topicDatas.length / 2) + topicDatas.length % 2) + 'px'
            };

            if (feeling_sentiment_value !== "none") {
                feelingData["feeling_sentiment_value"] = feeling_sentiment_value;
                feelingData["feeling_sentiment_value_illustration"] = root + "/static/images/smiley_" + feeling_sentiment_value + "_white_14.png";
            }

            return feelingData;

            function shuffleAndMakeFirstLarge() {
                if (topicDatas.length % 2 != 0) {
                    var shuffle_number = Math.floor(Math.random() * topicDatas.length);
                    for (var i = 0; i < shuffle_number; i++) {
                        var rd = topicDatas.shift();
                        topicDatas.push(rd);
                    }
                    topicDatas[0]["classes"] = "topic_large topic_stack";
                }
            }
        }

        return {
            render: render
        }
    });