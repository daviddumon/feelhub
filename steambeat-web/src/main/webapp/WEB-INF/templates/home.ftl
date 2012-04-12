<@head.withHead>
<script type="text/javascript" src="${root}/static/js/home.js?${buildtime}"></script>
<script type="text/javascript">
    var steamId = "${steam.getId()}";
</script>
</@head.withHead>

<@body.subject>

</@body.subject>

<@body.related>

</@body.related>

<@body.panel>
<#--<div id="counter">${counter!0} opinions</div>-->
<div class="panel_box">
    <div class="counter good_without_image rounded">
        <img class="smiley" src="${root}/static/images/smiley_good_white.png"/>
        <p id="counter_good" class="counter_text titlefont">0</p>
    </div>
    <div class="counter bad_without_image rounded">
        <img class="smiley" src="${root}/static/images/smiley_bad_white.png"/>
        <p id="counter_bad" class="counter_text titlefont">0</p>
    </div>
</div>
</@body.panel>
