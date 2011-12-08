<#macro left>
    <body>
    <!--<a id="feedback" class="vertical roundeddown" href="${root}/webpages/http://www.steambeat.com">feedback</a>-->

    <div id="help">
        To post opinion about webpages, drag this button to your bookmark bar !!
        <a id="bookmarklet" href="javascript:<#include 'bookmark.ftl' />" title="Say it !">Say it !</a>
    </div>

    <header>
        <div id="inner">
            <!--<div id="innerleft">-->
            <!--<img src="${root}/static/images/steambeat.png" alt="steambeat logo" id="logo"/>-->
            <#nested/>
            <!--</div>-->
            <!--<div id="innerright">-->
            <!---->
            <!--</div>-->
        </div>
        <div id="expand_button" class="roundeddown vertical">
            <div id="expand_button_inner" class="roundeddown">more</div>
        </div>
    </header>


    <!--<footer>-->
    <!--<div id="footerlinks">-->
    <!--<a href="${root}/help">help</a>-->
    <!--<a href="${root}/terms">terms</a>-->
    <!--<a href="${root}/about">about</a>-->
    <!--</div>-->
    <!--<div id="copyright">-->
    <!--&copy bytedojo 2011-->
    <!--</div>-->
    <!--</footer>-->
</#macro>

<#macro right>
    <ul id="right">
        <#nested/>
    </ul>

    </body>
    </html>
</#macro>