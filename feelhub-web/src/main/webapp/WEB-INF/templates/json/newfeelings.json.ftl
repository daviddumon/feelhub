[
<#list feelingDatas as feelingData>
{
"id":"${feelingData.id}",
"text":"${feelingData.text?j_string}",
"languageCode":"${feelingData.languageCode}",
"userId":"${feelingData.userId}",
"referenceDatas":   [
    <#list feelingData.referenceDatas as referenceData>
    {
    "referenceId":"${referenceData.referenceId}",
    "sentimentValue":"${referenceData.sentimentValue}",
    "keywordValue":"${referenceData.keywordValue}",
    "languageCode":"${referenceData.languageCode}",
    "illustrationLink":"${referenceData.illustrationLink}"
    }${referenceData_has_next?string(",", "")}
    </#list>
]
}${feelingData_has_next?string(",", "")}
</#list>
]