[
<#list references as reference>
{"id":"${reference.id}"}${reference_has_next?string(",", "")}
</#list>
]