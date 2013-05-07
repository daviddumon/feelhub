define(["jquery", "plugins/hgn!templates/flow/flow_list"], function ($, template) {

    function render(container, index, width) {
        console.log("list : " + width);
        var list = template({"index": index, "width": Math.floor(width)});
        container.append(list);
    }

    return {
        render: render
    }
});