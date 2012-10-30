[
<#list illustrations as illustration>
{"referenceId":"${illustration.referenceId?j_string}","link":"${illustration.link}"}${illustration_has_next?string(",", "")}
</#list>
]