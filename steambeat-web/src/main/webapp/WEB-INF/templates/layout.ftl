<#macro panel>
<body>
<a id="feedback" class="vertical_right rounded_top greybutton shadow" href="${root}/webpages/http://www.steambeat.com">feedback</a>

<#--<div id="help">-->
<#--To post opinion about webpages, drag this button to your bookmark bar !!-->
<#--<a id="bookmarklet" href="javascript:<#include 'bookmark.ftl' />" title="Say it !">Say it !</a>-->
<#--</div>-->

<header>
    <div id="header_top_wrapper">
        <a id="logo" class="greybutton font_title" href="http://${domain}${root}">steambeat</a>
    </div>

    <form id="opinion_form" method="post" action="" autocomplete="off" class="rounded_down">
        <p class="font_title">GIVE YOUR OPINION !</p>

        <div id="opinion_form_inner_wrapper" style="display: none">
            <textarea id="opinion_form_textarea" name="text" class="font_text"></textarea>

            <div id="opinion_form_judgments" class="font_title">
            <span id="add_subject1" class="subject_tag good font_title add_tag">this webpage<span class="subject_info font_title">You like!</span><input
                                type="hidden"
                                name="feeling"
                                value="good"></input></span>
            </div>
            <input id="opinion_form_submit" type="submit" value="ok" class="greybutton font_title rounded"/>
         </div>
    </form>

</header>

<div id="panel">
    <#nested/>
</div>

<ul id="opinions">
</ul>
<div id="arrow_up" class="rounded_down font_title" style="display: none">scroll to top</div>

<footer>
    <#--<span id="about_button" class="footer_button greybutton">about</span>-->
    <#--<span id="help_button" class="footer_button greybutton">help</span>-->
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