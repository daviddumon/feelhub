<header>
    <a href="${root}" id="home-link"></a>
    <#--<img src="${root}/static/images/logo.png"/>-->

    <div id="actions">
    <#if userInfos.authenticated>
        <a id="logout" class="header-button" href="javascript:void(0);">LOGOUT</a>
    <#else>
        <a id="login-button" class="header-button" href="javascript:void(0);">LOGIN</a>
        <a id="signup-button" class="header-button" href="javascript:void(0);">SIGNUP</a>
    </#if>
    </div>

    <div id="header-message">
    <#if userInfos.authenticated>
        Hello ${userInfos.user.fullname} !
    </#if>
    </div>
</header>