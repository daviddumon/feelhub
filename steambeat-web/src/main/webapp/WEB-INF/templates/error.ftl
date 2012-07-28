<@layout.headbegin>
</@layout.headbegin>

<@layout.cssprod>
<link rel="stylesheet" href="${root}/static/css/noflow.css?${buildtime}"/>
</@layout.cssprod>

<@layout.cssdev>
<link rel="stylesheet/less" type="text/css" href="${root}/static/css/noflow.less?${buildtime}"/>
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

<@layout.fixed>

</@layout.fixed>

<@layout.body>
<div id="error">
    <img src="${root}/static/images/smiley_bad_white.png"/>

    <p>There is a great disturbance in the Force</p>
    <a href="${root}/">return to home</a>
</div>
</@layout.body>