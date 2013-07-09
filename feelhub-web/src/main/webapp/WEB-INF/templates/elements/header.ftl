<header>
    <a id="home-link" href="${root}">Feelhub<span>.com</span></a>

<#--<form method="get" action="${root}/search" id="search">-->
<#--<input name="q" type="text" autocomplete="off"/>-->
<#--</form>-->

    <div id="login-helper">
    <#if userInfos.authenticated>
        <p>Hello ${userInfos.user.fullname} ! - <a href="javascript:void(0);" class="logout">logout</a></p>
    <#else>
        <a class="login-button" href="javascript:void(0);">login</a> or <a class="signup-button" href="javascript:void(0);">signup</a>
    </#if>
    </div>
</header>

<form id="login" class="popup">

    <div class="holder">
    <#if !userInfos.anonymous>
        <p>Hello ${userInfos.fullname} !</p>

        <p>Please enter your password, or <a href="javascript:void(0);" class="logout">change user</a> !</p>
        <input name="email" value="${userInfos.email}" type="text" autocomplete="off" maxlength="100" style="display: none"/>
    <#else>
        <span class="help_text">Email</span>
        <input name="email" value="" type="text" autocomplete="off" maxlength="100"/>
    </#if>
    </div>

    <div class="holder">
        <span class="help_text">Password</span>
        <input name="password" value="" type="password" autocomplete="off" maxlength="100"/>
    </div>

    <div class="holder">
        <input name="remember" type="checkbox" id="login_remember"/><label for="login_remember">remember me</label><a id="login_submit" href="javascript:void(0);">LOGIN</a>
    </div>

    <hr class="social"/>
    <span class="social">login with</span>

    <div class="social-logins">
        <a href="${facebookUrl}" id="facebook_login"><img src="/static/images/social/Facebook.png" alt="facebook login"/></a>
        <a href="${googleUrl}" id="google_login"><img src="/static/images/social/Google.png" alt="google login"/></a>
    </div>

</form>

<form id="signup" class="popup">

    <div class="holder">
        <span class="help_text">Full name</span>
        <input name="fullname" value="" type="text" autofocus autocomplete="off" maxlength="100"/>
    </div>

    <div class="holder">
        <span class="help_text">Email</span>
        <input name="email" value="" type="text" autocomplete="off" maxlength="100"/>
    </div>

    <div class="holder">
        <span class="help_text">Password</span>
        <input name="password" value="" type="password" autocomplete="off" maxlength="100"/>
    </div>

    <div class="holder">
        <select name="language">
            <option value=""></option>
        <#list locales as locale>
            <#if locale.code == preferedLanguage>
                <option selected="true" value="${locale.code}">${locale.localizedName}</option>
            <#else>
                <option value="${locale.code}">${locale.localizedName}</option>
            </#if>
        </#list>
        </select>
    </div>

    <div class="holder">
        <a id="signup_submit" href="" class="call-to-action">SIGNUP</a>
    </div>

    <hr class="social"/>
    <span class="social">sign up with</span>

    <div class="social-logins">
        <a href="${facebookUrl}" id="facebook_login"><img src="/static/images/social/Facebook.png" alt="facebook login"/></a>
        <a href="${googleUrl}" id="google_login"><img src="/static/images/social/Google.png" alt="google login"/></a>
    </div>
</form>