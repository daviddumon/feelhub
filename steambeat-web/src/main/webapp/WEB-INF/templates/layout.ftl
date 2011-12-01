<#macro left>
    <body>

    <a id="feedback" class="vertical" href="${root}/webpages/http://www.steambeat.com">feedback</a>

    <header>
        <div id="inner">
            <div style="float: left;border: 1px solid white">
                <div style="width: 14px; height: 14px; background-color: #66CC33; float: left;border: 1px solid white; border-bottom-right-radius: 5px"></div>
                <div style="width: 14px; height: 14px; background-color: #FF3333; float: left;border: 1px solid white; border-bottom-left-radius: 5px"></div>
                <div style="clear: both;width: 14px; height: 14px; background-color: #FF3333; float: left;border: 1px solid white; border-top-right-radius: 5px"></div>
                <div style="width: 14px; height: 14px; background-color: #66CC33; float: left;border: 1px solid white; border-top-left-radius: 5px"></div>
            </div>
            <#nested/>
        </div>
    </header>

    <footer>
        <div id="footerlinks">
            <a href="${root}/help">help</a>
            <a href="${root}/terms">terms</a>
            <a href="${root}/about">about</a>
        </div>
        <div id="copyright">
            &copy bytedojo 2011
        </div>
    </footer>
</#macro>

<#macro right>
    <ul id="right">
        <#nested/>
    </ul>

    </body>
    </html>
</#macro>