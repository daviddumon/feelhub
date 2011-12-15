<#macro withHeader>
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
    <link rel="stylesheet" href="${root}/static/css/reset.css"/>
    <link rel="stylesheet" href="${root}/static/css/dialog.css"/>
    <link rel="stylesheet" href="${root}/static/css/header.css"/>
    <link rel="stylesheet" href="${root}/static/css/footer.css"/>
    <link rel="stylesheet" href="${root}/static/css/core.css"/>
    <link rel="icon" href="${root}/favicon.ico">
    <script type="text/javascript">
        var root = "http://${domain}${root}";
    </script>
    <#if !dev>
        <!--[if lt IE 9]>
        <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <#--<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/jquery-ui.min.js"></script>-->
        <script type="text/javascript" src="${root}/static/js/lib/jquery-ui-1.8.16.custom.min.js"></script>
    </#if>
    <#if dev>
        <script type="text/javascript" src="${root}/static/js/lib/jquery-1.7.1.min.js"></script>
        <script type="text/javascript" src="${root}/static/js/lib/jquery-ui-1.8.16.custom.min.js"></script>
    </#if>
    <script type="text/javascript" src="${root}/static/js/lib/modernizr.custom.21481.min.js"></script>
    <script type="text/javascript" src="${root}/static/js/lib/raphael-2.0.0.min.js"></script>
    <script type="text/javascript" src="${root}/static/js/lib/steambeatglobal.js"></script>
    <script type="text/javascript" src="${root}/static/js/steambeat.jquery.js"></script>
    <script type="text/javascript" src="${root}/static/js/lib/time.js"></script>
    <script type="text/javascript" src="${root}/static/js/lib/orderedlinkedlist.js"></script>
    <script type="text/javascript" src="${root}/static/js/lib/graph.js"></script>
    <script type="text/javascript" src="${root}/static/js/lib/timeline.js"></script>
    <script type="text/javascript" src="${root}/static/js/lib/flow.js"></script>
    <script type="text/javascript" src="${root}/static/js/layout.js"></script>
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