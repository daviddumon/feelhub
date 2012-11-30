define(['jquery', 'plugins/hgn!templates/dashboard_related_element_edit'], function ($, template) {

    function render(topicData, editMode, container) {
        if (editMode) {
            renderEdit(topicData, container);
        } else {
            renderNormal(topicData, container);
        }
    }

    function renderEdit(topicData, container) {
        var dashboard_related_element = template(topicData);
        $(container).append(dashboard_related_element);
    }

    function renderNormal(topicData, container) {

    }

    return {
        render:render
    }
});