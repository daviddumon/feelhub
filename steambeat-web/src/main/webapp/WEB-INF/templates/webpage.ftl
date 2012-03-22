<@head.withHead>
<script type="text/javascript" src="${root}/static/js/webpage.js?${buildtime}"></script>
<script type="text/javascript">
    var subjectId = "${webPage.getId()}";
</script>
</@head.withHead>

<@body.hub>
<div id="to_top" class="color_darkblue button hover_medblue">add your opinion now !</div>
</@body.hub>

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
        <p class="counter_text titlefont">0</p>
    </div>
    <div class="counter bad rounded">
        <img class="smiley" src="${root}/static/images/smiley_bad_white.png"/>
        <p class="counter_text titlefont">0</p>
    </div>
</div>
-->
</@body.panel>

<@body.form>
<form id="opinion_form" method="post" action="" autocomplete="off" class="color_bg_lightblue rounded_top">
    <div id="opinion_title" class="font_text color_medblue">add your opinion</div>
    <textarea id="opinion_form_textarea" name="text" class="font_text"></textarea>

    <div id="opinion_form_judgments" class="font_title" style="display: none">
        <div class='judgments_header font_title'>related</div>
        <span id="add_judgment1" class="judgment_tag good font_title add_tag">this webpage<span class="judgment_info font_title">You like!</span>
            <input type="hidden" name="feeling" value="good"/>
            <input type="hidden" name="subjectId" value="${webPage.getId()}"/></span>
    </div>
    <input id="opinion_form_submit" type="submit" value="ok" class="button font_title color_medblue hover_darkblue"/>
</form>
</@body.form>