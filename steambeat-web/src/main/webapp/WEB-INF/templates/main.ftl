<@layout.headbegin>
<script type="text/javascript">
        <#if authentificated?has_content>
        var authentificated = ${authentificated?string};
        <#else >
        var authentificated = false;
        </#if>

        <#if keyword?has_content>
        var keyword = "${keyword}";
        var referenceId = "${keyword.getReferenceId()!""}";
        <#else >
        var keyword = "";
        var referenceId = "";
        </#if>

    console.log("authentificated : " + authentificated);
    console.log("keyword : " + keyword);
    console.log("referenceId : " + referenceId);
    console.log("root : " + root);
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
<script type="text/javascript" src="${root}/static/js/main.js?${buildtime}"></script>
</@layout.js>

<@layout.mustache>
<#--<#include "mustache/judgment.mustache.js">-->
<#--<#include "mustache/opinion.mustache.js">-->
<#--<#include "mustache/related.mustache.js">-->
<#--<#include "mustache/form_judgment.mustache.js">-->
<#--<#include "mustache/form_block.mustache.js">-->
<#include "mustache/reference.mustache.js">
</@layout.mustache>

<@layout.headend>

</@layout.headend>

<@layout.fixed>
<div id="panel">

    <div id="panel_left" class="panel_box">
        <#include "includes/counters.ftl"/>
    </div>

    <div id="panel_center" class="panel_box">

    </div>

    <div id="panel_right" class="panel_box">

    </div>
    <#--<div id="related_list" class="panel_box">-->
        <#--<span>related</span>-->
    <#--</div>-->
</div>

<a href="javascript:void(0);" id="form_button">add your opinion</a>
</@layout.fixed>

<@layout.body>
    <#include "includes/form.ftl"/>

<ul id="opinions"></ul>
</@layout.body>