<#macro headbegin>
<!DOCTYPE html>
<!-- Copyright Steambeat 2012 -->
<html lang="en">
<head>
    <link href='https://fonts.googleapis.com/css?family=Droid+Serif' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Droid+Sans' rel='stylesheet' type='text/css'>
    <title>Steambeat</title>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <meta name="keywords" content="sentiment analysis"/>
    <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <script type="text/javascript">
        var root = "${root}";
    </script>

    <#nested/>

</#macro>

<#macro cssprod>

    <#if !dev>
        <link rel="stylesheet" href="${root}/static/css/reset.css?${buildtime}"/>
        <link rel="stylesheet" href="${root}/static/css/reference.css?${buildtime}"/>
        <link rel="stylesheet" href="${root}/static/css/opinion.css?${buildtime}"/>
        <link rel="stylesheet" href="${root}/static/css/form.css?${buildtime}"/>
        <#nested/>
    </#if>

</#macro>

<#macro cssdev>

    <#if dev>
        <link rel="stylesheet/less" type="text/css" href="${root}/static/css/reset.less?${buildtime}"/>
        <link rel="stylesheet/less" type="text/css" href="${root}/static/css/reference.less?${buildtime}"/>
        <link rel="stylesheet/less" type="text/css" href="${root}/static/css/opinion.less?${buildtime}"/>
        <link rel="stylesheet/less" type="text/css" href="${root}/static/css/form.less?${buildtime}"/>
        <#nested/>
    </#if>

</#macro>

<#macro jsprod>

    <#if !dev>
        <!--[if lt IE 9]>
        <script src="https://html5shim.googlecode.com/svn/trunk/html5.js?${buildtime}"></script>
        <script src="https://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js?${buildtime}"></script>
        <![endif]-->
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
        <#nested/>
    </#if>

</#macro>

<#macro jsdev>

    <#if dev>
        <script type="text/javascript" src="${root}/static/js/lib/jquery-1.8.2.min.js?${buildtime}"></script>
        <script type="text/javascript" src="${root}/static/js/lib/less-1.3.0.min.js?${buildtime}"></script>
        <#nested/>
    </#if>

</#macro>

<#macro js>

    <script type="text/javascript" src="${root}/static/js/lib/modernizr.custom.21481.min.js?${buildtime}"></script>
    <script type="text/javascript" src="${root}/static/js/lib/ICanHaz.min.js?${buildtime}"></script>
    <script type="text/javascript" src="${root}/static/js/lib/polling.js?${buildtime}"></script>
    <script type="text/javascript" src="${root}/static/js/lib/image-scaling.js?${buildtime}"></script>
    <script type="text/javascript" src="${root}/static/js/responsive.js?${buildtime}"></script>
    <script type="text/javascript" src="${root}/static/js/authentification.js?${buildtime}"></script>
    <#nested/>

</#macro>

<#macro mustache>

    <#nested/>

</#macro>

<#macro headend>

    <#nested/>

    <#if !dev>
        <script type="text/javascript">

            var _gaq = _gaq || [];
            _gaq.push(['_setAccount', 'UA-27608970-1']);
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

<#macro left>
<body>
<header>
    <div>
        <div>
            <a href="${root}">steambeat</a>
        </div>
    </div>
</header>
<div id="wrapper">
    <div id="left">
        <#nested/>
    </div>
</#macro>

<#macro right>
    <div id="right">
        <#nested/>
    </div>
</div>
</body>
</html>
</#macro>