<#macro panel>
<body class="font_text color_bg_lightblue">

<div id="fixed_layer">
    <#include "header.ftl">
    <div id="panel" class="color_bg_darkblue">
        <#nested/>
    </div>

</#macro>

<#macro form>
    <div id="blanket" style="display: none;"></div>
    <div id="popup_form" style="display: none;">
        <a href="" onClick="closeOpinionForm();return false;">close</a>

        <form id="opinion_form" method="post" action="" autocomplete="off">

            <#nested/>

            <textarea id="opinion_form_textarea" name="text" class="font_text"></textarea>

            <input id="opinion_form_submit" type="submit" value="ok" class="button font_title"/>
        </form>
    </div>
    <a href="" onclick="openOpinionForm();return false;" id="form_button" class="color_bg_darkblue rounded_down font_title color_darkblue">add your opinion</a>
</div>

<ul id="opinions"></ul>

<a id="feedback" class="vertical_left rounded_down button font_title color_bg_darkblue color_lightblue" href="${root}/bookmarklet?q=http%3A%2F%2Fwww.steambeat.com&version=1">feedback</a>

</body>
</html>
</#macro>
