[
<#list referenceDataList as referenceData>
{"referenceId":"${referenceData.referenceId}","illustrationLink":"${referenceData.illustrationLink}","keywordValue":"${referenceData.keywordValue}","languageCode":"${referenceData.languageCode}"}${referenceData_has_next?string(",", "")}
</#list>
]