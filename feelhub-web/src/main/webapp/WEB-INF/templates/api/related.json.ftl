[
<#list topicDataList as topicData>
{
"id":"${topicData.id}",
"name":"${topicData.name?j_string}",
"thumbnail":"${topicData.thumbnail?j_string}",
"type":"${topicData.type}"
}
${topicData_has_next?string(",", "")}
</#list>
]