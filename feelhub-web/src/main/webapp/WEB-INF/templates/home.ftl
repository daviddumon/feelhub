<@base.head_production>
<link rel="stylesheet" href="${root}/static/css/home.css?cache=${buildtime}"/>
<script type="text/javascript" data-main="${root}/static/js/controller-built/home-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@base.head_production>

<@base.head_development>
<link rel="stylesheet/less" type="text/css" href="${root}/static/css/home.less?cache=${buildtime}"/>
<script type="text/javascript" data-main="${root}/static/js/controller/home-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@base.head_development>

<@base.js>
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
</@base.js>

<@base.body>
    <#include "elements/header.ftl"/>

<ul id="flow"></ul>
</@base.body>