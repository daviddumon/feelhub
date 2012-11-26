<#macro js>
    <@head.headbegin>
    <script type="text/javascript">
        var authentificated = ${userInfos.authenticated?string};
            <#if !userInfos.anonymous>
            var userLanguageCode = "${userInfos.user.languageCode}";
            </#if>

        var topicId = "";
        var keywordValue = "";
        var languageCode = "";
        var illustrationLink = "";
        var typeValue = "";

        var flow;
    </script>
    </@head.headbegin>

    <@head.cssprod>
    <link rel="stylesheet" href="${root}/static/css/flow_layout.css?${buildtime}"/>
    </@head.cssprod>

    <@head.cssdev>
    <link rel="stylesheet/less" type="text/css" href="${root}/static/css/flow_layout.less?${buildtime}"/>
    </@head.cssdev>

    <@head.jsprod>
    </@head.jsprod>

    <@head.jsdev>
    </@head.jsdev>

    <@head.js>
    <script type="text/javascript" src="${root}/static/js/form.js?${buildtime}"></script>
    <script type="text/javascript" src="${root}/static/js/flow.js?${buildtime}"></script>
        <#nested/>

    </@head.js>

    <@head.mustache>
        <#include "../mustache/topic.html">
        <#include "../mustache/feeling.html">
    </@head.mustache>

    <@head.headend>

    </@head.headend>
</#macro>

<#macro dashboard>
<body>
<#include "header.ftl"/>

<div id="dashboard">
    <#nested/>
</div>
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

