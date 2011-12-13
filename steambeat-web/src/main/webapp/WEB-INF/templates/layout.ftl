<#macro panel>
<body>
<a id="feedback" class="vertical rounded_down greybutton" href="${root}/webpages/http://www.steambeat.com">feedback</a>

<#--<div id="help">-->
<#--To post opinion about webpages, drag this button to your bookmark bar !!-->
<#--<a id="bookmarklet" href="javascript:<#include 'bookmark.ftl' />" title="Say it !">Say it !</a>-->
<#--</div>-->

<header>
    <a id="steambeat" class="greybutton" href="${root}">steambeat</a>
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

    <div id="arrow_up" class="rounded greybutton" style="display: none"><p>&uarr;</p><br>top</div>
</div>

<footer>
    <a id="help_button" class="footer_button greybutton" href="">help</a>
    <a id="about_button" class="footer_button greybutton" href="">about</a>
</footer>

</body>
</html>
</#macro>