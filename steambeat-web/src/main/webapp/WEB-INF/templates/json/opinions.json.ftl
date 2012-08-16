[
<#list opinions as opinion>
{"text":"${opinion.text?j_string}","feeling":"${opinion.judgments[0].feeling}","referenceId":"${opinion.judgments[0].referenceId}"}${opinion_has_next?string(",", "")}
</#list>
]