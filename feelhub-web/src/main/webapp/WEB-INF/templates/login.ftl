<@noflow.jsprod>
<script type="text/javascript" data-main="${root}/static/js/controller-built/login-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@noflow.jsprod>

<@noflow.jsdev>
<script type="text/javascript" data-main="${root}/static/js/controller/login-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@noflow.jsdev>

<@noflow.js>
<script type="text/javascript">
    var referrer = "${referrer}";
</script>
</@noflow.js>

<@noflow.body>
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

        <#--<div class="social">or</div>-->
        <#--<a href="${facebookUrl}" class="facebook_login">-->
            <#--<img src="${root}/static/images/facebook_login.png"/>-->
        <#--</a>-->
    </div>
</form>
</@noflow.body>