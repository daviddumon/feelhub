<@layout.headbegin>
</@layout.headbegin>

<@layout.cssprod>
<link rel="stylesheet" href="${root}/static/css/noflow_layout.css?${buildtime}"/>
</@layout.cssprod>

<@layout.cssdev>
<link rel="stylesheet/less" type="text/css" href="${root}/static/css/noflow_layout.less?${buildtime}"/>
</@layout.cssdev>

<@layout.jsprod>
</@layout.jsprod>

<@layout.jsdev>
</@layout.jsdev>

<@layout.js>
</@layout.js>

<@layout.mustache>
</@layout.mustache>

<@layout.headend>

</@layout.headend>

<@layout.body>
<div id="help">

    <p>Drag this bookmarklet to your bookmarks bar : <a id="bookmarklet" href="javascript:<#include 'bookmarklet/bookmarklet.js' />">Feelhub!</a></p>

</div>
</@layout.body>