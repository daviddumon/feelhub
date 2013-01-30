<#macro js>
    <@head.headbegin>
    </@head.headbegin>

    <@head.cssprod>
    <link rel="stylesheet" href="${root}/static/css/dashboard.css?cache=${buildtime}"/>
    <link rel="stylesheet" href="${root}/static/css/command.css?cache=${buildtime}"/>
    <link rel="stylesheet" href="${root}/static/css/flow.css?cache=${buildtime}"/>
    <link rel="stylesheet" href="${root}/static/css/ad.css?cache=${buildtime}"/>
    </@head.cssprod>

    <@head.cssdev>
    <link rel="stylesheet/less" type="text/css" href="${root}/static/css/dashboard.less?cache=${buildtime}"/>
    <link rel="stylesheet/less" type="text/css" href="${root}/static/css/command.less?cache=${buildtime}"/>
    <link rel="stylesheet/less" type="text/css" href="${root}/static/css/flow.less?cache=${buildtime}"/>
    <link rel="stylesheet/less" type="text/css" href="${root}/static/css/ad.less?cache=${buildtime}"/>
    </@head.cssdev>

    <@head.jsprod>
    </@head.jsprod>

    <@head.jsdev>
    </@head.jsdev>

    <@head.js>
    <script type="text/javascript">
            <#if topicData??>
            var topicData = ${topicData?string};
            <#else>
            var topicData = {};
            </#if>

            <#if realtypes??>
            var realtypes = [
                <#list realtypes as type>
                    "${type}"${type_has_next?string(",", "")}
                </#list>
            ]
            <#else>
            var realtypes = [];
            </#if>
    </script>
        <#nested/>
    </@head.js>

    <@head.headend>

    </@head.headend>
</#macro>

<#macro dashboard>
<body>
    <#include "header.ftl"/>

    <#nested/>

</#macro>

<#macro command>
<div id="command">
    <#if userInfos.authenticated!false>
        <#nested/>
    <#else>
        <div>Please login or create an account!</div>
    </#if>
</div>
<ul id="flow">
</ul>
</body>
</html>
</#macro>

