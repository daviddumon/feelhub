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

<div id="newsubject" class="good rounded">
    <img src="${root}/static/images/smiley_good_white.png"/>

    <p class="titlefont">Creating this awesome subject for you !</p>
</div>

</body>
</html>
