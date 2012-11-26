<@flow.js>
<script type="text/javascript" src="${root}/static/js/form.js?${buildtime}"></script>
</@flow.js>

<@flow.dashboard>

</@flow.dashboard>

<@flow.command>
<form id="createtopic">
    <input type="hidden" name="description" value="${topicData.description}"/>
    <select name="type">
        <#list types as type>
            <option value="${type}">${type}</option>
        </#list>
    </select>
    <input type="submit" value="go"/>
</form>
</@flow.command>