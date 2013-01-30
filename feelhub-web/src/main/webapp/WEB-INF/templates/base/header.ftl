<header xmlns="http://www.w3.org/1999/html">
    <div id="header-top">
        <a id="home_link" href="${root}">Feelhub<span>.com</span></a>

        <div id="login_helper">
        <#if !userInfos.anonymous>
            <p>Hello ${userInfos.user.fullname}! - <#if userInfos.authenticated><a href="javascript:void(0);" class="logout">logout</a><#else><a href="${root}/login">login</a></#if>   </p>
        <#else>
            <p><a href="${root}/login">login</a> or <a href="${root}/signup">create account</a></p>
        </#if>
        </div>
    </div>
    <div id="header-bottom">
        <ul id="header-bottom-left">
            <li><a href="${root}">Latest</a></li>
        <#if userInfos.authenticated!false>
            <li><a href="${root}/myfeelings">My feelings</a></li>
        </#if>
        <#--<li><a href="">Near me</a></li>-->
        <#--<li><a href="">Channels</a></li>-->
        <#--<li><a href="">Trends</a></li>-->
        <#--<li><a href="">Sponsored</a></li>-->
        </ul>
        <ul id="header-bottom-right">
            <li><a href="${root}/help">About</a></li>
        </ul>
    </div>
    <form method="get" action="${root}/search" id="search">
        <input name="q" type="text" autocomplete="off"/>
    </form>
</header>