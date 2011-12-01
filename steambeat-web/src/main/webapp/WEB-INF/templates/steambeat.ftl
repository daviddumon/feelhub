<@header.withHeader>
<script type="text/javascript" src="${root}/static/js/steambeat.js"></script>
</@header.withHeader>

<@layout.left>
<a id="bookmarklet" href="javascript:<#include 'bookmark.ftl' />" title="Say it !">Say it !</a>
<div id="counter">${counter!0} opinions</div>
</@layout.left>

<@layout.right>

</@layout.right>