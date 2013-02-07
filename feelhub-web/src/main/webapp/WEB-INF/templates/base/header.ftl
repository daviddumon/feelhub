<header>
    <div id="header-top">
        <a id="home_link" href="${root}">Feelhub<span>.com</span></a>

        <div id="login_helper">

        <#if userInfos.authenticated || !userInfos.anonymous>
            <p>Hello ${userInfos.user.fullname}! - <a href="javascript:void(0);" class="logout">logout</a></p>
        </#if>
        </div>
    </div>
    <div id="header-bottom">
        <ul id="header-bottom-left">
            <li><a href="${root}">Latest</a></li>
        <#if userInfos.authenticated>
            <li><a href="${root}/myfeelings">My feelings</a></li>
        </#if>
        </ul>
        <ul id="header-bottom-right">
            <li><a href="${root}/help">About</a></li>
        </ul>
    </div>
    <form method="get" action="${root}/search" id="search">
        <input name="q" type="text" autocomplete="off"/>
    </form>
</header>