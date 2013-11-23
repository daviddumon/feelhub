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
    <p>Share your feelings about anything on the web.</p>
    <p>You can use our bookmarklet to create new topics from any webpage.</p>
    <p>Signup now and discover what makes people feel and react.</p>
    <p>It's free, and always will be!</p>
    <img id="show" src="${root}/static/images/show.png"/>
</div>
<div class="arrow-right"></div>
<div id="right">
    <#include 'elements/login.ftl'/>
    <#include 'elements/signup.ftl'/>
</div>

</@base.body>