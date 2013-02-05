<@flow.jsprod>
<script type="text/javascript" data-main="${root}/static/js/controller-built/search-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@flow.jsprod>

<@flow.jsdev>
<script type="text/javascript" data-main="${root}/static/js/controller/search-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@flow.jsdev>

<@flow.js>
<script type="text/javascript">
    var q = "${q}";
    var type = "${type}";
</script>
</@flow.js>

<@flow.dashboard>
<div id="slogan">
    <span>Topics for</span>
    <span>${q}</span>
</div>
</@flow.dashboard>

<@flow.command>
</@flow.command>