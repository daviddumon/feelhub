<header class="color_bg_lightblue">
    <a id="home_link" class="button hover_medblue font_title color_darkblue" href="${root}">steambeat</a>

    <form id="signup" action="${root}/signup" method="POST">
        <input name="email" type="text"/>
        <input name="password" type="password"/>
        <input type="submit"/>
    </form>
    <form id="login" action="${root}/sessions" method="POST">
        <input name="email" type="text"/>
        <input name="password" type="password"/>
        <input type="submit"/>
    </form>
    <p id="header_right" class="font_title color_darkblue">Drag this bookmarklet to your bookmarks bar : <a id="bookmarklet" href="javascript:<#include 'bookmarklet.js' />" class="rounded font_title color_darkblue">Steambeat!</a></p>
</header>