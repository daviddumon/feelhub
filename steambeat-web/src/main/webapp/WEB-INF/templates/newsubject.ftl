<@head.withHead>
<link rel="stylesheet" href="${root}/static/css/newsubject.css?${buildtime}"/>
<script type="text/javascript" src="${root}/static/js/newsubject.js?${buildtime}"></script>
</@head.withHead>

<body class="font_text color_bg_lightblue">

<#include "header.ftl">

<script type="text/javascript">
    $(function () {
        newsubject.create("${uri}");
    });
</script>

<div id="newsubject" class="good_without_image rounded">
    <img id="loading_gif" src="${root}/static/images/ajax-loader.gif"/>

    <p class="titlefont">Steambeat is creating this awesome subject for you !</p>
</div>

</body>
</html>
