<#macro jsprod>
    <@head.headbegin>
    </@head.headbegin>

    <@head.cssprod>
    <link rel="stylesheet" href="${root}/static/css/fixed.css?cache=${buildtime}"/>
    </@head.cssprod>

    <@head.cssdev>
    <link rel="stylesheet/less" type="text/css" href="${root}/static/css/fixed.less?cache=${buildtime}"/>
    </@head.cssdev>

    <@head.jsprod>
        <#nested/>
    </@head.jsprod>
</#macro>

<#macro jsdev>
    <@head.jsdev>
        <#nested/>
    </@head.jsdev>
</#macro>

<#macro js>
    <@head.js>
        <#nested/>
    </@head.js>

    <@head.headend>

    </@head.headend>
</#macro>

<#macro body>
<body>

<div id="messages"></div>

    <#nested/>
</body>
</html>
</#macro>