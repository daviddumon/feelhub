<@flow.js>
<script type="text/javascript" data-main="${root}/static/js/controller/search-controller" src="${root}/static/js/require.js?${buildtime}"></script>
<script type="text/javascript">
    var q = "${q}";
    var topicDatas = [
        <#list topicDatas as topicData>
            {
                "id":"${topicData.id}",
                "description":"${topicData.description}",
                "illustrationLink":"${topicData.illustrationLink}",
                "type":"${topicData.type}"
            }{topicData_has_next? string(",", "")}
        </#list>
    ]

</script>
</@flow.js>

<@flow.dashboard>
<span id="slogan">Existing topics for ${q}</span>
</@flow.dashboard>

<@flow.command>
<a href="${root}/newtopic/${q}" id="create_topic">create a new topic</a>
</@flow.command>