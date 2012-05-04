<#macro panel>
<body class="font_text color_bg_lightblue">
<div id="blanket" style="display: none;"></div>

<form id="opinion_form" method="post" action="" autocomplete="off" style="display: none;">
    <a id="opinion_close" href="" onClick="closeOpinionForm();return false;">close</a>

    <div class="opinion_form_title color_darkblue">my opinion</div>

    <textarea id="opinion_form_textarea" name="text" class="font_text"></textarea>

    <div class="opinion_form_title color_darkblue">about this subject</div>

    <div id="opinion_form_subject" class="font_title">
    </div>

    <div class="opinion_form_title color_darkblue">and those related subjects</div>

    <div id="opinion_form_related">
    </div>

    <input id="opinion_form_submit" type="submit" value="OK" class="button font_title hover_darkblue"/>
</form>

<div id="fixed_layer">

    <#include "header.ftl">

    <div id="panel" class="color_bg_darkblue">
        <#nested/>
    </div>

    <a href="" onclick="openOpinionForm();return false;" id="form_button" class="color_bg_darkblue rounded_down font_title color_darkblue" style="display: none">add your opinion</a>
</div>

<ul id="opinions"></ul>

<a id="feedback" class="vertical_left rounded_down button font_title color_bg_darkblue color_lightblue" href="${root}/bookmarklet?q=http%3A%2F%2Fwww.steambeat.com&version=1">feedback</a>

</body>
</html>
</#macro>
