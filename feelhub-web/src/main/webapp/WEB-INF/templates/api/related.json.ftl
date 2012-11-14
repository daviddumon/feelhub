[
<#list keywordDataList as keywordData>
{"topicId":"${keywordData.topicId}","illustrationLink":"${keywordData.illustrationLink}","keywordValue":"${keywordData.keywordValue}","languageCode":"${keywordData.languageCode}"}${keywordData_has_next?string(",", "")}
</#list>
]