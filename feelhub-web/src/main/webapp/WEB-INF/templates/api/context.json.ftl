[
<#list contextDatas as contextData>
{
"value":"${contextData.value?j_string}",
"id":"${contextData.id}",
"thumbnail":"${contextData.thumbnail?j_string}"
}${contextData_has_next?string(",", "")}
</#list>
]