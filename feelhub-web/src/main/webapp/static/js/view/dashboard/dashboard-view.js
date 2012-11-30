define(["./dashboard-illustration-element-view",
    "./dashboard-description-element-view",
    "./dashboard-related-element-view"],
    function (diev, ddev, drev) {

        var dashboard_container = "#dashboard";

        function render(topicData, editMode) {
            diev.render(topicData, editMode, dashboard_container);
            ddev.render(topicData, editMode, dashboard_container);
            drev.render(topicData, editMode, dashboard_container);
        }

        return  {
            render:render
        };
    });