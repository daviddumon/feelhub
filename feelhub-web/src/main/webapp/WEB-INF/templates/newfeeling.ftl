<form id="feeling-form" autocomplete="off">
    <div id="form-left-panel">
        <select name="language">
            <option value=""></option>
        <#list locales as locale>
            <option value="${locale.code}">${locale.localizedName}</option>
        </#list>
        </select>
        <span class="help_text">Hi <#if userInfos.authenticated || !userInfos.anonymous>${userInfos.fullname}</#if>! How do you feel ?</span>
        <textarea name="text"></textarea>

        <div id="form-topics"></div>
    </div>
    <div id="form-right-panel"></div>
</form>