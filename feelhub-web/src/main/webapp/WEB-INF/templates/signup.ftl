<@noflow.js>
<script type="text/javascript" data-main="${root}/static/js/controller/signup-controller" src="${root}/static/js/require.js?${buildtime}"></script>
</@noflow.js>

<@noflow.body>
<form id="signup">
    <h1 class="font_title">JOIN TODAY !</h1>

    <div class="holder">
        <span class="help_text">Full name</span>
        <input name="fullname" value="" type="text" autofocus autocomplete="off" maxlength="100" aria-required="true"/>

        <div class="error_text"></div>
    </div>
    <div class="holder">
        <span class="help_text">Email</span>
        <input name="email" value="" type="text" autocomplete="off" maxlength="100" aria-required="true"/>

        <div class="error_text"></div>
    </div>
    <div class="holder">
        <span class="help_text">Password</span>
        <input name="password" value="" type="password" autocomplete="off" maxlength="100" aria-required="true"/>

        <div class="error_text"></div>
    </div>
    <div class="holder">
        <p>What is your favorite language :</p>
        <select name="language" aria-required="true">
            <#list locales as locale>
                <option value="${locale.code}">${locale.name}</option>
            </#list>
        </select>

        <div class="error_text"></div>
    </div>
    <div class="holder">
        <a id="signup_submit" href="">Create my account</a>
        or
        <a href="${facebookUrl}">Use facebook</a>
    </div>
</form>
</@noflow.body>