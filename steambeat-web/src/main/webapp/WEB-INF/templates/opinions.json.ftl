[
<#list opinions as opinion>
{"text":"${opinion.text?j_string}","feeling":"${opinion.feeling}","subjectId":"${opinion.subjectId}"}${opinion_has_next?string(",", "")}
</#list>
]