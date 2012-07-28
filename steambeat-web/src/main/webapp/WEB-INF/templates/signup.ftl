<@layout.headbegin>
</@layout.headbegin>

<@layout.cssprod>
<link rel="stylesheet" href="${root}/static/css/noflow.css?${buildtime}"/>
</@layout.cssprod>

<@layout.cssdev>
<link rel="stylesheet/less" type="text/css" href="${root}/static/css/noflow.less?${buildtime}"/>
</@layout.cssdev>

<@layout.jsprod>
</@layout.jsprod>

<@layout.jsdev>
</@layout.jsdev>

<@layout.js>
<script type="text/javascript" src="${root}/static/js/signup.js?${buildtime}"></script>
</@layout.js>

<@layout.mustache>
</@layout.mustache>

<@layout.headend>

</@layout.headend>

<@layout.fixed>

</@layout.fixed>

<@layout.body>
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
            <option value="English">English</option>
            <option value="French">French</option>
            <option value="German">German</option>
            <option value="Italian">Italian</option>
            <option value="Portuguese">Portuguese</option>
            <option value="Russian">Russian</option>
            <option value="Spanish">Spanish</option>
            <option value="Swedish">Swedish</option>
        </select>

        <div class="error_text"></div>
    </div>
    <div class="holder">
        <a id="signup_submit" href="">Create my account</a>
    </div>
</form>

</@layout.body>