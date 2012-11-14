[
<#list feelingDatas as feelingData>
{
"id":"${feelingData.id}",
"text":"${feelingData.text?j_string}",
"languageCode":"${feelingData.languageCode}",
"userId":"${feelingData.userId}",
"keywordDatas":   [
    <#list feelingData.keywordDatas as keywordData>
    {
    "topicId":"${keywordData.topicId}",
    "sentimentValue":"${keywordData.sentimentValue}",
    "keywordValue":"${keywordData.keywordValue}",
    "languageCode":"${keywordData.languageCode}",
    "illustrationLink":"${keywordData.illustrationLink}"
    }${keywordData_has_next?string(",", "")}
    </#list>
]
}${feelingData_has_next?string(",", "")}
</#list>
]