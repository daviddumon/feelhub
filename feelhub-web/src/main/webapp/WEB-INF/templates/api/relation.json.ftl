[
<#list topicDataList as topicData>
{
"id":"${topicData.id}",
"name":"${topicData.name}",
"illustration":"${topicData.illustration}",
"type":"${topicData.type}"
}
${topicData_has_next?string(",", "")}
</#list>
]