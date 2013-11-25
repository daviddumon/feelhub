<div id="signup" class="popup">
    <a class="top-close-button close-button" href="javascript:void(0);">CLOSE</a>

    <div class="social">
        <a href="${facebookUrl}" id="facebook_login"><img src="/static/images/social/Facebook.png" alt="facebook login"/></a>
        <a href="${googleUrl}" id="google_login"><img src="/static/images/social/Google.png" alt="google login"/></a>
    </div>

    <form>
        <div class="holder">
            <input id="fullname" name="fullname" value="" type="text" autofocus autocomplete="off" maxlength="100" placeholder="Name"/>
        </div>

        <div class="holder">
            <input name="email" value="" type="text" autocomplete="off" maxlength="100" placeholder="Email"/>
        </div>

        <div class="holder">
            <input name="password" value="" type="password" autocomplete="off" maxlength="100" placeholder="Password"/>
        </div>

        <div class="holder">
            <label for="language-select">language : </label>
            <select name="language" id="language-select">
                <option value=""></option>
            <#if locales??>
                <#list locales as locale>
                    <#if locale.code == preferedLanguage>
                        <option selected="true" value="${locale.code}">${locale.localizedName}</option>
                    <#else>
                        <option value="${locale.code}">${locale.localizedName}</option>
                    </#if>
                </#list>
            </#if>
            </select>
        </div>

        <div class="holder">
            <a id="signup_submit" href="" class="call-to-action">SIGNUP</a>
        </div>
    </form>

    <div class="popup-bottom">
        Already have an account ? <a id="login-button" href="javascript:void(0);">LOGIN</a>
    </div>

</div>