<#macro panel>
<body class="textfont">

<a id="feedback" class="vertical_right rounded_top button shadow titlefont" href="${root}/webpages/http://www.steambeat.com">feedback</a>

<header>
    <div id="header_top_wrapper">
        <a id="logo" class="button titlefont" href="http://${domain}${root}">steambeat</a>
        <div id="header_right">
            <p class="titlefont">Drag this bookmarklet to your bookmarks bar : <a id="bookmarklet" href="javascript:<#include 'bookmark.ftl' />" class="rounded titlefont">Steambeat!</a></p>
        </div>
    </div>

    <form id="opinion_form" method="post" action="" autocomplete="off" class="rounded_down shadow">
        <div id="opinion_form_title" class="titlefont button"><span style="color: #FFF; font-size: 20px">&rarr;</span> GIVE YOUR OPINION ! <span style="color: #FFF; font-size: 20px">&larr;</span></div>

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

</header>

<div id="panel">
    <#nested/>
</div>

<ul id="opinions">
</ul>
<div id="scroll_to_top" class="rounded_down titlefont button" style="display: none">scroll to top</div>

<footer>
    <#--<span id="about_button" class="footer_button button titlefont">about</span>-->
    <#--<span id="help_button" class="footer_button button titlefont">help</span>-->
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