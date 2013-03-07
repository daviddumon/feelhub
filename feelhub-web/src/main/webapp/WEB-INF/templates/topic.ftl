<@flow.jsprod>
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
<div id="carousel-wrapper">
    <div id="topic-name"><span></span></div>
    <a href="javascript:void(0);" id="carousel-prev" class="nav"></a>

    <div id="carousel">
        <ul id="dashboard">
        </ul>
    </div>

    <a href="javascript:void(0);" id="carousel-next" class="nav"></a>
</div>
</@flow.dashboard>

<@flow.command>
    <#include "newfeeling.ftl"/>
</@flow.command>