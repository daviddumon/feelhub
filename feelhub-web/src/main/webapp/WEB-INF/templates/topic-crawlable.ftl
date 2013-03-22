<@flow.jsprod>
</@flow.jsprod>

<@flow.jsdev>
</@flow.jsdev>

<@flow.js>
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
<div class='flow_list' id='flow_list_0'>
    <#if feelingDatas??>
        <#list feelingDatas as feelingData>
            <li class="flow-element feeling" id="${feelingData.id}">
                <#if feelingData.feelingSentimentValue?has_content>
                    <img src="${root}/static/images/smiley_${feelingData.feelingSentimentValue}_white_14.png" class="img_${feelingData.feelingSentimentValue} feeling_sentiment_illustration"/>
                <#else>
                    <div class="feeling_spacer"></div>
                </#if>
                <#list feelingData.text as text>
                    <p>${text?j_string}&nbsp;</p>
                </#list>
                <div class="feeling_related" style="">
                    <#list feelingData.sentimentDatas as sentimentData>
                        <#if sentimentData.id?has_content>
                            <a href="${root}/topic/${sentimentData.id}" class="topic topic-float topic-spacer topic-with-sentiment topic-medium">
                                <img src="${root}/static/images/smiley_${sentimentData.sentimentValue}_white_14.png" class="img_${sentimentData.sentimentValue} topic-sentiment"/>
                                <img src="${sentimentData.thumbnailMedium?j_string}" class="illustration"/>
                                <span class="${sentimentData.sentimentValue} name">${sentimentData.name?j_string}</span>
                            </a>
                        </#if>
                    </#list>
                </div>
            </li>
        </#list>
    </#if>
</div>
</@flow.feelings>