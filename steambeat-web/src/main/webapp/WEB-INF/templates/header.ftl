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
        <link rel="stylesheet" href="${root}/static/css/layout.css"/>
        <link rel="icon" href="${root}/favicon.ico">
        <script type="text/javascript">
            var root = "http://${domain}${root}";
        </script>
        <#if !dev>
            <!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
            <![endif]-->
            <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
        </#if>
        <#if dev>
            <script type="text/javascript" src="${root}/static/js/lib/jquery-1.7.1.min.js"></script>
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
        <#if !dev>
            <script>

            </script>
        </#if>
        <#nested/>
    </head>
</#macro>