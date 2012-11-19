[
<#list feelingDatas as feelingData>
{
"id":"${feelingData.id}",
"text":"${feelingData.text?j_string}",
"languageCode":"${feelingData.languageCode}",
"userId":"${feelingData.userId}",
"topicDatas":   [
    <#list feelingData.topicDatas as topicData>
    {
    "id":"${topicData.id}",
    "sentimentValue":"${topicData.sentimentValue}",
    "description":"${topicData.description}",
    "illustrationLink":"${topicData.illustrationLink}",
    "type":"${topicData.type}"
    }${topicData_has_next?string(",", "")}
    </#list>
]
}${feelingData_has_next?string(",", "")}
</#list>
]