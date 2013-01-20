<#macro headbegin>
<!DOCTYPE html>
<!-- Copyright Feelhub 2012 -->
<html lang="en">
<head>
    <link href='//fonts.googleapis.com/css?family=Droid+Serif|Autour+One|Spicy+Rice' rel='stylesheet' type='text/css'>
    <title>Feelhub.com - Share your feelings with the world!</title>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <meta name="keywords" content="sentiment analysis"/>
    <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">

    <#nested/>

</#macro>

<#macro cssprod>

    <#if !dev>
        <link rel="stylesheet" href="${root}/static/css/reset.css?${buildtime}"/>
        <link rel="stylesheet" href="${root}/static/css/common.css?${buildtime}"/>
        <#nested/>
    </#if>

</#macro>

<#macro cssdev>

    <#if dev>
        <link rel="stylesheet/less" type="text/css" href="${root}/static/css/reset.less?${buildtime}"/>
        <link rel="stylesheet/less" type="text/css" href="${root}/static/css/common.less?${buildtime}"/>
        <#nested/>
    </#if>

</#macro>

<#macro jsprod>

    <#if !dev>
        <!--[if lt IE 9]>
        <script src="https://html5shim.googlecode.com/svn/trunk/html5.js?${buildtime}"></script>
        <script src="https://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js?${buildtime}"></script>
        <![endif]-->
        <#nested/>
    </#if>

</#macro>

<#macro jsdev>

    <#if dev>
        <script type="text/javascript" src="${root}/static/js/lib/less-1.3.0.min.js?${buildtime}"></script>
        <#nested/>
    </#if>

</#macro>

<#macro js>
    <script type="text/javascript">
        var root = "${root}";
        var authentificated = ${userInfos.authenticated?string};
        var languageCode = "${userInfos.languageCode}";
        <#if !userInfos.anonymous>
        var userId = "${userInfos.user.id}";
        <#else>
        var userId = "";
        </#if>
    </script>

    <script type="text/javascript" src="${root}/static/js/lib/modernizr.custom.21481.min.js?${buildtime}"></script>
    <script type="text/javascript" src="${root}/static/js/image-scaling.js?${buildtime}"></script>
    <#nested/>

</#macro>

<#macro headend>

    <#nested/>

    <#if !dev>
        <script type="text/javascript">
            var uvOptions = {};
            (function () {
                var uv = document.createElement('script');
                uv.type = 'text/javascript';
                uv.async = true;
                uv.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'widget.uservoice.com/G00yvG6dRhvx0Oag2Yn2Yw.js';
                var s = document.getElementsByTagName('script')[0];
                s.parentNode.insertBefore(uv, s);
            })();
        </script>

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
</#macro>