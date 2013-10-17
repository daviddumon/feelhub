<div id="bookmarkletinstall" class="popup">
    <a class="top-close-button close-button" href="javascript:void(0);">CLOSE</a>

    <div id="step-1" class="bookmarkletinstall-part">
        <div class="popup-title">
            Get the Feelhub bookmarklet
        </div>

        <p>You can use our bookmarklet to create topics while browsing the web.</p>

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

            <img id="step-2-chrome-img-1" src="/static/images/bookmarkletinstall/chrome-1.jpg"/>
            <img id="step-2-chrome-img-2" src="/static/images/bookmarkletinstall/chrome-2.jpg"/>
            <img id="step-2-chrome-img-3" src="/static/images/bookmarkletinstall/chrome-3.jpg"/>

            <p>Open the Google Chrome menu by clicking the 3 stripes button right next to the address bar</p>

            <p>In the list, click the "bookmarks" item</p>

            <p>Select the menu's item "Show Bookmarks bar"</p>
        </div>

        <div class="popup-bottom">
            &nbsp;<a class="previous-button" href="javascript:void(0);">PREVIOUS</a><a class="next-button" href="javascript:void(0);">NEXT</a>
        </div>
    </div>

    <div id="step-3" class="bookmarkletinstall-part">
        <div class="popup-title">
            Get the Feelhub bookmarklet
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