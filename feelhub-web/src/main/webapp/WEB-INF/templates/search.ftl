<@head.headbegin>
</@head.headbegin>

<@head.cssprod>
<link rel="stylesheet" href="${root}/static/css/flow.css?cache=${buildtime}"/>
</@head.cssprod>

<@head.cssdev>
<link rel="stylesheet/less" type="text/css" href="${root}/static/css/flow.less?cache=${buildtime}"/>
</@head.cssdev>

<@head.jsprod>
<script type="text/javascript" data-main="${root}/static/js/controller-built/search-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@head.jsprod>

<@head.jsdev>
<script type="text/javascript" data-main="${root}/static/js/controller/search-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@head.jsdev>

<@head.js>
<script type="text/javascript">
        <#if topicData??>
        var topicData = ${topicData?string};
        <#else>
        var topicData = {};
        </#if>
</script>
<script type="text/javascript">
    var q = "${q}";
    var type = "${type}";

    var initial_datas = [
        <#if topicDatas??>
            <#list topicDatas as topicData>
                {
                    "id": "${topicData.id}",
                    "name": "${topicData.name?j_string}",
                    "thumbnailLarge": "${topicData.thumbnailLarge?j_string}",
                    "thumbnailMedium": "${topicData.thumbnailMedium?j_string}",
                    "thumbnailSmall": "${topicData.thumbnailSmall?j_string}",
                    "type": "${topicData.type}"
                }${topicData_has_next?string(",", "")}
            </#list>
        </#if>
    ]
</script>
</@head.js>

<@head.headend>

</@head.headend>

<body>
<#include "base/header.ftl"/>

<div id="slogan">
    <span>Topics for</span>
    <span>${q}</span>
</div>

<div id="command" class="search">
<#if userInfos.authenticated>

<#elseif !userInfos.anonymous>
    <div id="login-panel">
        <a href="${root}/login" class="login-button call-to-action">LOGIN</a>
    </div>
<#else>
    <div id="login-panel">
        <a href="${root}/login" class="login-button call-to-action">LOGIN</a> <span>or</span> <a href="${root}/signup" class="signup-button call-to-action">SIGN UP</a>
    </div>
</#if>
</div>
<ul id="flow" class="search">
</ul>
</body>
</html>