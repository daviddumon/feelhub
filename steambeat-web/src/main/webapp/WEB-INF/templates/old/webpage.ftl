<@head.withHead>
<script type="text/javascript" src="${root}/static/js/subject.js?${buildtime}"></script>
<script type="text/javascript">
    var subjectId = "${webPage.getId()}";
</script>
</@head.withHead>

<@body.panel>

<#--<div id="counter">${counter!0} opinions</div>-->
<div class="panel_box">
    <div class="counter good_without_image rounded">
        <img class="smiley" src="${root}/static/images/smiley_good_white.png"/>

        <p id="counter_good" class="counter_text titlefont">0</p>
    </div>
    <div class="counter neutral_without_image rounded">
        <img class="smiley" src="${root}/static/images/smiley_neutral_white.png"/>

        <p id="counter_neutral" class="counter_text titlefont">0</p>
    </div>
    <div class="counter bad_without_image rounded">
        <img class="smiley" src="${root}/static/images/smiley_bad_white.png"/>

        <p id="counter_bad" class="counter_text titlefont">0</p>
    </div>
</div>
<div class="panel_box panel_resource">
    <div id="webpageRoot"><span onclick="javascript:window.open('${webPage.uri}');" style="cursor: pointer">${webPage.description}</span></div>
    <div id="illustration"><img <#if ''?matches('${webPage.illustration}')> style='display: none;' <#else> src="${webPage.illustration}" </#if> /></div>
</div>
<div class="panel_box related_box">
    <span>related</span>
</div>
</@body.panel>