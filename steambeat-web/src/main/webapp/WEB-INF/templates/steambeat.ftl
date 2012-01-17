<@header.withHeader>
<script type="text/javascript" src="${root}/static/js/steambeat.js"></script>
</@header.withHeader>

<@layout.panel>
<#--<div id="counter">${counter!0} opinions</div>-->
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
</@layout.panel>