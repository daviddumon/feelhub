define(['jquery', 'plugins/hgn!templates/search_command_real_view'],
    function ($, template) {

        function render(container) {
            var search_command_web_view = template({"root":root,"q":q});
            $(container).append(search_command_web_view);
        }

        return {
            render:render
        }
    });