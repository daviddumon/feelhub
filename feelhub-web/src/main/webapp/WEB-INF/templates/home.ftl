<@flow.jsprod>
<script type="text/javascript" data-main="${root}/static/js/controller-built/home-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@flow.jsprod>

<@flow.jsdev>
<script type="text/javascript" data-main="${root}/static/js/controller/home-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@flow.jsdev>

<@flow.js>
</@flow.js>

<@flow.dashboard>
<div id="carousel-wrapper">
    <div id="topic-name"><span></span></div>
    <a href="javascript:void(0);" id="carousel-prev" class="nav"></a>

    <div id="carousel">
        <ul id="dashboard">
        </ul>
    </div>

    <a href="javascript:void(0);" id="carousel-next" class="nav"></a>
</div>
</@flow.dashboard>

<@flow.command>
    <#include "newfeeling.ftl"/>
</@flow.command>