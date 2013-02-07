<@noflow.jsprod>
<script type="text/javascript" data-main="${root}/static/js/controller-built/signup-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@noflow.jsprod>

<@noflow.jsdev>
<script type="text/javascript" data-main="${root}/static/js/controller/signup-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@noflow.jsdev>

<@noflow.js>
</@noflow.js>

<@noflow.body>
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
                <option value="${locale.code}">${locale.name}</option>
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
</@noflow.body>