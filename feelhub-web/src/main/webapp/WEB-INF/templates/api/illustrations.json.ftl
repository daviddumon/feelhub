[
<#list illustrations as illustration>
{"topicId":"${illustration.topicId?j_string}","link":"${illustration.link}"}${illustration_has_next?string(",", "")}
</#list>
]