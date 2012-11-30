define(['jquery', 'plugins/hgn!templates/dashboard_illustration_element_edit'], function ($, template) {

    function render(topicData, editMode, container) {
        if (editMode) {
            renderEdit(topicData, container);
        } else {
            renderNormal(topicData, container);
        }
    }

    function renderEdit(topicData, container) {
        topicData.illustrationLink = root + "/static/images/none.png";
        var dashboard_illustration_element = template(topicData);
        $(container).append(dashboard_illustration_element);
    }

    function renderNormal(topicData, container) {

    }

    return {
        render:render
    }
});