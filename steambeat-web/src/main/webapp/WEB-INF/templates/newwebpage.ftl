<@head.withHead>
<link rel="stylesheet" href="${root}/static/css/newwebpage.css"/>
<script type="text/javascript" src="${root}/static/js/newwebpage.js"></script>
</@head.withHead>

<body class="font_text color_bg_lightblue">

<#include "header.ftl">

<script type="text/javascript">
    $(function () {
        newwebpage.create("${uri}");
    });
</script>

<div id="newwebpage" class="good rounded">
    <img src="${root}/static/images/smiley_good_white.png"/>

    <p class="titlefont">Creating this awesome subject for you !</p>
</div>

</body>
</html>
