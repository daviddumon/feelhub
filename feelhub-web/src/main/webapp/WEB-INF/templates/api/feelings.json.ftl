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
    "name":"${topicData.name}",
    "illustration":"${topicData.illustration}",
    "type":"${topicData.type}"
    }${topicData_has_next?string(",", "")}
    </#list>
]
}${feelingData_has_next?string(",", "")}
</#list>
]