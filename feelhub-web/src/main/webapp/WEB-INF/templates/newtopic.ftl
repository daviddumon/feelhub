<@noflow.jsprod>
<script type="text/javascript" data-main="${root}/static/js/controller-built/newtopic-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@noflow.jsprod>

<@noflow.jsdev>
<script type="text/javascript" data-main="${root}/static/js/controller/newtopic-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@noflow.jsdev>

<@noflow.js>
</@noflow.js>

<@noflow.body>
<form id="newtopic">
    <h1>${topicData.name}</h1>
    <input type="hidden" name="name" value="${topicData.name}"/>

    <div class="holder">
        Choose a category :
        <select name="type">
            <option value=""></option>
            <#if types??>
                <#list types as type>
                    <option value="${type}">${type}</option>
                </#list>
            </#if>
        </select>
    </div>
    <div class="holder">
        <input type="submit" name="submit" class="create-topic call-to-action" value="CREATE"/>
    </div>
</form>
</@noflow.body>