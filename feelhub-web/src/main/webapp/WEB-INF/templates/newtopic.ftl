<@flow.js>
<script type="text/javascript" data-main="${root}/static/js/controller/newtopic-controller" src="${root}/static/js/require.js?${buildtime}"></script>
<script type="text/javascript">
        <#if types??>
        var types = [
            <#list types as type>
                "${type}"${type_has_next?string(",", "")}
            </#list>
        ]
        </#if>
</script>
</@flow.js>

<@flow.dashboard>
<div id="carousel-wrapper">
    <a href="javascript:void(0);" id="carousel-prev" class="nav"></a>

    <div id="carousel">
        <ul id="dashboard">
        </ul>
    </div>

    <a href="javascript:void(0);" id="carousel-next" class="nav"></a>
</div>
</@flow.dashboard>

<@flow.command>
<input type="submit" name="submit" value="create"/>
</@flow.command>