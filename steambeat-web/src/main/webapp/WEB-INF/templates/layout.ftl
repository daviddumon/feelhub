<#macro left>
    <body>

    <a id="feedback" class="vertical" href="${root}/webpages/http://www.steambeat.com">feedback</a>

    <header>
        <div id="inner">
            <img src="${root}/static/images/steambeat.png" alt="steambeat logo" id="logo"/>
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