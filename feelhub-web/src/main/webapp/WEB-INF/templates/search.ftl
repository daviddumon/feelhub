<@flow.js>
<script type="text/javascript" src="${root}/static/js/form.js?${buildtime}"></script>
<script type="text/javascript" src="${root}/static/js/search.js?${buildtime}"></script>
<script type="text/javascript">
    var q = "${q}";
</script>
</@flow.js>

<@flow.dashboard>
<div class="box">
    <span id="slogan">Existing topics for ${q}</span>
</div>
</@flow.dashboard>

<@flow.command>
<a href="${root}/newtopic/${q}" id="create_topic">create a new topic</a>
</@flow.command>