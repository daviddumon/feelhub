[<#list opinions as opinion>
{"text":"${opinion.text}","feeling":"${opinion.feeling}","subjectId":"${opinion.subjectId}"}${opinion_has_next?string(",", "")}
</#list>]