<@flow.jsprod>
<script type="text/javascript" data-main="${root}/static/js/controller-built/topic-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@flow.jsprod>

<@flow.jsdev>
<script type="text/javascript" data-main="${root}/static/js/controller/topic-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
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
<form id="feeling_form" autocomplete="off">
    <select name="language">
        <option value=""></option>
        <#list locales as locale>
            <option value="${locale.code}">${locale.name}</option>
        </#list>
    </select>
    <textarea name="text"></textarea>
    <input type="hidden" name="topicId" value="${topicData.id}"/>
    <button type="submit">
        share my feeling
    </button>
</form>
</@flow.command>