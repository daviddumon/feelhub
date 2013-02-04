[
<#list topicDataList as topicData>
{
"id":"${topicData.id}",
"name":"${topicData.name}",
"thumbnailLarge":"${topicData.thumbnailLarge?j_string}",
"thumbnailMedium":"${topicData.thumbnailMedium?j_string}",
"thumbnailSmall":"${topicData.thumbnailSmall?j_string}",
"type":"${topicData.type}"
}
${topicData_has_next?string(",", "")}
</#list>
]