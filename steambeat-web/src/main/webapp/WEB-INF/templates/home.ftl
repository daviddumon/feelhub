<@head.withHead>
<script type="text/javascript" src="${root}/static/js/home.js?${buildtime}"></script>
</@head.withHead>

<@body.hub>
<#--<div id="to_top" class="color_darkblue button hover_medblue">back to top !</div>-->
</@body.hub>

<@body.panel>
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
<div id="blanket" style="display: none;"></div>
<div id="popup_form" style="display: none;">
    <a href="" onClick="closeOpinionForm();return false;">close</a>

    <form id="opinion_form" method="post" action="" autocomplete="off">
        <textarea id="opinion_form_textarea" name="text" class="font_text"></textarea>

        <input id="opinion_form_submit" type="submit" value="ok" class="button font_title"/>
    </form>
</div>
<a href="" onclick="openOpinionForm();return false;" id="form_button" class="color_bg_darkblue rounded_down font_title color_darkblue">add your opinion</a>
</@body.form>