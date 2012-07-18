<@head.begin>
</@head.begin>

<@head.cssprod>
<link rel="stylesheet" href="${root}/static/css/login.css?${buildtime}"/>
</@head.cssprod>

<@head.cssdev>
<link rel="stylesheet/less" type="text/css" href="${root}/static/css/login.less?${buildtime}"/>
</@head.cssdev>

<@head.jsprod>
</@head.jsprod>

<@head.jsdev>
</@head.jsdev>

<@head.js>
<script type="text/javascript" src="${root}/static/js/login.js?${buildtime}"></script>
<script type="text/javascript">

</script>
</@head.js>

<@head.mustache>

</@head.mustache>

<@head.end>

</@head.end>

<@info.panel>

<form id="login">
    <h1 class="font_title">WELCOME</h1>

    <div class="holder">
        <span class="help_text font_text">Email</span>
        <input name="email" value="" type="text" class="font_text" autocomplete="off" maxlength="100" aria-required="true"/>

        <div class="error_text"></div>
    </div>
    <div class="holder">
        <span class="help_text font_text">Password</span>
        <input name="password" value="" type="password" class="font_text" autocomplete="off" maxlength="100" aria-required="true"/>

        <div class="error_text"></div>
    </div>
    <div class="holder">
        <a id="login_submit" href="" class="color_bg_darkblue rounded font_title color_darkblue">login</a>
    </div>
</form>
</@info.panel>
