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
"topicDatas":   [
    <#list feelingData.topicDatas as topicData>
    {
    <#if topicData.id?has_content>"id":"${topicData.id}",</#if>
    "sentimentValue":"${topicData.sentimentValue}",
    "name":"${topicData.name}",
    "illustration":"${topicData.illustration}",
    "type":"${topicData.type}"
    }${topicData_has_next?string(",", "")}
    </#list>
]
}${feelingData_has_next?string(",", "")}
</#list>
]