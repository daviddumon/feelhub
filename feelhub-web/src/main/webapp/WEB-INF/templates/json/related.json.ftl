[
<#list topicDataList as topicData>
{"topicId":"${topicData.topicId}","illustrationLink":"${topicData.illustrationLink}","keywordValue":"${topicData.keywordValue}","languageCode":"${topicData.languageCode}"}${topicData_has_next?string(",", "")}
</#list>
]