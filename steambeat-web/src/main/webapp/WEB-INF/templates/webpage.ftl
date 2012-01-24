<@head.withHead>
<script type="text/javascript" src="${root}/static/js/webpage.js"></script>
<script type="text/javascript">
    var formAction = "http://${domain}${root}/webpages/${webPage.getId()}/opinions";
</script>
</@head.withHead>

<@body.hub>
    <div id="to_top" class="color_darkblue button hover_medblue">add your opinion now !</div>
</@body.hub>

<@body.panel>
<div class="panel_box panel_resource">
    <span id="webpageRoot"><span onclick="javascript:window.open('${webPage.id}');" style="cursor: pointer">${webPage.id}</span></span>
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