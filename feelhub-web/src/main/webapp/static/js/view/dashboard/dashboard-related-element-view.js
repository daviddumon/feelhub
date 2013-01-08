define(['jquery', 'plugins/hgn!templates/dashboard_related_element_edit', 'plugins/hgn!templates/dashboard_related_element'],
    function ($, edit_template, normal_template) {

        function render(topicData, editMode, container) {
            if (editMode) {
                renderEdit(topicData, container);
            } else {
                renderNormal(topicData, container);
            }
        }

        function renderEdit(topicData, container) {
            var dashboard_related_element = edit_template(topicData);
            $(container).append(dashboard_related_element);
        }

        function renderNormal(topicData, container) {
            var dashboard_related_element = normal_template(topicData);
            $(container).append(dashboard_related_element);
        }

        return {
            render: render
        }
    });