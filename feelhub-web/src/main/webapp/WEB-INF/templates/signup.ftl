<@head.headbegin>
</@head.headbegin>

<@head.cssprod>
<link rel="stylesheet" href="${root}/static/css/landing.css?cache=${buildtime}"/>
</@head.cssprod>

<@head.cssdev>
<link rel="stylesheet/less" type="text/css" href="${root}/static/css/landing.less?cache=${buildtime}"/>
</@head.cssdev>

<@head.jsprod>
<script type="text/javascript" data-main="${root}/static/js/controller-built/signup-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@head.jsprod>

<@head.jsdev>
<script type="text/javascript" data-main="${root}/static/js/controller/signup-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@head.jsdev>

<@head.js>
</@head.js>

<@head.headend>
</@head.headend>

<body>
<div id="panel-1">

    <div id="left">
        <h2>Feelhub<span>.com</span></h2>

        <h1>Share your feelings with the world!</h1>
    </div>

    <div id="right">
        <a href="${root}/login" id="login" class="call-to-action">I have an account</a>
        <hr/>
        <form id="signup">
            <div class="holder">
                <span class="help_text">Full name</span>
                <input name="fullname" value="" type="text" autofocus autocomplete="off" maxlength="100"/>

                <div class="error_text"></div>
            </div>
            <div class="holder">
                <span class="help_text">Email</span>
                <input name="email" value="" type="text" autocomplete="off" maxlength="100"/>

                <div class="error_text"></div>
            </div>
            <div class="holder">
                <span class="help_text">Password</span>
                <input name="password" value="" type="password" autocomplete="off" maxlength="100"/>

                <div class="error_text"></div>
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

                <div class="error_text"></div>
            </div>
            <div class="holder">
                <a id="signup_submit" href="" class="call-to-action">SIGNUP</a>

                <div class="social">or you may sign in with</div>
                <a href="${facebookUrl}" id="facebook_login">Sign in with Facebook</a>
            </div>
        </form>
    </div>
</div>

<div id="panel-2">
    <ul>
        <li>
            <div class="li-title">Express yourself</div>
            <div class="li-description">On Feelhub, you can say anything about everything!</div>
            <img src="${root}/static/images/smiley_good_white.png" id="landing-good"/>
            <img src="${root}/static/images/smiley_neutral_white.png" id="landing-neutral"/>
            <img src="${root}/static/images/smiley_bad_white.png" id="landing-bad"/>
        </li>
        <li>
            <div class="li-title">Share with others</div>
            <div class="li-description">Everybody can see your feelings and react to them!</div>
            <img src="${root}/static/images/flow.png" width="80%"/>
        </li>
        <li>
            <div class="li-title">Discover and explore</div>
            <div class="li-description">Explore other people sentiments, discover new trends and follow feelings about things you care about!</div>
            <img src="${root}/static/images/related.png" width="90%"/>
        </li>
    </ul>
</div>

<div id="panel-3">
    <ul>
        <li>&copy; 2013 Feelhub</li>
        <li>follow us on twitter : <a href="https://twitter.com/feelhub">@feelhub</a></li>
        <li><a href="mailto:contact@feelhub.com">contact</a></li>
    </ul>
</div>
</body>
</html>