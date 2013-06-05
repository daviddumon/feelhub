<@fixed.jsprod>
<script type="text/javascript" data-main="${root}/static/js/controller-built/fixed-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@fixed.jsprod>

<@fixed.jsdev>
<script type="text/javascript" data-main="${root}/static/js/controller/fixed-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@fixed.jsdev>

<@fixed.js>
</@fixed.js>

<@fixed.body>
<div id="help">
    <h1>Welcome !</h1>

    <p>Welcome to FeelHub! </p>

    <p>Please drag this bookmarklet to your bookmarks bar : <a id="bookmarklet" href="javascript:<#include '../bookmarklet/bookmarklet-snippet.js' />">Feelhub!</a></p>

    <a href="${root}">return to home</a>
</div>
</@fixed.body>
