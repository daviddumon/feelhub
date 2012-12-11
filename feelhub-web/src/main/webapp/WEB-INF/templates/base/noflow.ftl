<#macro js>
    <@head.headbegin>
    </@head.headbegin>

    <@head.cssprod>
    <link rel="stylesheet" href="${root}/static/css/noflow_layout.css?${buildtime}"/>
    </@head.cssprod>

    <@head.cssdev>
    <link rel="stylesheet/less" type="text/css" href="${root}/static/css/noflow_layout.less?${buildtime}"/>
    </@head.cssdev>

    <@head.jsprod>
    </@head.jsprod>

    <@head.jsdev>
    </@head.jsdev>

    <@head.js>
        <#nested/>
    </@head.js>

    <@head.headend>

    </@head.headend>
</#macro>

<#macro body>
<body>
    <#include "header.ftl"/>
<#nested/>
</body>
</html>
</#macro>