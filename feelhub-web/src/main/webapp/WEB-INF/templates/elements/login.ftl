<div id="login" class="popup">
    <a id="close-button" href="javascript:void(0);">CLOSE</a>

    <div class="social">
        <a href="${facebookUrl}" id="facebook_login"><img src="/static/images/social/Facebook.png" alt="facebook login"/></a>
        <a href="${googleUrl}" id="google_login"><img src="/static/images/social/Google.png" alt="google login"/></a>
    </div>

    <form>
        <div class="holder">
        <#if !userInfos.anonymous>
            <p>Hello ${userInfos.fullname} !</p>

            <p>Please enter your password, or <a href="javascript:void(0);" class="logout-popup">change user</a> !</p>
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
            <input name="remember" type="checkbox" id="login_remember"/><label for="login_remember">remember me</label>
        </div>

        <div class="holder">
            <a id="login_submit" href="javascript:void(0);">LOGIN</a>
        </div>
    </form>

    <div class="popup-bottom">
        Don't have an account ? <a id="signup-button" href="javascript:void(0);">SIGNUP</a>
    </div>

</div>