<@layout.headbegin>
</@layout.headbegin>

<@layout.cssprod>
<link rel="stylesheet" href="${root}/static/css/noflow_layout.css?${buildtime}"/>
</@layout.cssprod>

<@layout.cssdev>
<link rel="stylesheet/less" type="text/css" href="${root}/static/css/noflow_layout.less?${buildtime}"/>
</@layout.cssdev>

<@layout.jsprod>
</@layout.jsprod>

<@layout.jsdev>
</@layout.jsdev>

<@layout.js>
<script type="text/javascript" src="${root}/static/js/login.js?${buildtime}"></script>
</@layout.js>

<@layout.mustache>
</@layout.mustache>

<@layout.headend>

</@layout.headend>

<@layout.body>
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
        <input name="remember" type="checkbox"/><span>remember me</span>

        <div class="error_text"></div>
    </div>
    <div class="holder">
        <a id="login_submit" href="">login</a>
    or
    <a href="${facebookUrl}">login with facebook</a>
    </div>
</form>
</@layout.body>