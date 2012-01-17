<#macro panel>
<body class="textfont">

<div id="overlay_top" class="rounded_top shadow"></div>
<div id="overlay_bottom" class="rounded_top shadow"></div>
<!--<div id="overlay_shadow" class="rounded_top shadow"></div>-->

<!--
<a id="feedback" class="vertical_right rounded_top button shadow titlefont" href="${root}/webpages/http://www.steambeat.com">feedback</a>
-->

<form id="opinion_form" method="post" action="" autocomplete="off">
        <div id="opinion_form_title" class="titlefont button">ADD YOUR OPINION !</div>

        <div id="opinion_form_inner_wrapper" style="display: none">
            <textarea id="opinion_form_textarea" name="text" class="textfont"></textarea>

            <div id="opinion_form_judgments" class="titlefont">
            <span id="add_subject1" class="subject_tag good titlefont add_tag">this webpage<span class="subject_info titlefont">You like!</span><input
                                type="hidden"
                                name="feeling"
                                value="good"></input></span>
            </div>
            <input id="opinion_form_submit" type="submit" value="ok" class="button titlefont rounded"/>
         </div>

         <div id="form_arrow"></div>
         <div id="form_arrow_border"></div>
</form>

<header>
    <a id="homelink" class="button titlefont" href="http://${domain}${root}/">steambeat</a>
    <p id="header_right" class="titlefont">Drag this bookmarklet to your bookmarks bar : <a id="bookmarklet" href="javascript:<#include 'bookmark.ftl' />" class="rounded titlefont">Steambeat!</a></p>
</header>

<div id="panel">
    <#nested/>
</div>

<div class="protector">
    <h1 class="ribbon_right">
        <strong class="ribbon_right_content">OPINIONS</strong>
    </h1>
</div>

<ul id="opinions"></ul>
<!--<img src="${root}/static/images/scroll.png" id="scroll_to_top" class="rounded_down button" style="display: none"></img>-->

<!--
<footer>
    <span id="about_button" class="footer_button button titlefont">about</span>
    <span id="help_button" class="footer_button button titlefont">help</span>
</footer>

<div id="about_dialog" title="About" style="display: none;">
    <#include "about.ftl">
</div>

<div id="help_dialog" title="Help" style="display: none;">
    <#include "help.ftl">
</div>
-->

</body>
</html>
</#macro>