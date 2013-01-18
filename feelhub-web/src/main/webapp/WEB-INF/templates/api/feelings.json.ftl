[
<#list feelingDatas as feelingData>
{
"id":"${feelingData.id}",
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
    "id":"${topicData.id}",
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