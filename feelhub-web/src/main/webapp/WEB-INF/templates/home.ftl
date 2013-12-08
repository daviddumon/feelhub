<@base.head_begin/>

<@base.head_production>
<link rel="stylesheet" href="${root}/static/css/home.css?cache=${buildtime}"/>
<script type="text/javascript" data-main="${root}/static/js/controller-built/home-controller"
        src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@base.head_production>

<@base.head_development>
<link rel="stylesheet/less" type="text/css" href="${root}/static/css/home.less?cache=${buildtime}"/>
<script type="text/javascript" data-main="${root}/static/js/controller/home-controller"
        src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@base.head_development>

<@base.head_end>
</@base.head_end>

<@base.body>
<div id="overlay"></div>
    <#include 'elements/login.ftl'/>
    <#include 'elements/signup.ftl'/>
    <#include 'elements/add.ftl'/>
    <#include 'elements/welcome.ftl'/>

    <#if userInfos.authenticated>
        <#if bookmarkletShow??>
            <#include 'elements/bookmarkletinstall.ftl'/>
        </#if>
    </#if>

    <#include "elements/header.ftl"/>

<ul id="flow">
</ul>
</@base.body>