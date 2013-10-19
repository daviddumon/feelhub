<div id="bookmarkletinstall" class="popup">
    <a class="top-close-button close-button" href="javascript:void(0);">CLOSE</a>

    <div id="step-1" class="bookmarkletinstall-part chrome firefox safari opera">
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

    <div id="step-2" class="bookmarkletinstall-part chrome firefox safari opera">
        <p>A bookmarklet is a button in your bookmarks bar.</p>

        <p>It's very simple to use, just click the bookmarklet while you're reading a webpage, and you're on Feelhub!</p>

        <iframe width="564" height="348" src="//www.youtube.com/embed/psZ8bIC1Lpc?rel=0&enablejsapi=1" frameborder="0" allowfullscreen></iframe>

        <div class="bottom-wrapper">
            <a id="bookmark-help-button-1" class="bookmark-help-button" href="javascript:void(0);">Ok, get the bookmarklet</a>
            <a id="bookmark-help-button-3" class="bookmark-help-button" href="javascript:void(0);">I don't see my bookmarks bar</a>
        </div>
    </div>

    <div id="step-3" class="bookmarkletinstall-part chrome">
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

    <div id="step-3" class="bookmarkletinstall-part firefox">
        <div class="popup-title">
            Activate the Firefox Bookmarks bar
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

    <div id="step-3" class="bookmarkletinstall-part safari">
        <div class="popup-title">
            Activate the Safari Bookmarks bar
        </div>

        <div class="horizontal-block">
            <img id="step-2-safari-img-1" src="/static/images/bookmarkletinstall/safari-1.jpg"/>
            <img id="step-2-safari-img-2" src="/static/images/bookmarkletinstall/safari-2.jpg"/>
        </div>

        <div class="horizontal-block">
            <p>Open the view menu.</p>

            <p>Select the menu's item "Show Bookmarks Bar"</p>
        </div>

        <div class="bottom-wrapper">
            <a id="bookmark-help-button-1" class="bookmark-help-button" href="javascript:void(0);">Ok, get the bookmarklet</a>
            <a id="bookmark-help-button-2" class="bookmark-help-button" href="javascript:void(0);">What is a bookmarklet</a>
        </div>
    </div>

    <div id="step-3" class="bookmarkletinstall-part opera">
        <div class="popup-title">
            Activate the Opera Bookmarks bar
        </div>

        <div class="horizontal-block">
            <img id="step-2-opera-img-1" src="/static/images/bookmarkletinstall/opera-1.jpg"/>
            <img id="step-2-opera-img-2" src="/static/images/bookmarkletinstall/opera-2.jpg"/>
            <img id="step-2-opera-img-3" src="/static/images/bookmarkletinstall/opera-3.jpg"/>
        </div>

        <div class="horizontal-block">
            <p>Open the view menu.</p>

            <p>Open the view menu.</p>

            <p>Select the menu's item "Show Bookmarks Bar"</p>
        </div>

        <div class="bottom-wrapper">
            <a id="bookmark-help-button-1" class="bookmark-help-button" href="javascript:void(0);">Ok, get the bookmarklet</a>
            <a id="bookmark-help-button-2" class="bookmark-help-button" href="javascript:void(0);">What is a bookmarklet</a>
        </div>
    </div>

</div>