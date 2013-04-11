define(["jquery","plugins/hgn!templates/flow/flow_topic"],

    function ($, template) {

        function render(data, container) {
            data.root =  root;
            if(data.thumbnail == "") {
                data.thumbnail = root + "/static/images/unknown.png";
            }
            var element = template(data);
            $(container).append(element);
        }

        function reset() {

        }

        return {
            render:render,
            reset:reset
        }
    });