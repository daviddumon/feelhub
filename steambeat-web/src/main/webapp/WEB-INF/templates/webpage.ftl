<@header.withHeader>
<link rel="stylesheet" href="${root}/static/css/panel.css"/>
<script type="text/javascript" src="${root}/static/js/webpage.js"></script>
</@header.withHeader>

<@layout.panel>
<#--<a id="bookmarklet" href="javascript:<#include 'bookmark.ftl' />" title="Say it !">Say it !</a>-->
<#--<div id="counter">${counter!0} opinions</div>-->
<div class="panel_box panel_resource">
    <span id="webpageRoot"><a href="#" onclick="javascript:window.open('${webPage.id}');">${webPage.id}</a></span>
</div>
<div class="panel_box">
    <div class="counter good rounded">
        <img class="smiley" src="${root}/static/images/smiley_good_white.png"/>
        <p class="counter_text">0</p>
    </div>
    <div class="counter bad rounded">
        <img class="smiley" src="${root}/static/images/smiley_bad_white.png"/>
        <p class="counter_text">0</p>
    </div>
</div>
</@layout.panel>