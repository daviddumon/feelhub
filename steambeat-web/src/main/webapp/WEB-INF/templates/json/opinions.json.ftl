[
<#list opinions as opinion>
{"text":"${opinion.text?j_string}","feeling":"${opinion.judgments[0].feeling}","topicId":"${opinion.judgments[0].topicId}"}${opinion_has_next?string(",", "")}
</#list>
]