<@layout.headbegin>
</@layout.headbegin>

<@layout.cssprod>
<link rel="stylesheet" href="${root}/static/css/noflow.css?${buildtime}"/>
</@layout.cssprod>

<@layout.cssdev>
<link rel="stylesheet/less" type="text/css" href="${root}/static/css/noflow.less?${buildtime}"/>
</@layout.cssdev>

<@layout.jsprod>
</@layout.jsprod>

<@layout.jsdev>
</@layout.jsdev>

<@layout.js>
<script type="text/javascript" src="${root}/static/js/newsubject.js?${buildtime}"></script>
<script type="text/javascript">
    $(function () {
        newsubject.create("${uri!''}");
    });
</script>
</@layout.js>

<@layout.mustache>
</@layout.mustache>

<@layout.headend>

</@layout.headend>

<@layout.fixed>

</@layout.fixed>

<@layout.body>
<div id="newsubject">
    <img id="loading_gif" src="${root}/static/images/ajax-loader.gif"/>

    <p>Steambeat is creating this awesome subject for you !</p>
</div>
</@layout.body>