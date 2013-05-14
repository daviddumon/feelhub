<@flow.jsprod>
<meta name="fragment" content="!">
<script type="text/javascript" data-main="${root}/static/js/controller-built/topic-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@flow.jsprod>

<@flow.jsdev>
<script type="text/javascript" data-main="${root}/static/js/controller/topic-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
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
                                "name": "${sentimentData.name?j_string}",
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
<header class="bigheader">
    <a id="home_link" href="${root}">Feelhub<span>.com</span></a>

    <form method="get" action="${root}/search" id="search">
        <input name="q" type="text" autocomplete="off"/>
    </form>
    <div id="login_helper">
        <#if userInfos.authenticated || !userInfos.anonymous>
            <p>Hello ${userInfos.user.fullname} ! - <a href="javascript:void(0);" class="logout">logout</a></p>
        </#if>
    </div>

    <div style="position: absolute; width: 80%; height: 348px; top: 133px; left: 10%; overflow: hidden;">

        <div class="topic topic-large topic-no-cursor" style="position: absolute; top: 0px; left: 0px; width: 564px; height: 348px;">
            <img src="${topicData.thumbnail}" class="illustration"/>
            <span>${topicData.name}</span>
        </div>

        <div id="youfeel" style="position: absolute; top: 31px; width: 133px; margin-left: 10%;">
            <canvas id="canvas-sentiment" width="133" height="133" style=""></canvas>
            <span id="youfeel" style="">You Feel</span>
        </div>

        <div id="uris" style="width: 564px; position: absolute; left: 800px;">
            <#list topicData.uris as uri>
                <img src="${root}/static/images/search-dark.png" class="linkicon"/>
                <a href="${uri}" class="uris" rel="nofollow" target="_blank">${uri}</a>
            </#list>
        </div>
    </div>

</header>
</@flow.dashboard>

<@flow.command classes="">
    <#--<#include "newfeeling.ftl"/>-->
</@flow.command>

<@flow.feelings>

</@flow.feelings>