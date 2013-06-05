<@fixed.jsprod>
<script type="text/javascript" data-main="${root}/static/js/controller-built/bookmarklet-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@fixed.jsprod>

<@fixed.jsdev>
<script type="text/javascript" data-main="${root}/static/js/controller/bookmarklet-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@fixed.jsdev>

<@fixed.js>
<script type="text/javascript">
    var uri = "${uri}";
</script>
</@fixed.js>

<@fixed.body>
<div id="wait">
    <img id="loading_gif" src="${root}/static/images/ajax-loader-green.gif"/>

    <p>We are looking for ${uri}</p>
</div>
</@fixed.body>