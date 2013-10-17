<div id="bookmarkletinstall" class="popup">
    <a class="top-close-button close-button" href="javascript:void(0);">CLOSE</a>

    <div id="step-1" class="bookmarkletinstall-part">
        <div class="popup-title">
            You can use our bookmarklet to create topics while browsing the web
        </div>

        <iframe width="564" height="348" src="//www.youtube.com/embed/psZ8bIC1Lpc?rel=0" frameborder="0" allowfullscreen></iframe>

        <p>It's very simple, just click the next button to begin !</p>

        <div class="popup-bottom">
            &nbsp;<a class="next-button" href="javascript:void(0);">NEXT</a>
        </div>
    </div>

    <div id="step-2" class="bookmarkletinstall-part">
        <div class="popup-title">
            Activate the Google Chrome Bookmarks bar
        </div>

        <div id="step-2-chrome">

            <div class="horizontal-block">
                <img id="step-2-chrome-img-1" src="/static/images/bookmarkletinstall/chrome-1.jpg"/>
                <img id="step-2-chrome-img-2" src="/static/images/bookmarkletinstall/chrome-2.jpg"/>
                <img id="step-2-chrome-img-3" src="/static/images/bookmarkletinstall/chrome-3.jpg"/>
            </div>

            <div class="horizontal-block">
                <p>Open the Google Chrome menu by clicking the 3 stripes button located right next to the address bar</p>

                <p>In the list, click the "Bookmarks" item</p>

                <p>Select the menu's item "Show Bookmarks bar"</p>
            </div>
        </div>

        <div class="popup-bottom">
            &nbsp;<a class="previous-button" href="javascript:void(0);">PREVIOUS</a><a class="next-button" href="javascript:void(0);">NEXT</a>
        </div>
    </div>

    <div id="step-3" class="bookmarkletinstall-part">
        <div class="popup-title">
            Drag the bookmarklet to your bookmarks bar
        </div>

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

        <p>Click and drag the above button to your bookmarks bar !</p>

        <div class="popup-bottom">
            &nbsp;<a class="previous-button" href="javascript:void(0);">PREVIOUS</a><a class="next-button" href="javascript:void(0);">NEXT</a>
        </div>
    </div>

    <div id="step-4" class="bookmarkletinstall-part">
        <div class="popup-title">
            Get the Feelhub bookmarklet
        </div>

        <div class="popup-bottom">
            &nbsp;<a class="previous-button" href="javascript:void(0);">PREVIOUS</a><a class="close-button" href="javascript:void(0);">END</a>
        </div>
    </div>

</div>