<#macro js>
    <@head.headbegin>
    <script type="text/javascript">
        var authentificated = ${userInfos.authenticated?string};
            <#if !userInfos.anonymous>
            var userLanguageCode = "${userInfos.user.languageCode}";
            </#if>

            <#if topicData??>
            var topicData = {
                "id":"${topicData.id}",
                "illustrationLink":"${topicData.illustrationLink}",
                "description":"${topicData.description}",
                "sentimentValue":"${topicData.sentimentValue}",
                "type":"${topicData.type}",
                "subtypes":[],
                "url":[]
            }
            </#if>
    </script>
    </@head.headbegin>

    <@head.cssprod>
    <link rel="stylesheet" href="${root}/static/css/flow_layout.css?${buildtime}"/>
    <link rel="stylesheet" href="${root}/static/css/dashboard.css?${buildtime}"/>
    </@head.cssprod>

    <@head.cssdev>
    <link rel="stylesheet/less" type="text/css" href="${root}/static/css/flow_layout.less?${buildtime}"/>
    <link rel="stylesheet/less" type="text/css" href="${root}/static/css/dashboard.less?${buildtime}"/>
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

<#macro dashboard>
<body>
    <#include "header.ftl"/>

<ul id="dashboard">
    <#nested/>
</ul>
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

