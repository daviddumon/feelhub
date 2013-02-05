<@noflow.jsprod>
<script type="text/javascript" data-main="${root}/static/js/controller-built/common-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@noflow.jsprod>

<@noflow.jsdev>
<script type="text/javascript" data-main="${root}/static/js/controller/common-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@noflow.jsdev>

<@noflow.js>
</@noflow.js>

<@noflow.body>
<div id="help">
    <h1>Welcome !</h1>

    <p>Please use the activation link in the mail we sent you before logging in feelhub</p>

    <p>Drag this bookmarklet to your bookmarks bar : <a id="bookmarklet" href="javascript:<#include 'bookmarklet/bookmarklet.js' />">Feelhub!</a></p>

    <a href="${root}">return to home</a>
</div>
</@noflow.body>
