<@head.headbegin>
</@head.headbegin>

<@head.cssprod>
<link rel="stylesheet" href="${root}/static/css/noflow.css?cache=${buildtime}"/>
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
    <div id="up">
        <a href="">sign in</a>
    </div>
    <div id="left">
        <h2>Welcome to <span>Feelhub<span>.com</span></span></h2>
        <h1>Share your feelings with the world!</h1>
    </div>
    <div id="right">
        <form id="signup">
            <h1 class="font_title">JOIN TODAY !</h1>

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
                <p>What is your favorite language :</p>
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

                <div class="social">or</div>
                <a href="${facebookUrl}" class="facebook_login">
                    <img src="${root}/static/images/facebook_login.png"/>
                </a>
            </div>
        </form>
    </div>
</div>

<div id="panel-2">
    <ul>
        <li><span class="li-title">Share your feelings about websites using our bookmarklet</span></li>
        <li><span class="li-title">Connect differents feelings together with semantic detection</span></li>
        <li><span class="li-title">Feelhub learns from what you say!</span></li>
    </ul>
</div>

<div id="panel-3">
    &copy; 2013 Feelhub
</div>
</body>
</html>