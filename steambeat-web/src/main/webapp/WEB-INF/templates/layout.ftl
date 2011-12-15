<#macro panel>
<body>
<a id="feedback" class="vertical rounded_down greybutton" href="${root}/webpages/http://www.steambeat.com">feedback</a>

<#--<div id="help">-->
<#--To post opinion about webpages, drag this button to your bookmark bar !!-->
<#--<a id="bookmarklet" href="javascript:<#include 'bookmark.ftl' />" title="Say it !">Say it !</a>-->
<#--</div>-->

<header>
    <a id="steambeat" class="greybutton" href="http://${domain}${root}">steambeat</a>
</header>

<div id="panel" class="rounded_top">
    <#nested/>
</div>

<div id="main">

    <div id="add_opinion_wrapper">
        <form action="" method="post" id="add_opinion_form" autocomplete="off" class="rounded" style="display: none">
            <p class="font_title">GIVE YOUR OPINION !</p>
            <textarea id="add_opinion_form_textarea" name="text" class="font_text"></textarea>

            <div id="add_opinion_form_judgments" class="font_title" style="display: none">
                <span id="add_subject1" class="subject_tag good font_title add_tag">faketag1<span class="subject_info font_title">You like!</span></span>
                <span id="add_subject2" class="subject_tag bad font_title add_tag">faketag2<span class="subject_info font_title">You don't like!</span></span>
                <span id="add_subject3" class="subject_tag good font_title add_tag">faketag3<span class="subject_info font_title">You like!</span></span>
                <span id="add_subject4" class="subject_tag good font_title add_tag">faketag4<span class="subject_info font_title">You like!</span></span>
            </div>
            <input id="add_opinion_form_submit" type="submit" value="ok" onclick="return false;" class="greybutton font_title rounded"/>
        </form>
    </div>

    <ul id="opinions">

    </ul>

    <div id="arrow_up" class="rounded greybutton" style="display: none"><p>&uarr;</p><br>top</div>
</div>

<footer>
    <span id="about_button" class="footer_button greybutton">about</span>
    <span id="help_button" class="footer_button greybutton">help</span>
</footer>

<div id="about_dialog" title="About" style="display: none;">
    <#include "about.ftl">
</div>

<div id="help_dialog" title="Help" style="display: none;">
    <#include "help.ftl">
</div>

</body>
</html>
</#macro>