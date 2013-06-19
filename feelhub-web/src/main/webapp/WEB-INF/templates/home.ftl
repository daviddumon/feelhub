<@head.headbegin>
</@head.headbegin>

<@head.cssprod>
<link rel="stylesheet" href="${root}/static/css/home.css?cache=${buildtime}"/>
</@head.cssprod>

<@head.cssdev>
<link rel="stylesheet/less" type="text/css" href="${root}/static/css/home.less?cache=${buildtime}"/>
</@head.cssdev>

<@head.jsprod>
<script type="text/javascript" data-main="${root}/static/js/controller-built/home-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@head.jsprod>

<@head.jsdev>
<script type="text/javascript" data-main="${root}/static/js/controller/home-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@head.jsdev>

<@head.js>
<script type="text/javascript">
        <#if topicData??>
        var topicData = ${topicData?string};
        <#else>
        var topicData = {};
        </#if>

    var initial_datas = [
        <#if feelingDatas??>
            <#list feelingDatas as feelingData>
                {
                    "feelingid": "${feelingData.id}",
                    "text": [
                        <#list feelingData.text as text>
                            "${text?j_string}"
                        ${text_has_next?string(",", "")}
                        </#list>
                    ],
                    "languageCode": "${feelingData.languageCode}",
                    "userId": "${feelingData.userId}",
                        <#if feelingData.feelingSentimentValue?has_content>"feeling_sentiment_value": "${feelingData.feelingSentimentValue}",</#if>
                    "sentimentDatas": [
                        <#list feelingData.sentimentDatas as sentimentData>
                            {
                                    <#if sentimentData.id?has_content>"id": "${sentimentData.id}",</#if>
                                "sentimentValue": "${sentimentData.sentimentValue}",
                                "name": "${sentimentData.name?j_string?replace("  "," ")}",
                                "thumbnail": "${sentimentData.thumbnail?j_string}",
                                "type": "${sentimentData.type}"
                            }${sentimentData_has_next?string(",", "")}
                        </#list>
                    ]
                }${feelingData_has_next?string(",", "")}
            </#list>
        </#if>
    ]
</script>

</@head.js>

<@head.headend>

</@head.headend>

<body>

<div id="messages"></div>
<header>
    <a id="home_link" href="${root}">Feelhub<span>.com</span></a>

    <form method="get" action="${root}/search" id="search">
        <input name="q" type="text" autocomplete="off"/>
    </form>
    <div id="login_helper">
    <#if userInfos.authenticated || !userInfos.anonymous>
        <p>Hello ${userInfos.user.fullname} ! - <a href="javascript:void(0);" class="logout">logout</a></p>
    </#if>
    </div>
</header>

<#--<div id="right-panel" class="${classes}">-->
<#--<div id="command">-->
<#--<#if userInfos.authenticated>-->
<#--<#nested/>-->
<#--<#elseif !userInfos.anonymous>-->
<#--<div id="login-panel">-->
<#--<a href="${root}/login" class="login-button call-to-action">LOGIN</a>-->
<#--</div>-->
<#--<#else>-->
<#--<div id="login-panel">-->
<#--<a href="${root}/login" class="login-button call-to-action">LOGIN</a> <span>or</span> <a href="${root}/signup" class="signup-button call-to-action">SIGN UP</a>-->
<#--</div>-->
<#--</#if>-->
<#--</div>-->
<#--<#include "newfeeling.ftl"/>-->

<ul id="flow">
</ul>
</div>
</body>
</html>