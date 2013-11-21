<@base.head_production>
<link rel="stylesheet" href="${root}/static/css/welcome.css?cache=${buildtime}"/>
<script type="text/javascript" data-main="${root}/static/js/controller-built/welcome-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@base.head_production>

<@base.head_development>
<link rel="stylesheet/less" type="text/css" href="${root}/static/css/welcome.less?cache=${buildtime}"/>
<script type="text/javascript" data-main="${root}/static/js/controller/welcome-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@base.head_development>

<@base.js>

</@base.js>

<@base.body>

<div id="left">
    <img id="logo" src="${root}/static/images/logo.png"/>
    <span id="catch">Feel the world</span>
</div>
<div id="right">
    <#include 'elements/login.ftl'/>
    <#include 'elements/signup.ftl'/>
</div>


</@base.body>