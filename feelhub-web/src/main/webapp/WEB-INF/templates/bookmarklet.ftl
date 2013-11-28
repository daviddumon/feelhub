<@base.head_begin>
    <#if uri??>
    var uri = "${uri}";
    <#else>
    var uri = "";
    </#if>
</@base.head_begin>

<@base.head_production>
<link rel="stylesheet" href="${root}/static/css/fixed.css?cache=${buildtime}"/>
<script type="text/javascript" data-main="${root}/static/js/controller-built/bookmarklet-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@base.head_production>

<@base.head_development>
<link rel="stylesheet/less" type="text/css" href="${root}/static/css/fixed.less?cache=${buildtime}"/>
<script type="text/javascript" data-main="${root}/static/js/controller/bookmarklet-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@base.head_development>

<@base.head_end>

</@base.head_end>

<@base.body>
<div id="overlay"></div>
    <#include 'elements/login.ftl'/>
    <#include 'elements/signup.ftl'/>
<div id="bookmarklet" class="fixed-panel">
    <p>Please wait</p>
    <img id="loading_gif" src="${root}/static/images/ajax-loader-small.gif" border="0" alt="loading"/>
</div>
</@base.body>