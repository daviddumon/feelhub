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
    "topicId":"${topicData.topicId}",
    "sentimentValue":"${topicData.sentimentValue}",
    "keywordValue":"${topicData.keywordValue}",
    "languageCode":"${topicData.languageCode}",
    "illustrationLink":"${topicData.illustrationLink}"
    }${topicData_has_next?string(",", "")}
    </#list>
]
}${feelingData_has_next?string(",", "")}
</#list>
]