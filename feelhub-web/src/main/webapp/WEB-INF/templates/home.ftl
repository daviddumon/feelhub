<@flow.jsprod>
<script type="text/javascript" data-main="${root}/static/js/controller-built/home-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@flow.jsprod>

<@flow.jsdev>
<script type="text/javascript" data-main="${root}/static/js/controller/home-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@flow.jsdev>

<@flow.js>
<script type="text/javascript">
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
</@flow.js>

<@flow.dashboard>
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
</@flow.dashboard>

<@flow.command classes="">
<#--<#include "newfeeling.ftl"/>-->
</@flow.command>

<@flow.feelings>

</@flow.feelings>