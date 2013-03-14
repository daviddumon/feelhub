<@flow.jsprod>
<script type="text/javascript" data-main="${root}/static/js/controller-built/myfeelings-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@flow.jsprod>

<@flow.jsdev>
<script type="text/javascript" data-main="${root}/static/js/controller/myfeelings-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
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
        <li id="dashboard-sentiment">
            <div class="holder">
                <canvas id="canvas-sentiment" width="120" height="120">
                    <img class="smiley good" src="${root}/static/images/smiley_good_white.png"/>
                </canvas>
            </div>
        </li>
        <div class="li-title">trends</div>
        <li id="dashboard-info">

        </li>
        <div class="li-title">most liked topics</div>
        <li id="dashboard-related">

        </li>
        <div class="li-title">most disliked topics</div>
        <li id="dashboard-medias" class="last">

        </li>
    </ul>
</div>
</@flow.dashboard>

<@flow.command>
    <#include "newfeeling.ftl"/>
</@flow.command>