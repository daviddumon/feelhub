[
<#list contextDatas as contextData>
{
"value":"${contextData.value?j_string}",
"id":"${contextData.id}",
"thumbnailSmall":"${contextData.thumbnailSmall?j_string}"
}${contextData_has_next?string(",", "")}
</#list>
]