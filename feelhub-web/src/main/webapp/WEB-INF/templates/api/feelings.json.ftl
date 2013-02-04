[
<#list feelingDatas as feelingData>
{
"feelingid":"${feelingData.id}",
"text":
[
    <#list feelingData.text as text>
    "${text?j_string}"
    ${text_has_next?string(",", "")}
    </#list>
],
"languageCode":"${feelingData.languageCode}",
"userId":"${feelingData.userId}",
"feeling_sentiment_value":"${feelingData.feelingSentimentValue}",
"sentimentDatas":   [
    <#list feelingData.sentimentDatas as sentimentData>
    {
    <#if sentimentData.id?has_content>"id":"${sentimentData.id}",</#if>
    "sentimentValue":"${sentimentData.sentimentValue}",
    "name":"${sentimentData.name?j_string}",
    <#if sentimentData.thumbnailLarge?has_content>"thumbnailLarge":"${sentimentData.thumbnailLarge?j_string}",</#if>
    <#if sentimentData.thumbnailMedium?has_content>"thumbnailMedium":"${sentimentData.thumbnailMedium?j_string}",</#if>
    <#if sentimentData.thumbnailSmall?has_content>"thumbnailSmall":"${sentimentData.thumbnailSmall?j_string}",</#if>
    "type":"${sentimentData.type}"
    }${sentimentData_has_next?string(",", "")}
    </#list>
]
}${feelingData_has_next?string(",", "")}
</#list>
]