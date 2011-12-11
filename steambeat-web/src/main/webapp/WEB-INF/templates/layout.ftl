<#macro left>
<body>
<!--<a id="feedback" class="vertical roundeddown" href="${root}/webpages/http://www.steambeat.com">feedback</a>-->

<#--<div id="help">-->
<#--To post opinion about webpages, drag this button to your bookmark bar !!-->
<#--<a id="bookmarklet" href="javascript:<#include 'bookmark.ftl' />" title="Say it !">Say it !</a>-->
<#--</div>-->

<header>

    <div id="left_panel">
        <img src="${root}/static/images/steambeat.png" alt="steambeat logo" id="logo"/>

        <form action="" method="post" id="post_opinion" autocomplete="off">
            <textarea id="newopinion" name="text"></textarea>
            <input id="submit_opinion" type="submit" value="ok"/>
        </form>

        <div id="inner_left_panel">
            <#nested/>
        </div>
    </div>

</#macro>

<#macro right>
    <div id="right_panel">
        <#nested/>
    </div>

    <div id="expand_button" class="roundeddown vertical">more</div>

</header>

<div id="main">
    <ul id="right"></ul>
    <div id="loadmore">
        <div id="loadmore_button" class="rounded">load more</div>
    </div>
</div>

<footer>
    <div id="help_button" class="footer_button">?</div>
    <div id="about_button" class="footer_button">i</div>
</footer>

</body>
</html>
</#macro>