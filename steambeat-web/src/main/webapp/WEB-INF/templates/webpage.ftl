<@header.withHeader>
<script type="text/javascript" src="${root}/static/js/webpage.js"></script>
</@header.withHeader>

<@layout.left>
<a id="bookmarklet" href="javascript:<#include 'bookmark.ftl' />" title="Say it !">Say it !</a>
<div id="counter">${counter!0} opinions</div>
<div id="resource">
    <span id="webpageRoot"><a href="#" onclick="javascript:window.open('${webPage.id}');">${webPage.id}</a></span>
</div>
<form action="${root}/webpages/${webPage.id}/opinions" method="post" id="post_opinion" autocomplete="off">
    <div id="opiniontype"></div>
    <textarea id="newopinion" name="text" autofocus="on"></textarea>
    <input id="submit_good" type="submit" value="good"/>
    <input id="submit_bad" type="submit" value="bad"/>
    <input id="submit_neutral" type="submit" value="neutral"/>
</form>
</@layout.left>

<@layout.right>

</@layout.right>