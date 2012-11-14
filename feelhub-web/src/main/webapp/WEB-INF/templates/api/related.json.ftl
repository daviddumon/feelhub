[
<#list keywordDataList as keywordData>
{
"topicId":"${keywordData.topicId}",
"illustrationLink":"${keywordData.illustrationLink}",
"keywordValue":"${keywordData.keywordValue}",
"languageCode":"${keywordData.languageCode}",
"typeValue":"${keywordData.typeValue}"}
${keywordData_has_next?string(",", "")}
</#list>
]