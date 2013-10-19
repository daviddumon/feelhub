<div id="bookmarkletinstall" class="popup">
    <a class="top-close-button close-button" href="javascript:void(0);">CLOSE</a>

    <div id="step-1-chrome" class="bookmarkletinstall-part">
        <img src="/static/images/bookmarkletinstall/bookmarklet_arrow.png"/>

        <div id="top-wrapper">
        <#if dev>
            <a href="javascript: (function () {
                    var jsCode = document.createElement('script');
                    jsCode.setAttribute('src', '${root}/static/js/bookmarklet/bookmarklet-dev.js');
                    document.body.appendChild(jsCode);
                 }());" id="bookmarklet">Feelhub</a>
        <#else>
            <a href="javascript: (function () {
                        var jsCode = document.createElement('script');
                        jsCode.setAttribute('src', '${root}/static/js/bookmarklet/bookmarklet-prod.js');
                        document.body.appendChild(jsCode);
                 }());" id="bookmarklet">Feelhub</a>
        </#if>

            <p>Drag this bookmarklet to your bookmarks bar</p>
            <a id="bookmark-help-button-2" class="bookmark-help-button" href="javascript:void(0);">What is a bookmarklet</a>
            <a id="bookmark-help-button-3" class="bookmark-help-button" href="javascript:void(0);">I don't see my bookmarks bar</a>
        </div>

    </div>

    <div id="step-2-chrome" class="bookmarkletinstall-part">
        <p>A bookmarklet is a button in your bookmarks bar.</p>

        <p>It's very simple to use, just click the bookmarklet while you're reading a webpage, and you're on Feelhub!</p>

        <iframe width="564" height="348" src="//www.youtube.com/embed/psZ8bIC1Lpc?rel=0&enablejsapi=1" frameborder="0" allowfullscreen></iframe>

        <div class="bottom-wrapper">
            <a id="bookmark-help-button-1" class="bookmark-help-button" href="javascript:void(0);">Ok, get the bookmarklet</a>
            <a id="bookmark-help-button-3" class="bookmark-help-button" href="javascript:void(0);">I don't see my bookmarks bar</a>
        </div>
    </div>

    <div id="step-3-chrome" class="bookmarkletinstall-part">
        <div class="popup-title">
            Activate the Google Chrome Bookmarks bar
        </div>

        <div class="horizontal-block">
            <img id="step-2-chrome-img-1" src="/static/images/bookmarkletinstall/chrome-1.jpg"/>
            <img id="step-2-chrome-img-2" src="/static/images/bookmarkletinstall/chrome-2.jpg"/>
            <img id="step-2-chrome-img-3" src="/static/images/bookmarkletinstall/chrome-3.jpg"/>
        </div>

        <div class="horizontal-block">
            <p>Open the Google Chrome menu by clicking the 3 stripes button located right next to the address bar</p>

            <p>In the list, click the "Bookmarks" item</p>

            <p>Finally, select the menu's item "Show Bookmarks bar"</p>
        </div>

        <div class="bottom-wrapper">
            <a id="bookmark-help-button-1" class="bookmark-help-button" href="javascript:void(0);">Ok, get the bookmarklet</a>
            <a id="bookmark-help-button-2" class="bookmark-help-button" href="javascript:void(0);">What is a bookmarklet</a>
        </div>
    </div>

    <div id="step-1-firefox" class="bookmarkletinstall-part">
        <img src="/static/images/bookmarkletinstall/bookmarklet_arrow.png"/>

        <div id="top-wrapper">
        <#if dev>
            <a href="javascript: (function () {
                        var jsCode = document.createElement('script');
                        jsCode.setAttribute('src', '${root}/static/js/bookmarklet/bookmarklet-dev.js');
                        document.body.appendChild(jsCode);
                     }());" id="bookmarklet">Feelhub</a>
        <#else>
            <a href="javascript: (function () {
                            var jsCode = document.createElement('script');
                            jsCode.setAttribute('src', '${root}/static/js/bookmarklet/bookmarklet-prod.js');
                            document.body.appendChild(jsCode);
                     }());" id="bookmarklet">Feelhub</a>
        </#if>

            <p>Drag this bookmarklet to your bookmarks bar</p>
            <a id="bookmark-help-button-2" class="bookmark-help-button" href="javascript:void(0);">What is a bookmarklet</a>
            <a id="bookmark-help-button-3" class="bookmark-help-button" href="javascript:void(0);">I don't see my bookmarks bar</a>
        </div>
    </div>

    <div id="step-2-firefox" class="bookmarkletinstall-part">
        <p>A bookmarklet is a button in your bookmarks bar.</p>

        <p>It's very simple to use, just click the bookmarklet while you're reading a webpage, and you're on Feelhub!</p>
        <iframe width="564" height="348" src="//www.youtube.com/embed/psZ8bIC1Lpc?rel=0&enablejsapi=1" frameborder="0" allowfullscreen></iframe>
        <div class="bottom-wrapper">
            <a id="bookmark-help-button-1" class="bookmark-help-button" href="javascript:void(0);">Ok, get the bookmarklet</a>
            <a id="bookmark-help-button-3" class="bookmark-help-button" href="javascript:void(0);">I don't see my bookmarks bar</a>
        </div>
    </div>

    <div id="step-3-firefox" class="bookmarkletinstall-part">
        <div class="popup-title">
            Activate the Google Chrome Bookmarks bar
        </div>

        <div class="horizontal-block">
            <img id="step-2-firefox-img-1" src="/static/images/bookmarkletinstall/firefox-1.jpg"/>
            <img id="step-2-firefox-img-2" src="/static/images/bookmarkletinstall/firefox-2.jpg"/>
        </div>

        <div class="horizontal-block">
            <p>Open the bookmarks menu located next to the address bar.</p>

            <p>Select the menu's item "View Bookmarks Toolbar"</p>
        </div>

        <div class="bottom-wrapper">
            <a id="bookmark-help-button-1" class="bookmark-help-button" href="javascript:void(0);">Ok, get the bookmarklet</a>
            <a id="bookmark-help-button-2" class="bookmark-help-button" href="javascript:void(0);">What is a bookmarklet</a>
        </div>
    </div>
</div>