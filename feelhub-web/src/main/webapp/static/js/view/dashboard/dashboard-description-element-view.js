define(['jquery', 'plugins/hgn!templates/dashboard_description_element_edit', 'plugins/hgn!templates/dashboard_description_element'],
    function ($, edit_template, normal_template) {

        function render(topicData, editMode, container) {
            if (editMode) {
                renderEdit(topicData, container);
            } else {
                renderNormal(topicData, container);
            }
        }

        function renderEdit(topicData, container) {
            topicData.types = types;
            var dashboard_description_element = edit_template(topicData);
            $(container).append(dashboard_description_element);
        }

        function renderNormal(topicData, container) {
            var dashboard_description_element = normal_template(topicData);
            $(container).append(dashboard_description_element);
        }

        return {
            render: render
        }
    });