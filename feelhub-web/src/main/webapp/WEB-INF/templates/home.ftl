<@base.head_begin>

var initial_datas = [
    <#if topicDatas??>
        <#list topicDatas as data>
        {
        "id":"${data.id}",
        "thumbnail":"${data.thumbnail?json_string}",
        "name":"${data.name?json_string}",
        "happyFeelingCount":${data.happyFeelingCount?c},
        "sadFeelingCount":${data.sadFeelingCount?c},
        "boredFeelingCount":${data.boredFeelingCount?c},
        "viewCount":${data.viewCount?c},
        "popularity":${data.popularity?c},
        "creationDate":${data.creationDate?c},
        "lastModificationDate":${data.lastModificationDate?c}
        }${data_has_next?string(",", "")}
        </#list>
    </#if>
]

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

<ul id="flow">
    <li class="flow-element">
        <div id="add-topic">
            <h2>Add a topic</h2>

            <input type="text" class="form-control" placeholder="Topic name or url"/>

            <div class="buttons">
                <button type="button" class="btn btn-success btn-lg" id="create-http-topic">
                    <i class="glyphicon glyphicon-globe"></i>
                </button>
                <button type="button" class="btn btn-success btn-lg" id="create-real-topic">
                    <i class="glyphicon glyphicon-comment"></i>
                </button>
            </div>
        </div>
    </li>
</ul>
</@base.body>