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
"sentimentDatas":   [
    <#list feelingData.sentimentDatas as sentimentData>
    {
        <#if sentimentData.id?has_content>"id":"${sentimentData.id}",</#if>
    "sentimentValue":"${sentimentData.sentimentValue}",
    "name":"${sentimentData.name?j_string}",
    "thumbnail":"${sentimentData.thumbnail?j_string}",
    "type":"${sentimentData.type}"
    }${sentimentData_has_next?string(",", "")}
    </#list>
]
}${feelingData_has_next?string(",", "")}
</#list>
]