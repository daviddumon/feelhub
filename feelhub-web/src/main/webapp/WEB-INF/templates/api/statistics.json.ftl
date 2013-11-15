[
<#list statistics as stat>
{"happy":"${stat.happy}","sad":"${stat.sad}","bored":"${stat.bored}"}${stat_has_next?string(",", "")}
</#list>
]