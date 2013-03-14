<header>
    <a id="home_link" href="${root}">Feelhub<span>.com</span></a>

    <form method="get" action="${root}/search" id="search">
        <input name="q" type="text" autocomplete="off"/>
    </form>
    <div id="login_helper">
    <#if userInfos.authenticated || !userInfos.anonymous>
        <p>Hello <a href="${root}/myfeelings">${userInfos.user.fullname}</a> ! - <a href="javascript:void(0);" class="logout">logout</a></p>
    </#if>
    </div>
</header>