<header>
    <a id="home-link" href="${root}">Feelhub<span>.com</span></a>

<#--<form method="get" action="${root}/search" id="search">-->
<#--<input name="q" type="text" autocomplete="off"/>-->
<#--</form>-->

    <div id="login-helper">
    <#if userInfos.authenticated>
        <p>Hello ${userInfos.user.fullname} ! <a href="javascript:void(0);" class="logout header-button">logout</a></p>
    <#else>
        <a id="login-button" class="header-button" href="javascript:void(0);">LOGIN</a><a id="signup-button" class="header-button" href="javascript:void(0);">SIGNUP</a>
    </#if>
    </div>
</header>