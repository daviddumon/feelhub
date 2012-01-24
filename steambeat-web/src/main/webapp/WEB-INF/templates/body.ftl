<#macro hub>
<body class="font_text color_bg_lightblue">

<#include "header.ftl">

<div id="hud" class="color_bg_lightblue">
    <#nested/>
</div>
</#macro>

<#macro panel>
<div id="panel" class="color_bg_darkblue">
    <#nested/>
</div>

<form id="opinion_form" method="post" action="" autocomplete="off" class="color_bg_lightblue rounded_top">
    <div id="opinion_title" class="font_text color_medblue">add your opinion</div>
    <textarea id="opinion_form_textarea" name="text" class="font_text"></textarea>
    <div id="opinion_form_judgments" class="font_title" style="display: none">
        <span id="add_subject1" class="subject_tag good font_title add_tag">this webpage<span class="subject_info font_title">You like!</span><input
                        type="hidden"
                        name="feeling"
                        value="good"></input></span>
    </div>
    <input id="opinion_form_submit" type="submit" value="ok" class="button font_title color_medblue hover_darkblue"/>
</form>

<ul id="opinions"></ul>

<a id="feedback" class="vertical_left rounded_down button font_title color_bg_darkblue color_lightblue" href="${root}/webpages/http://www.steambeat.com">feedback</a>

</body>
</html>
</#macro>
