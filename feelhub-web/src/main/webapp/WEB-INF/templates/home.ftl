<@layout.headbegin>
<script type="text/javascript">
    var authentificated = ${userInfos.authenticated?string};
        <#if !userInfos.anonymous>
        var userLanguageCode = "${userInfos.user.languageCode}";
        </#if>

    var topicId = "";
    var keywordValue = "";
    var languageCode = "";
    var illustrationLink = "";
    var flow;
</script>
</@layout.headbegin>

<@layout.cssprod>
<link rel="stylesheet" href="${root}/static/css/flow_layout.css?${buildtime}"/>
</@layout.cssprod>

<@layout.cssdev>
<link rel="stylesheet/less" type="text/css" href="${root}/static/css/flow_layout.less?${buildtime}"/>
</@layout.cssdev>

<@layout.jsprod>
</@layout.jsprod>

<@layout.jsdev>
</@layout.jsdev>

<@layout.js>
<script type="text/javascript" src="${root}/static/js/form.js?${buildtime}"></script>
<script type="text/javascript" src="${root}/static/js/flow.js?${buildtime}"></script>
<script type="text/javascript" src="${root}/static/js/main.js?${buildtime}"></script>
</@layout.js>

<@layout.mustache>
    <#include "mustache/topic.html">
    <#include "mustache/feeling.html">
</@layout.mustache>

<@layout.headend>

</@layout.headend>

<@layout.body>

<div id="dashboard">
    <div class="box">
        <span id="slogan">Share your feelings with the world!</span>
    </div>
</div>
<div id="feeling-form"></div>

<ul id="feelings"></ul>
</@layout.body>