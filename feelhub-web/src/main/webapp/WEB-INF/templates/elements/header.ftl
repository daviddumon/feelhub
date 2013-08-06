<header>
    <a id="home-link" href="${root}">Feelhub<span>.com</span></a>

<#--<form method="get" action="${root}/search" id="search">-->
<#--<input name="q" type="text" autocomplete="off"/>-->
<#--</form>-->

    <div id="actions">
    <#if userInfos.authenticated>
        <a id="logout" class="header-button" href="javascript:void(0);">LOGOUT</a>
    <#else>
        <a id="login-button" class="header-button" href="javascript:void(0);">LOGIN</a>
        <a id="signup-button" class="header-button" href="javascript:void(0);">SIGNUP</a>
    </#if>
        <a id="help-button" class="header-button" href="javascript:void(0);">HELP</a>
    </div>

    <div id="header-message">
    <#if userInfos.authenticated>
        Hello ${userInfos.user.fullname} !
    </#if>
    </div>
</header>