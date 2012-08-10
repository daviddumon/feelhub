[
<#list topics as topic>
{"id":"${topic.id}"}${topic_has_next?string(",", "")}
</#list>
]