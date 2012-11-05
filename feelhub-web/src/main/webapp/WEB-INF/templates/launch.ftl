<!DOCTYPE html>
<!-- Copyright Feelhub 2012 -->
<html lang="en">
<head>
    <link href='http://fonts.googleapis.com/css?family=Droid+Serif|Autour+One|Spicy+Rice' rel='stylesheet' type='text/css'>
    <title>Feelhub.com - Share your feelings with the world!</title>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <meta name="keywords" content="sentiment analysis"/>
    <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">

<#if !dev>
    <link rel="stylesheet" href="${root}/static/css/reset.css?${buildtime}"/>
    <link rel="stylesheet" href="${root}/static/css/common.css?${buildtime}"/>
    <link rel="stylesheet" href="${root}/static/css/noflow_layout.css?${buildtime}"/>
</#if>

<#if dev>
    <link rel="stylesheet/less" type="text/css" href="${root}/static/css/reset.less?${buildtime}"/>
    <link rel="stylesheet/less" type="text/css" href="${root}/static/css/common.less?${buildtime}"/>
    <link rel="stylesheet/less" type="text/css" href="${root}/static/css/noflow_layout.less?${buildtime}"/>
</#if>

<#if dev>
    <script type="text/javascript" src="${root}/static/js/lib/less-1.3.0.min.js?${buildtime}"></script>
</#if>
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
<div id="launch">
    <p>Launching soon !</p>
</div>
</body>
</html>