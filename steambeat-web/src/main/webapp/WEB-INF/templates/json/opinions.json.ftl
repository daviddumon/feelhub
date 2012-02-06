[
<#list opinions as opinion>
{"text":"${opinion.text?j_string}","feeling":"${opinion.judgments[0].feeling}","subjectId":"${opinion.judgments[0].subject.id}","shortDescription":"${opinion.judgments[0].subject.shortDescription}"}${opinion_has_next?string(",", "")}
</#list>
]