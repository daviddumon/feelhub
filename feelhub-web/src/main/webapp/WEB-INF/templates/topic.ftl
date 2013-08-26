<@base.head_production>
<link rel="stylesheet" href="${root}/static/css/topic.css?cache=${buildtime}"/>
<meta name="fragment" content="!">
<script type="text/javascript" data-main="${root}/static/js/controller-built/topic-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@base.head_production>

<@base.head_development>
<link rel="stylesheet/less" type="text/css" href="${root}/static/css/topic.less?cache=${buildtime}"/>
<script type="text/javascript" data-main="${root}/static/js/controller/topic-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@base.head_development>

<@base.js>
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
</@base.js>

<@base.body>
<div id="overlay"></div>
    <#include 'elements/login.ftl'/>
    <#include 'elements/signup.ftl'/>
    <#include 'elements/help.ftl'/>
    <#include "elements/header.ftl"/>

<div id="topic-container" class="group">

    <div class="topic-column">

        <div id="feelings-panel" class="topic-element">
            <div id="youfeel">
                <div id="youfeel-title">You Feel</div>
                <canvas id="canvas-youfeel"></canvas>
            </div>

            <div id="theyfeel">
                <div id="youfeel-title">They Feel</div>
                <canvas id="canvas-theyfeel"></canvas>
            </div>
        </div>

        <a id="current-topic" href="${topicData.uris[0]}" class="topic-element" rel="nofollow" target="_blank">
            <div class="wrapper">
                <img src="${topicData.thumbnail}" class="illustration"/>
                <span>${topicData.name}</span>
            </div>
        </a>

        <div id="uris" class="topic-element">
            <#list topicData.uris as uri>
                <img src="${root}/static/images/search-dark.png" class="linkicon"/>
                <a href="${uri}" class="uris" rel="nofollow" target="_blank">${uri}</a>
            </#list>
        </div>

    </div>

    <div class="topic-column">

        <div class="topic-element">
            <form id="comment-form" autocomplete="off">
                <span class="help-text">How do you feel about that ?</span>
                <textarea name="comment"></textarea>
                <input type="submit" value="good"/>
                <input type="submit" value="neutral"/>
                <input type="submit" value="bad"/>
            </form>

            <ul></ul>
        </div>

    </div>

</div>

</@base.body>