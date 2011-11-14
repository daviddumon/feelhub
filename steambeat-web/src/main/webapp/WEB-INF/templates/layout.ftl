<#macro withHeader>
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
            <script type="text/javascript" src="${root}/static/js/lib/jquery-1.6.2.min.js"></script>
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
    <img src="${root}/static/images/alpha.png" alt="alpha" id="alpha"/>
    <a id="feedback" href="${root}/feeds/http://www.steambeat.com"></a>

    <header>
        <div id="innerheader">
            <div id="inneralwaysshow">
                <a id="slogan" href="${root}/">Free speech everywhere</a>

                <form id="formsearch" action="${root}/feeds/" method="get" autocomplete="off" autofocus="on">
                    <input id="searchstring" value="search">
                    <input id="submitsearch" type="submit" value="&rarr;">
                </form>
            </div>
            <div id="help">
                <div class="parentheaderbox">
                    <div class="childheaderbox">
                        <em class="steps">1</em>
                        <img src="${root}/static/images/arrow_right.png" class="arrowsteps" alt="right"/>

                        <div class="insideheaderbox">
                            <p> Drag the button below to your bookmarks bar</p>
                            <a id="bookmarklet" href="javascript:<#include 'bookmark.ftl' />" title="Say it !">Say it !</a>
                        </div>
                    </div>
                    <div class="childheaderbox">
                        <em class="steps">2</em>
                        <img src="${root}/static/images/arrow_right.png" class="arrowsteps" alt="right"/>

                        <div class="insideheaderbox">
                            <p>Find a website you want to comment on</p>
                        </div>
                    </div>
                    <div class="childheaderbox">
                        <em class="steps">3</em>
                        <img src="${root}/static/images/arrow_right.png" class="arrowsteps" alt="right"/>

                        <div class="insideheaderbox">
                            <p>Click on the button to leave a message on steambeat !</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="counter">${counter} opinions</div>
        <div id="helpbar"></div>
        <div id="helpbutton">?</div>

    </header>

    <div id="timeline_display"></div>

    <#nested/>

</#macro>
