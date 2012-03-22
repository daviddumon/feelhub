[
<#list statistics as stat>
{"good":"${stat.goodJudgments}","bad":"${stat.badJudgments}"}${stat_has_next?string(",", "")}
</#list>
]