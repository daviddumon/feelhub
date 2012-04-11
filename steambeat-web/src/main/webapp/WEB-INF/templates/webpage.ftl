<@head.withHead>
<script type="text/javascript" src="${root}/static/js/webpage.js?${buildtime}"></script>
<script type="text/javascript">
    var subjectId = "${webPage.getId()}";
</script>
</@head.withHead>

<@body.subject>
<div class="judgment button no_select">
    <div class="feeling_smiley good rounded_top">
        <#--<img class="judgment_smiley" src="${root}/static/images/smiley_good_white.png"/>-->
    </div>
    <div id="add_judgment1" class="judgment_tag good_border font_title">
    ${webPage.getShortDescription()}
        <input type="hidden" name="feeling" value="good"/>
        <input type="hidden" name="subjectId" value="${webPage.getId()}"/>
    </div>
</div>
</@body.subject>

<@body.related>

</@body.related>

<@body.panel>
<div class="panel_box panel_resource">
    <div id="webpageRoot"><span onclick="javascript:window.open('${webPage.uri}');" style="cursor: pointer">${webPage.description}</span></div>
    <div id="illustration"><img <#if ''?matches('${webPage.illustration}')> style='display: none;' <#else> src="${webPage.illustration}" </#if> /></div>
</div>
<!--
<div id="counter">${counter!0} opinions</div>
<div class="panel_box">
    <div class="counter good rounded">
        <img class="smiley" src="${root}/static/images/smiley_good_white.png"/>
        <p class="counter_tl
        ext titlefont">0</p>
    </div>
    <div class="counter bad rounded">
        <img class="smiley" src="${root}/static/images/smiley_bad_white.png"/>
        <p class="counter_text titlefont">0</p>
    </div>
</div>
-->
</@body.panel>