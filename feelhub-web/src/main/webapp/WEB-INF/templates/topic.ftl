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
        "feelingid":"${feelingData.id}",
        "userId":"${feelingData.userId}",
        "topicId":"${feelingData.topicId}",
        "text":
        [
            <#list feelingData.text as text>
            "${text?j_string}"
            ${text_has_next?string(",", "")}
            </#list>
        ],
        "languageCode":"${feelingData.languageCode}",
        "creationDate":"${feelingData.creationDate}",
            <#if feelingData.feelingValue?has_content>"feelingValue":"${feelingData.feelingValue}",</#if>
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

        <form id="feeling-form" autocomplete="off" class="topic-element">
            <textarea name="comment"></textarea>
            <span class="help-text">How do you feel about that ?</span>
            <canvas id="feeling-value-good" feeling-value="good"></canvas>
            <canvas id="feeling-value-neutral" feeling-value="neutral"></canvas>
            <canvas id="feeling-value-bad" feeling-value="bad"></canvas>
        </form>

        <div id="feelings-panel" class="topic-element">
            <canvas id="pie"></canvas>
            <ul id="feelings"></ul>
        </div>

    </div>

</div>

</@base.body>