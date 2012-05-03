[
<#list subjects as subject>
{"id":"${subject.id}","shortDescription":"${subject.shortDescription}","description":"${subject.description}","url":"http://${domain}${root}${subject.uriToken}${subject.id}"}${subject_has_next?string(",", "")}
</#list>
]