[
<#list relations as relation>
{"id":"${relation.id}","fromId":"${relation.fromId}","toId":"${relation.toId}","weight":"${relation.weight}"}${relation_has_next?string(",", "")}
</#list>
]