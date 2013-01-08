define(['jquery', 'plugins/hgn!templates/flow_list'], function ($, template) {

    function render(container, index) {
        var list = template({"index": index});
        container.append(list);
    }

    return {
        render: render
    }
});