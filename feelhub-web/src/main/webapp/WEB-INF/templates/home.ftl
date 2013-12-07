<@base.head_begin>
var flow_uri_end_point = "live";
</@base.head_begin>

<@base.head_production>
<link href="${root}/static/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="${root}/static/css/home.css?cache=${buildtime}"/>
<script type="text/javascript" data-main="${root}/static/js/controller-built/home-controller"
        src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@base.head_production>

<@base.head_development>
<link href="${root}/static/css/bootstrap.min.css" rel="stylesheet">
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

    <#if userInfos.authenticated>
        <#if bookmarkletShow??>
            <#include 'elements/bookmarkletinstall.ftl'/>
        </#if>
    </#if>

    <#assign tab = "live">
    <#include "elements/header.ftl"/>

<div id="filters">
    <div id="filters-overlay"></div>
    <#--<div id="choose-filter">-->
        <#--<span>choose filter</span>-->
        <#--<ul>-->
            <#--<li>order</li>-->
            <#--<li>from</li>-->
            <#--<li>languages</li>-->
            <#--<li>visibility</li>-->
            <#--<li>tags</li>-->
            <#--<li>sources</li>-->
            <#--<li>feelings</li>-->
            <#--<li>safety</li>-->
        <#--</ul>-->
    <#--</div>-->
    <div id="filter-order" class="filter select-unique">
        <div class="top-filter">
            <div class="current-value"></div>
            <span class="down_arrow"></span>
        </div>
        <ul>
            <li class="select-unique">New feelings</li>
            <li class="select-unique">Popular topics</li>
            <li class="select-unique">New topics</li>
        </ul>
    </div>
    <div id="filter-from-people" class="filter select-unique">
        <div class="top-filter">
            <div class="current-value"></div>
            <span class="down_arrow"></span>
        </div>
        <ul>
            <li class="select-unique">From everyone</li>
            <li class="select-unique">From me</li>
        </ul>
    </div>
    <div id="filter-languages" class="filter">
        <div class="top-filter">
            <div class="current-value"></div>
            <span class="down_arrow"></span>
        </div>
        <ul>
            <li class="select-unique">All languages</li>
            <#if locales??>
                <#list locales as locale>
                    <li class="select-multiple" value="${locale.code}">${locale.localizedName}</li>
                </#list>
            </#if>
        </ul>
    </div>
    <div id="filter-visibility" class="filter" role="button">
        <div class="top-filter">
            <div class="current-value"></div>
            <span class="down_arrow"></span>
        </div>
        <ul>
            <li class="select-unique">All visibility</li>
            <li class="select-multiple">Seen</li>
            <li class="select-multiple">Unseen</li>
        </ul>
    </div>
    <div id="filter-tags" class="filter select-multiple">
        <div class="top-filter">
            <div class="current-value"></div>
            <span class="down_arrow"></span>
        </div>
        <ul>
            <li class="select-unique">All tags</li>
        </ul>
    </div>
    <div id="filter-sources" class="filter select-unique">
        <div class="top-filter">
            <div class="current-value"></div>
            <span class="down_arrow"></span>
        </div>
        <ul>
            <li class="select-unique">All sources</li>
        </ul>
    </div>
    <div id="filter-feelings" class="filter select-unique">
        <div class="top-filter">
            <div class="current-value"></div>
            <span class="down_arrow"></span>
        </div>
        <ul>
            <li class="select-unique">All feelings</li>
            <#list feelingValues as value>
                <li class="select-multiple">${value}</li>
            </#list>
        </ul>
    </div>
    <div id="filter-safety" class="filter select-unique">
        <div class="top-filter">
            <div class="current-value"></div>
            <span class="down_arrow"></span>
        </div>
        <ul>
            <li class="select-unique">Safe for work</li>
            <li class="select-unique">Not safe for work</li>
        </ul>
    </div>
</div>

<ul id="flow">
<#--<li class="flow-element">-->
<#--<div id="add-topic">-->
<#--<h2>Add a topic</h2>-->
<#--&lt;#&ndash;&ndash;&gt;-->
<#--<input type="text" class="form-control" placeholder="Topic name or url"/>-->
<#--&lt;#&ndash;&ndash;&gt;-->
<#--<div class="buttons">-->
<#--<button type="button" class="btn btn-success btn-lg" id="create-http-topic">-->
<#--<i class="glyphicon glyphicon-globe"></i>-->
<#--</button>-->
<#--<button type="button" class="btn btn-success btn-lg" id="create-real-topic">-->
<#--<i class="glyphicon glyphicon-comment"></i>-->
<#--</button>-->
<#--</div>-->
<#--</div>-->
<#--</li>-->
</ul>
</@base.body>