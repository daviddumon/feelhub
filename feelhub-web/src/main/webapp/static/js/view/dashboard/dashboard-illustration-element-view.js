define(['jquery', 'plugins/hgn!templates/dashboard_illustration_element_edit', 'plugins/hgn!templates/dashboard_illustration_element'],
    function ($, edit_template, normal_template) {

        function render(topicData, editMode, container) {
            if (editMode) {
                renderEdit(topicData, container);
            } else {
                renderNormal(topicData, container);
            }
        }

        function renderEdit(topicData, container) {
            topicData.illustrationLink = root + "/static/images/none.png";
            var dashboard_illustration_element = edit_template(topicData);
            $(container).append(dashboard_illustration_element);
        }

        function renderNormal(topicData, container) {
            var dashboard_illustration_element = normal_template(topicData);
            $(container).append(dashboard_illustration_element);
        }

        return {
            render: render
        }
    });