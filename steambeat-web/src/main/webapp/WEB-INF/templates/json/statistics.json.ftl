[
<#list statistics as stat>
{"good":"${stat.good}","bad":"${stat.bad}","neutral":"${stat.neutral}"}${stat_has_next?string(",", "")}
</#list>
]