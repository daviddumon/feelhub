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
                                "thumbnailLarge": "${sentimentData.thumbnailLarge?j_string}",
                                "thumbnailMedium": "${sentimentData.thumbnailMedium?j_string}",
                                "thumbnailSmall": "${sentimentData.thumbnailSmall?j_string}",
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
<div id="dashboard">
    <ul>
        <li id="dashboard-name" class="li-border">
            <div class="topic topic-large topic-center topic-no-cursor"><img src="${topicData.thumbnailLarge}" class="illustration"/><span>${topicData.name}</span></div>
        </li>
        <li id="dashboard-sentiment">
            <div class="holder">
                <canvas id="canvas-sentiment" width="120" height="120">
                    <img class="smiley good" src="${root}/static/images/smiley_good_white.png"/>
                </canvas>
            </div>
        </li>
        <div class="li-title">informations</div>
        <li id="dashboard-info">
            <div class="holder"><span class="name">Name : ${topicData.name}</span></div>
            <div class="holder"><span class="type">Category : ${topicData.type}</span></div>
            <div class="holder"><span class="description">${topicData.description}</span></div>
            <div class="holder">
                <#list topicData.uris as uri>
                    <img src="${root}/static/images/search-dark.png" class="linkicon"/>
                    <a href="${uri}" class="uris" rel="nofollow" target="_blank">${uri}</a>
                </#list>
            </div>
            <div class="holder">
                <#list topicData.subTypes as subtype>
                    <span class="subtypes">${subtype}</span>
                </#list>
            </div>
        </li>
        <div class="li-title">related topics</div>
        <li id="dashboard-related">
            <#if relatedDatas?has_content>
                <div class="holder">
                    <div class="related">
                        <#list relatedDatas as related>
                            <a href="${root}/topic/${related.id}" class="topic topic-small topic-inline topic-spacer"><img src="${related.thumbnailSmall}" class="illustration"/><span>${related.name}</span></a>
                        </#list>
                    </div>
                </div>
            </#if>
        </li>
        <div class="li-title">medias</div>
        <li id="dashboard-medias">
            <div class="holder">
                <#if mediaDatas?has_content>
                    <div class="medias">
                        <#list mediaDatas as media>
                            <a href="${root}/topic/${media.id}" class="topic topic-small topic-inline topic-spacer"><img src="${media.thumbnailSmall}" class="illustration"/><span>${media.type}</span></a>
                        </#list>
                    </div>
                </#if>
            </div>
        </li>
    </ul>
</div>
</@flow.dashboard>

<@flow.command classes="with-dashboard">
    <#include "newfeeling.ftl"/>
</@flow.command>

<@flow.feelings>

</@flow.feelings>