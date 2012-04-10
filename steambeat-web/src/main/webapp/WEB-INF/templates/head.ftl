<#macro withHead>
<!DOCTYPE html>
<!-- Copyright Bytedojo SAS 2011 -->
<html lang="en">
<head>
    <link href='http://fonts.googleapis.com/css?family=Droid+Serif' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Droid+Sans' rel='stylesheet' type='text/css'>
    <title>Steambeat</title>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <meta name="keywords" content="sentiment analysis"/>
    <#--<meta name="viewport" content="width=device-width, initial-scale=1.0">-->
    <meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <link rel="stylesheet" href="${root}/static/css/reset.css?${buildtime}"/>
    <link rel="stylesheet" href="${root}/static/css/common.css?${buildtime}"/>
    <link rel="stylesheet" href="${root}/static/css/hub.css?${buildtime}"/>
    <link rel="stylesheet" href="${root}/static/css/header.css?${buildtime}"/>
    <link rel="stylesheet" href="${root}/static/css/panel.css?${buildtime}"/>
    <link rel="stylesheet" href="${root}/static/css/form.css?${buildtime}"/>
    <link rel="stylesheet" href="${root}/static/css/flow.css?${buildtime}"/>
    <script type="text/javascript">
        var root = "http://${domain}${root}";
    </script>
    <#if !dev>
        <!--[if lt IE 9]>
        <script src="http://html5shim.googlecode.com/svn/trunk/html5.js?${buildtime}"></script>
        <script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js?${buildtime}"></script>
        <![endif]-->
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js?${buildtime}"></script>
    </#if>
    <#if dev>
        <script type="text/javascript" src="${root}/static/js/lib/jquery-1.7.1.min.js?${buildtime}"></script>
    </#if>
    <script type="text/javascript" src="${root}/static/js/lib/modernizr.custom.21481.min.js?${buildtime}"></script>
    <script type="text/javascript" src="${root}/static/js/lib/ICanHaz.min.js?${buildtime}"></script>
    <#--<script type="text/javascript" src="${root}/static/js/lib/raphael-2.0.0.min.js?${buildtime}"></script>-->
    <script type="text/javascript" src="${root}/static/js/lib/steambeatglobal.js?${buildtime}"></script>
    <#--<script type="text/javascript" src="${root}/static/js/lib/time.js?${buildtime}"></script>-->
    <#--<script type="text/javascript" src="${root}/static/js/lib/orderedlinkedlist.js?${buildtime}"></script>-->
    <#--<script type="text/javascript" src="${root}/static/js/lib/graph.js?${buildtime}"></script>-->
    <#--<script type="text/javascript" src="${root}/static/js/lib/timeline.js?${buildtime}"></script>-->
    <script type="text/javascript" src="${root}/static/js/lib/flow.js?${buildtime}"></script>
    <script type="text/javascript" src="${root}/static/js/hub.js?${buildtime}"></script>
    <script type="text/javascript" src="${root}/static/js/form.js?${buildtime}"></script>
    <#nested/>

    <#include "icanhaz/judgment.mustache.js">
    <#include "icanhaz/opinion.mustache.js">

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