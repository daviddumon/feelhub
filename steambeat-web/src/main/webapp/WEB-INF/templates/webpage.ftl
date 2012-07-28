<@layout.headbegin>
<script type="text/javascript">
    var steamId = "${steam.getId()}";
</script>
</@layout.headbegin>

<@layout.cssprod>
<link rel="stylesheet" href="${root}/static/css/flow.css?${buildtime}"/>
</@layout.cssprod>

<@layout.cssdev>
<link rel="stylesheet/less" type="text/css" href="${root}/static/css/flow.less?${buildtime}"/>
</@layout.cssdev>

<@layout.jsprod>
</@layout.jsprod>

<@layout.jsdev>
</@layout.jsdev>

<@layout.js>
<script type="text/javascript" src="${root}/static/js/hub.js?${buildtime}"></script>
<script type="text/javascript" src="${root}/static/js/form.js?${buildtime}"></script>
<script type="text/javascript" src="${root}/static/js/lib/flow.js?${buildtime}"></script>
<script type="text/javascript" src="${root}/static/js/subject.js?${buildtime}"></script>
<script type="text/javascript">
    var subjectId = "${webPage.getId()}";
</script>
</@layout.js>

<@layout.mustache>
    <#include "mustache/judgment.mustache.js">
    <#include "mustache/opinion.mustache.js">
    <#include "mustache/related.mustache.js">
    <#include "mustache/form_judgment.mustache.js">
    <#include "mustache/form_block.mustache.js">
</@layout.mustache>

<@layout.headend>

</@layout.headend>

<@layout.fixed>
<div id="panel">

    <#include "includes/counters.ftl"/>

    <div class="panel_box">
        <div id="webpageRoot"><span onclick="javascript:window.open('${webPage.uri}');" style="cursor: pointer">${webPage.description}</span></div>
        <div id="illustration"><img <#if ''?matches('${webPage.illustration}')> style='display: none;' <#else> src="${webPage.illustration}" </#if> /></div>
    </div>
    <div id="related_list" class="panel_box">
        <span>related</span>
    </div>
</div>

<a href="" id="form_button" style="display: none">add your opinion</a>
</@layout.fixed>

<@layout.body>
    <#include "includes/forms.ftl"/>

<ul id="opinions"></ul>
</@layout.body>