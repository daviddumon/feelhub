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
    </div>

    <div id="header-message">
    <#if userInfos.authenticated>
        Hello ${userInfos.user.fullname} !
    </#if>
    </div>

<#if dev>
    <a href="javascript: (function () {
        var jsCode = document.createElement('script');
        jsCode.setAttribute('src', '${root}/static/js/bookmarklet/bookmarklet-dev.js');
        document.body.appendChild(jsCode);
     }());" id="bookmarklet" style="color: #F5F5F5;">button</a>
<#else>
    <a href="javascript: (function () {
            var jsCode = document.createElement('script');
            jsCode.setAttribute('src', '${root}/static/js/bookmarklet/bookmarklet-prod.js');
            document.body.appendChild(jsCode);
     }());" id="bookmarklet" style="color: #F5F5F5;">button</a>
</#if>
</header>
<#--<div id="filters">-->
<#--popular-->
<#--most liked-->
<#--history-->
<#--in the air-->
<#--facebook-->
<#--twitter-->
<#--around me-->
<#--<div id="filters-button">FILTERS</div>-->
<#--</div>-->
