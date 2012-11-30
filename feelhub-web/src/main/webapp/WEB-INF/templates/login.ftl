<@noflow.js>
<script type="text/javascript" data-main="${root}/static/js/controller/login-controller" src="${root}/static/js/require.js?${buildtime}"></script>
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

            <p>Please enter your password, or <a href="javascript:void(0);" id="logout">change user</a> !</p>
            <input name="email" value="${userInfos.email}" type="text" autocomplete="off" maxlength="100" aria-required="true" style="display: none"/>
        <#else>
            <span class="help_text">Email</span>
            <input name="email" value="" type="text" autocomplete="off" maxlength="100" aria-required="true"/>
        </#if>

        <div class="error_text"></div>
    </div>

    <div class="holder">
        <span class="help_text">Password</span>
        <input name="password" value="" type="password" autocomplete="off" maxlength="100" aria-required="true"/>

        <div class="error_text"></div>
    </div>
    <div class="holder">
        <input name="remember" type="checkbox" id="login_remember"/><label for="login_remember">remember me</label>

        <div class="error_text"></div>
    </div>
    <div class="holder">
        <a id="login_submit" href="">login</a>
        or
        <a href="${facebookUrl}">login with facebook</a>
    </div>
</form>
</@noflow.body>