<@fixed.jsprod>
<script type="text/javascript" data-main="${root}/static/js/controller-built/login-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@fixed.jsprod>

<@fixed.jsdev>
<script type="text/javascript" data-main="${root}/static/js/controller/login-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@fixed.jsdev>

<@fixed.js>
<script type="text/javascript">
    var referrer = "${referrer}";
</script>
</@fixed.js>

<@fixed.body>
<form id="login">
    <h1>WELCOME</h1>

    <div class="holder">
        <#if !userInfos.anonymous>
            <p>Hello ${userInfos.fullname} !</p>

            <p>Please enter your password, or <a href="javascript:void(0);" class="logout">change user</a> !</p>
            <input name="email" value="${userInfos.email}" type="text" autocomplete="off" maxlength="100" style="display: none"/>
        <#else>
            <span class="help_text">Email</span>
            <input name="email" value="" type="text" autocomplete="off" maxlength="100"/>
        </#if>

        <div class="error_text"></div>
    </div>

    <div class="holder">
        <span class="help_text">Password</span>
        <input name="password" value="" type="password" autocomplete="off" maxlength="100"/>

        <div class="error_text"></div>
    </div>
    <div class="holder">
        <input name="remember" type="checkbox" id="login_remember"/><label for="login_remember">remember me</label>

        <div class="error_text"></div>
    </div>
    <div class="holder">
        <a id="login_submit" href="" class="call-to-action">LOGIN</a>

        <div class="social">or you may sign in with</div>
        <a href="${facebookUrl}" id="facebook_login">Sign in with Facebook</a>
        <a href="${googleUrl}" id="google_login">Sign in with Google</a>
    </div>
</form>
</@fixed.body>