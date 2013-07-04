<!DOCTYPE html>
<!-- Copyright Feelhub 2012 -->
<html lang="en">
<head>
    <link href='//fonts.googleapis.com/css?family=Playball|Droid+Serif|Autour+One|Roboto+Condensed|Lobster' rel='stylesheet' type='text/css'>
<#if topicData??>
    <title>Feelhub.com - ${topicData.name}</title>
    <#if topicData.description?has_content>
        <meta name="description" content="${topicData.description}"/>
    <#else>
        <meta name="description" content="${topicData.name}"/>
    </#if>
<#else>
    <title>Feelhub.com - Share your feelings with the world!</title>
    <meta name="description" content="Share your feelings with the world!"/>
</#if>

    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0, target-densitydpi=device-dpi">


<#if !dev>
    <link rel="stylesheet" href="${root}/static/css/fixed.css?cache=${buildtime}"/>
</#if>

<#if dev>
    <link rel="stylesheet/less" type="text/css" href="${root}/static/css/fixed.less?cache=${buildtime}"/>
</#if>

<#if !dev>
    <!--[if lt IE 9]>
    <script src="https://html5shim.googlecode.com/svn/trunk/html5.js?cache=${buildtime}"></script>
    <script src="https://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js?cache=${buildtime}"></script>
    <![endif]-->
    <script type="text/javascript" data-main="${root}/static/js/controller-built/login-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</#if>

<#if dev>
    <script type="text/javascript" src="${root}/static/js/less-1.3.3.min.js?cache=${buildtime}"></script>
    <script type="text/javascript" data-main="${root}/static/js/controller/login-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</#if>

    <script type="text/javascript">
        <#if root??>
        var root = "${root}";
        </#if>

        <#if cookie??>
        var cookie = "${cookie}";
        </#if>

        <#if userInfos??>

        var authentificated = ${userInfos.authenticated?string};
        var languageCode = "${userInfos.languageCode}";

            <#if !userInfos.anonymous>
            var userId = "${userInfos.user.id}";
            <#else>
            var userId = "";
            </#if>
        </#if>

        var initial_messages = [
        <#if messages??>
            <#list messages as message>
                {
                    feeling: "${message.feeling}",
                    text: "${message.text?j_string}",
                    timer: "${message.secondTimer}"
                }${message_has_next?string(",", "")}
            </#list>
        </#if>
        ];
    </script>

    <script type="text/javascript">
        var referrer = "${referrer}";
    </script>


<#if !dev>

    <script type="text/javascript">
        var _gaq = _gaq || [];
        _gaq.push(['_setAccount', 'UA-35946821-1']);
        _gaq.push(['_trackPageview']);

        (function () {
            var ga = document.createElement('script');
            ga.type = 'text/javascript';
            ga.async = true;
            ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
            var s = document.getElementsByTagName('script')[0];
            s.parentNode.insertBefore(ga, s);
        })();
    </script>
</#if>
</head>

<body>

<div id="messages"></div>

<form id="login">
    <h1>WELCOME</h1>

    <div class="holder">
    <#if !userInfos.anonymous>
        <p>Hello ${userInfos.fullname} !</p>

        <p>Please enter your password, or <a href="javascript:void(0);" class="logout">change user</a> !</p>
        <input name="email" value="${userInfos.email}" type="text" autocomplete="off" maxlength="100" style="display: none"/>
    <#else>
        <span class="help_text">Email</span>
        <input name="email" value="" type="text" autocomplete="off" maxlength="100"/>
    </#if>
    </div>

    <div class="holder">
        <span class="help_text">Password</span>
        <input name="password" value="" type="password" autocomplete="off" maxlength="100"/>
    </div>
    <div class="holder">
        <input name="remember" type="checkbox" id="login_remember"/><label for="login_remember">remember me</label>
    </div>
    <div class="holder">
        <a id="login_submit" href="" class="call-to-action">LOGIN</a>

        <div class="social">or you may sign in with</div>
        <a href="${facebookUrl}" id="facebook_login">Sign in with Facebook</a>
        <a href="${googleUrl}" id="google_login">Sign in with Google</a>
    </div>
</form>
</body>
</html>