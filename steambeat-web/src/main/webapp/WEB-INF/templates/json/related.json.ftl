[
<#list subjects as subject>
{"id":"${subject.id}","shortDescription":"${subject.shortDescription}","description":"${subject.description}","illustration":"${subject.illustration}","url":"http://${domain}${root}${subject.uriToken}${subject.id}"}${subject_has_next?string(",", "")}
</#list>
]