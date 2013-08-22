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
    <#if feelingData.feelingSentimentValue?has_content>"feeling_sentiment_value":"${feelingData.feelingSentimentValue}",</#if>
}${feelingData_has_next?string(",", "")}
</#list>
]