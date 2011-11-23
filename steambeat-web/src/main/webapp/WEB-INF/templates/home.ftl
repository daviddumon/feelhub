<!DOCTYPE html>
<!-- Copyright Bytedojo SAS 2011 -->
<html lang="en">
<head>
    <title>Steambeat</title>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <meta name="keywords" content="sentiment analysis"/>
    <link rel="stylesheet" href="${root}/static/css/reset.css"/>
    <link rel="stylesheet" href="${root}/static/css/header.css"/>
    <link rel="stylesheet" href="${root}/static/css/main.css"/>
    <link rel="stylesheet" href="${root}/static/css/footer.css"/>
    <link rel="icon" href="${root}/favicon.ico">
    <#if !dev>
        <!--[if lt IE 9]>
        <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
    </#if>
    <#if dev>
        <script type="text/javascript" src="${root}/static/js/lib/jquery-1.7.min.js"></script>
    </#if>
    <script type="text/javascript" src="${root}/static/js/lib/modernizr.custom.21481.min.js"></script>
    <script type="text/javascript" src="${root}/static/js/lib/raphael-2.0.0.min.js"></script>
    <script type="text/javascript" src="${root}/static/js/lib/steambeat.js"></script>
    <script type="text/javascript" src="${root}/static/js/steambeat.jquery.js"></script>
    <script type="text/javascript" src="${root}/static/js/lib/time.js"></script>
    <script type="text/javascript" src="${root}/static/js/lib/orderedlinkedlist.js"></script>
    <script type="text/javascript" src="${root}/static/js/lib/graph.js"></script>
    <script type="text/javascript" src="${root}/static/js/lib/timeline.js"></script>
    <script type="text/javascript" src="${root}/static/js/lib/flow.js"></script>
    <script type="text/javascript" src="${root}/static/js/layout.js"></script>
    <script type="text/javascript">
        var root = "http://${domain}${root}";
    </script>
    <#if !dev>
        <script>

        </script>
    </#if>
</head>

<body>
<!--<a id="feedback" href="${root}/webpages/http://www.steambeat.com"></a>-->
<!---->
<!--<header>-->
    <!--<a id="slogan" href="${root}/">Free speech everywhere</a>-->
    <!---->
    <!--<div id="counter">${counter!0} opinions</div>-->
    <!---->
    <!--<span class="copyright">&copy; steambeat.com 2011</span>-->
    <!--<span class="contact"><a href="mailto:contact@steambeat.com">contact</a></span>-->
    <!--<br>-->
    <!--<span class="bytedojo">brought to you by-->
    <!--<a href="http://www.bytedojo.com" target="_blank"><img src="${root}/static/images/bytedojo_logo.png" alt="logo bytedojo" id="bytedojologo"/></a> and-->
    <!--<a href="http://www.arpinum.fr" target="_blank"><img src="${root}/static/images/arpinum.png" alt="logo arpinum" id="arpinumlogo"/></a></span>-->
    <!--<br><br><br>-->
    <!--<span class="alchemy">-->
        <!--<img src="${root}/static/images/alchemyAPI.jpg" alt="AlchemyAPI - Transforming Text Into Knowledge" id="alchemylogo"/>-->
        <!--steambeat is using Alchemy API-->
    <!--</span>-->

    <div id="timeline_display"></div>

<!--</header>-->

<script type="text/javascript" src="${root}/static/js/home.js"></script>

<!--<span id="webpageselector">LAST OPINIONS</span>-->
<!--<ul id="webpage"></ul>-->

</body>
</html>