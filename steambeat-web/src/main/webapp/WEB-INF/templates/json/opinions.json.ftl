[
<#list opinions as opinion>
{"text":"${opinion.text?j_string}","feeling":"${opinion.judgments[0].feeling}","subjectId":"${opinion.judgments[0].subjectId}","semantic":"${opinion.judgments[0].subject.semanticDescription}","shortDescription":"${opinion.judgments[0].subject.shortDescription?j_string}"}${opinion_has_next?string(",", "")}
</#list>
]