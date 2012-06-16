<@head.begin>
</@head.begin>

<@head.cssprod>
</@head.cssprod>

<@head.cssdev>
</@head.cssdev>

<@head.jsprod>
</@head.jsprod>

<@head.jsdev>
</@head.jsdev>

<@head.js>
<script type="text/javascript" src="${root}/static/js/home.js?${buildtime}"></script>
<script type="text/javascript">
    var steamId = "${steam.getId()}";
</script>
</@head.js>

<@head.mustache>

</@head.mustache>

<@head.end>

</@head.end>

<@normal.panel>
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
</@normal.panel>
