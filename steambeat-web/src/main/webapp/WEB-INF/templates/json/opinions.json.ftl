[
<#list opinionDatas as opinionData>
{
"text":"${opinionData.text?j_string}",
"referenceDatas":
[
    <#list opinionData.referenceDatas as referenceData>
    {"referenceId":"${referenceData.referenceId}","feeling":"${referenceData.feeling}","keywordValue":"${referenceData.keywordValue}","languageCode":"${referenceData.languageCode}","illustrationLink":"${referenceData.illustrationLink}"}${referenceData_has_next?string(",", "")}
    </#list>
]
}${opinionData_has_next?string(",", "")}
</#list>
]