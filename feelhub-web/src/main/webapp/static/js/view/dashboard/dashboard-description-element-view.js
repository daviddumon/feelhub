define(['jquery', 'plugins/hgn!templates/dashboard_description_element_edit'], function ($, template) {

    function render(topicData, editMode, container) {
        if (editMode) {
            renderEdit(topicData, container);
        } else {
            renderNormal(topicData, container);
        }
    }

    function renderEdit(topicData, container) {
        topicData.types = types;
        var dashboard_description_element = template(topicData);
        $(container).append(dashboard_description_element);
    }

    function renderNormal(topicData, container) {

    }

    return {
        render:render
    }
});