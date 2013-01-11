define(["jquery","plugins/hgn!templates/flow/flow_topic"],

    function ($, template) {

        function render(data, container) {
            data.root =  root;
            var element = template(data);
            $(container).append(element);
        }

        return {
            render:render
        }
    });