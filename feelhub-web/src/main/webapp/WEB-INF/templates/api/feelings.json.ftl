[
<#list feelingDatas as feelingData>
{
"feelingid":"${feelingData.id}",
"userId":"${feelingData.userId}",
"topicId":"${feelingData.topicId}",
"text":
[
    <#list feelingData.text as text>
    "${text?j_string}"
    ${text_has_next?string(",", "")}
    </#list>
],
"languageCode":"${feelingData.languageCode}",
"creationDate":"${feelingData.creationDate}",
    <#if feelingData.feelingValue?has_content>"feelingValue":"${feelingData.feelingValue}"</#if>
}${feelingData_has_next?string(",", "")}
</#list>
]