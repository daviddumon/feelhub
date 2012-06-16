<#macro panel>
<body class="font_text color_bg_lightblue">
<div id="form_blanket" style="display: none;"></div>

<#include "../includes/form.ftl">

<div id="fixed_layer">

    <#include "../includes/header.ftl">

    <div id="panel" class="color_bg_darkblue">
        <#nested/>
    </div>

    <a href="" id="form_button" class="color_bg_darkblue rounded_down font_title color_darkblue" style="display: none">add your opinion</a>
</div>

<ul id="opinions"></ul>

</body>
</html>
</#macro>
