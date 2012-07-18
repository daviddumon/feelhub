<@head.begin>
</@head.begin>

<@head.cssprod>
<link rel="stylesheet" href="${root}/static/css/signup.css?${buildtime}"/>
</@head.cssprod>

<@head.cssdev>
<link rel="stylesheet/less" type="text/css" href="${root}/static/css/signup.less?${buildtime}"/>
</@head.cssdev>

<@head.jsprod>
</@head.jsprod>

<@head.jsdev>
</@head.jsdev>

<@head.js>
<script type="text/javascript" src="${root}/static/js/signup.js?${buildtime}"></script>
<script type="text/javascript">

</script>
</@head.js>

<@head.mustache>

</@head.mustache>

<@head.end>

</@head.end>

<@info.panel>

<form id="signup">
    <h1 class="font_title">JOIN TODAY !</h1>

    <div class="holder">
        <span class="help_text font_text">Full name</span>
        <input name="fullname" value="" type="text" class="font_text" autofocus autocomplete="off" maxlength="100" aria-required="true"/>

        <div class="error_text"></div>
    </div>
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
        <p class="font_text">What is your favorite language :</p>
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
        <a id="signup_submit" href="" class="color_bg_darkblue rounded font_title color_darkblue">Create my account</a>
    </div>
</form>
</@info.panel>
