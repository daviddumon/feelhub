<#macro panel>
<body>
<a id="feedback" class="vertical rounded_down" href="${root}/webpages/http://www.steambeat.com">feedback</a>

<#--<div id="help">-->
<#--To post opinion about webpages, drag this button to your bookmark bar !!-->
<#--<a id="bookmarklet" href="javascript:<#include 'bookmark.ftl' />" title="Say it !">Say it !</a>-->
<#--</div>-->

<header>
<#--<div id="help_button" class="footer_button">?</div>-->
<#--<div id="about_button" class="footer_button">i</div>-->
</header>

<div id="panel" class="rounded_top">
    <#nested/>
</div>

<div id="main">
<#--<div id="add_opinion"></div>-->

<#--<form action="" method="post" id="post_opinion" autocomplete="off">-->
<#--<textarea id="newopinion" name="text"></textarea>-->
<#--<input id="submit_opinion" type="submit" value="ok"/>-->
<#--</form>-->
    <ul id="opinions">
    </ul>

    <div id="arrow_up" class="rounded" style="display: none"><p>&uarr;</p><br>top</div>
</div>

</body>
</html>
</#macro>