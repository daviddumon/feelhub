<div id="login" class="popup">
    <a class="top-close-button close-button" href="javascript:void(0);">CLOSE</a>

    <div class="social">
        <a href="${facebookUrl}" id="facebook_login"><img src="/static/images/social/Facebook.png" alt="facebook login"/></a>
        <a href="${googleUrl}" id="google_login"><img src="/static/images/social/Google.png" alt="google login"/></a>
    </div>

    <form>
        <div class="holder">
        <#if !userInfos.anonymous>
            <p>Hello ${userInfos.fullname} !</p>

            <p>Please enter your password, or <a href="javascript:void(0);" class="logout-popup">change user</a> !</p>
            <input id="email" name="email" value="${userInfos.email}" type="text" autocomplete="off" maxlength="100" style="display: none"/>
        <#else>
            <input id="email" name="email" value="" type="text" autocomplete="off" maxlength="100" placeholder="Email"/>
        </#if>
        </div>

        <div class="holder">
            <input name="password" value="" type="password" autocomplete="off" maxlength="100" placeholder="Password"/>
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