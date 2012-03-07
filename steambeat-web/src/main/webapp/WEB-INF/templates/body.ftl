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
</#macro>

<#macro form>
    <#nested/>

<ul id="opinions"></ul>

<a id="feedback" class="vertical_left rounded_down button font_title color_bg_darkblue color_lightblue" href="${root}/bookmarklet?q=http%3A%2F%2Fwww.steambeat.com">feedback</a>

</body>
</html>
</#macro>
