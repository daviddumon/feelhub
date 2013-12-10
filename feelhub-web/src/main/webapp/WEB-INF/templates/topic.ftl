<@base.head_begin>
var initial_datas = [

    <#if feelingDatas??>
        <#list feelingDatas as feelingData>
        {
        "feelingId":"${feelingData.id}",
        "userId":"${feelingData.userId}",
        "topicId":"${feelingData.topicId}",
        "text":
        [
            <#list feelingData.text as text>
            "${text?json_string}"
            ${text_has_next?string(",", "")}
            </#list>
        ],
        "languageCode":"${feelingData.languageCode}",
        "force":"${feelingData.force}",
        "creationDate":"${feelingData.creationDate}",
            <#if feelingData.feelingValue?has_content>"feelingValue":"${feelingData.feelingValue}",</#if>
        }${feelingData_has_next?string(",", "")}
        </#list>
    </#if>
];
</@base.head_begin>

<@base.head_production>
<link rel="stylesheet" href="${root}/static/css/topic.css?cache=${buildtime}"/>
<meta name="fragment" content="!">
<script type="text/javascript" data-main="${root}/static/js/controller-built/topic-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@base.head_production>

<@base.head_development>
<link rel="stylesheet/less" type="text/css" href="${root}/static/css/topic.less?cache=${buildtime}"/>
<script type="text/javascript" data-main="${root}/static/js/controller/topic-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@base.head_development>

<@base.head_end>

</@base.head_end>

<@base.body>
    <div id="overlay"></div>
    <#include 'elements/login.ftl'/>
    <#include 'elements/signup.ftl'/>
    <#include 'elements/add.ftl'/>
    <#include "elements/header.ftl"/>

    <#if feelingDatas?? && (feelingDatas?size > 0)>
    <div id="topic-container" class="feelings">
    <#else>
    <div id="topic-container" class="nofeelings">
    </#if>
    <div id="nofeelings">There are no feelings yet, be the first !</div>
<div class="topic-column">

    <#if topicData.uris?? && (topicData.uris?size > 0)>
    <a id="current-topic" href="${topicData.uris[0]}" class="topic-element" rel="nofollow" target="_blank">
        <div id="visit">Click to visit this link</div>
    <#else>
    <div id="current-topic" class="topic-element">
    </#if>
    <div class="wrapper">
        <#if topicData.thumbnail?has_content>
            <img src="${topicData.thumbnail}" class="illustration"/>
        <#else>
            <img src="${root}/static/images/fake-illustration.png" class="illustration"/>
            <img src="${root}/static/images/ajax-loader.gif" class="loading"/>
        </#if>

        <span>${topicData.name}</span>
    </div>
    <#if topicData.uris?? && (topicData.uris?size > 0)>
    </a>
    <#else>
    </div>
    </#if>

    <div id="analytics" class="topic-element">
        <canvas id="pie" class="pie-canvas" data-happy="${topicData.happyFeelingCount}" data-bored="${topicData.boredFeelingCount}" data-sad="${topicData.sadFeelingCount}">no feelings</canvas>

        <#assign feelingsCount=topicData.happyFeelingCount + topicData.boredFeelingCount + topicData.sadFeelingCount>
        <#if topicData.happyFeelingCount &gt; topicData.sadFeelingCount && topicData.happyFeelingCount &gt; topicData.boredFeelingCount >
            <@feelingsCounter feelingsCount "happy"/>
        <#elseif topicData.sadFeelingCount &gt; topicData.happyFeelingCount && topicData.sadFeelingCount &gt; topicData.boredFeelingCount >
            <@feelingsCounter feelingsCount "sad"/>
        <#else>
            <@feelingsCounter feelingsCount "bored"/>
        </#if>
    </div>

    <#if topicData.uris?? && (topicData.uris?size > 0)>
        <div id="uris" class="topic-element">
            <#list topicData.uris as uri>
                <img src="${root}/static/images/search-dark.png" class="linkicon"/>
                <a href="${uri}" class="uris" rel="nofollow" target="_blank">${uri}</a>
            </#list>
        </div>
    </#if>

    <#if relatedDatas?? && (relatedDatas?size > 0)>
        <div id="related" class="topic-element">
            <span class="block-title">most related</span>
            <#list relatedDatas as related>
                <a href="${root}/topic/${related.id}" id="${related.id}">
                    <div class="wrapper">
                        <#if related.thumbnail?has_content>
                            <img src="${related.thumbnail}" class="illustration"/>
                        <#else>
                            <img src="${root}/static/images/unknown.png" class="illustration"/>
                        </#if>
                        <span>${related.name}</span>
                    </div>
                </a>
            </#list>
        </div>
    </#if>

</div>

    <div class="topic-column">

        <form id="feeling-form" autocomplete="off" class="topic-element">
            <textarea name="comment" placeholder="How do you feel about that ?"></textarea>

            <div class="canvas-button">
                <canvas id="feeling-value-happy" feeling-value="happy" class="feeling-canvas"></canvas>
                <div class="canvas-help-text">&nbsp;</div>
            </div>
            <div class="canvas-button">
                <canvas id="feeling-value-bored" feeling-value="bored" class="feeling-canvas"></canvas>
                <div class="canvas-help-text">&nbsp;</div>
            </div>
            <div class="canvas-button">
                <canvas id="feeling-value-sad" feeling-value="sad" class="feeling-canvas"></canvas>
                <div class="canvas-help-text">&nbsp;</div>
            </div>
        </form>

        <ul id="feelings" class="topic-element"></ul>
    </div>

</div>
    <#macro feelingsCounter count class>
    <span id="counter" class="${class}"><#if count == 0>No feelings<#elseif count == 1>1 feeling<#else>${count} feelings</#if><span>
    </#macro>
</@base.body>