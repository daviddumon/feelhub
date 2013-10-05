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
];

    <#if statistics?? && (statistics?size > 0)>
    var statistics = {
    good: ${statistics[0].good},
    neutral: ${statistics[0].neutral},
    bad: ${statistics[0].bad}
    };
    <#else>
    var statistics = {
    good: 0,
    neutral: 0,
    bad: 0
    };
    </#if>
</@base.js>

<@base.body>
<div id="overlay"></div>
    <#include 'elements/login.ftl'/>
    <#include 'elements/signup.ftl'/>
    <#include 'elements/help.ftl'/>
    <#include "elements/header.ftl"/>

<div id="topic-container" class="group">

<div class="topic-column">

    <#if topicData.uris?? && (topicData.uris?size > 0)>
    <a id="current-topic" href="${topicData.uris[0]}" class="topic-element" rel="nofollow" target="_blank">
    <#else>
    <div id="current-topic" class="topic-element">
    </#if>
    <div class="wrapper">
        <img src="${topicData.thumbnail}" class="illustration"/>
        <span>${topicData.name}</span>
    </div>
    <#if topicData.uris?? && (topicData.uris?size > 0)>
    </a>
    <#else>
    </div>
    </#if>

    <div id="analytics" class="topic-element">
        <#if statistics?? && (statistics?size > 0)>
            <span id="counter">${statistics[0].good + statistics[0].neutral + statistics[0].bad} feelings</span>
        <#else>
            <span id="counter">0 feelings</span>
        </#if>
        <canvas id="pie" class="pie-canvas">no feelings</canvas>
    </div>

    <#if topicData.uris?? && (topicData.uris?size > 0)>
        <div id="uris" class="topic-element">
            <#list topicData.uris as uri>
                <img src="${root}/static/images/search-dark.png" class="linkicon"/>
                <a href="${uri}" class="uris" rel="nofollow" target="_blank">${uri}</a>
            </#list>
        </div>
    </#if>

    <div id="related" class="topic-element">
        <#list relatedDatas as related>
            <a href="${root}/topic/${related.id}">
                <div class="wrapper">
                    <img src="${related.thumbnail}" class="illustration"/>
                    <span>${related.name}</span>
                </div>
            </a>
        </#list>
    </div>

</div>

<div class="topic-column">

    <form id="feeling-form" autocomplete="off" class="topic-element">
        <textarea name="comment"></textarea>
        <span class="help-text">How do you feel about that ?</span>
        <canvas id="feeling-value-good" feeling-value="good" class="feeling-canvas"></canvas>
        <canvas id="feeling-value-neutral" feeling-value="neutral" class="feeling-canvas"></canvas>
        <canvas id="feeling-value-bad" feeling-value="bad" class="feeling-canvas"></canvas>
    </form>

    <#if feelingDatas?? && (feelingDatas?size > 0)>
        <ul id="feelings" class="topic-element"></ul>
    </#if>
</div>

</div>

</@base.body>