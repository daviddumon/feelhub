<#macro left>
<body>
<!--<a id="feedback" class="vertical roundeddown" href="${root}/webpages/http://www.steambeat.com">feedback</a>-->

<#--<div id="help">-->
<#--To post opinion about webpages, drag this button to your bookmark bar !!-->
<#--<a id="bookmarklet" href="javascript:<#include 'bookmark.ftl' />" title="Say it !">Say it !</a>-->
<#--</div>-->

<header>
<#--<img src="${root}/static/images/steambeat.png" alt="steambeat logo" id="logo"/>-->
<#--<div id="help_button" class="footer_button">?</div>-->
<#--<div id="about_button" class="footer_button">i</div>-->
</header>

<div id="left_panel" class="rounded_top_left">
    <#nested/>
</div>

</#macro>

<#macro right>
<div id="right_panel" class="rounded_top_right">
    <#nested/>
</div>

<div id="expand_button" class="rounded_down vertical">more</div>

<div id="main">
<#--<div id="add_opinion"></div>-->

<#--<form action="" method="post" id="post_opinion" autocomplete="off">-->
<#--<textarea id="newopinion" name="text"></textarea>-->
<#--<input id="submit_opinion" type="submit" value="ok"/>-->
<#--</form>-->
    <ul id="opinions">
    </ul>
</div>

</body>
</html>
</#macro>