[
<#list topicDataList as topicData>
{
"id":"${topicData.id}",
"name":"${topicData.name}",
    <#if topicData.thumbnailLarge?has_content>"thumbnailLarge":"${topicData.thumbnailLarge?j_string}",</#if>
    <#if topicData.thumbnailMedium?has_content>"thumbnailMedium":"${topicData.thumbnailMedium?j_string}",</#if>
    <#if topicData.thumbnailSmall?has_content>"thumbnailSmall":"${topicData.thumbnailSmall?j_string}",</#if>
"type":"${topicData.type}"
}
${topicData_has_next?string(",", "")}
</#list>
]