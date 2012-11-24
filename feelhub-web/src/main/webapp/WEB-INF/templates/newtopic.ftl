<@layout.headbegin>
<script type="text/javascript">
    var authentificated = ${userInfos.authenticated?string};
        <#if !userInfos.anonymous>
        var userLanguageCode = "${userInfos.user.languageCode}";
        </#if>

    var description = "${topicData.description}";

    var flow;
</script>
</@layout.headbegin>

<@layout.cssprod>
<link rel="stylesheet" href="${root}/static/css/flow_layout.css?${buildtime}"/>
<link rel="stylesheet" href="${root}/static/css/noflow_layout.css?${buildtime}"/>
</@layout.cssprod>

<@layout.cssdev>
<link rel="stylesheet/less" type="text/css" href="${root}/static/css/flow_layout.less?${buildtime}"/>
<link rel="stylesheet/less" type="text/css" href="${root}/static/css/noflow_layout.less?${buildtime}"/>
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
    <#include "mustache/keyword.html">
    <#include "mustache/feeling.html">
</@layout.mustache>

<@layout.headend>

</@layout.headend>

<@layout.body>
<div id="dashboard">
    <div id="main_keyword" class="box">
        <span id="slogan">No results for ${topicData.description}!</span>
    </div>
</div>
<form id="newtopic">
    <h1 class="font_title">CREATE A NEW TOPIC FOR ${topicData.description?upper_case}!</h1>

    <div class="holder">
        <a id="newtopic_submit" href="">CREATE</a>
    </div>
</form>
</@layout.body>