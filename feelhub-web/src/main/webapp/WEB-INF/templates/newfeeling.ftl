<form id="feeling_form" autocomplete="off">
    <div class="hidden-form">
        <select name="language">
            <option value=""></option>
            <#list locales as locale>
                <option value="${locale.code}">${locale.name}</option>
            </#list>
        </select>
        <textarea name="text"></textarea>
        <#if topicData??>
            <input type="hidden" name="topicId" value="${topicData.id}"/>
        </#if>
    </div>
    <button type="submit" class="call-to-action">
        SHARE MY FEELING
    </button>
    <div id="triangle"></div>
</form>