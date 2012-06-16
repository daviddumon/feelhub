<#--<@head.withHead>-->
<#--<link rel="stylesheet" href="${root}/static/css/error.css?${buildtime}"/>-->
<#--</@head.withHead>-->

<body class="font_text color_bg_lightblue">

<#--<#include "header.ftl">-->

<div id="error" class="bad_without_image rounded">
    <img src="${root}/static/images/smiley_bad_white.png"/>
    <p class="titlefont">Your bookmarklet is outdated ! Please drag this bookmarklet to your bookmarks bar !</p>
    <a id="bookmarklet" href="javascript:<#include '../bookmarklet.js' />" class="rounded font_title color_darkblue">Steambeat!</a>
</div>

</body>
</html>
