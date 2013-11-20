<header>
    <a href="${root}" id="home-link"></a>

    <div id="menu">
        <a href="${root}/live" class="header-link<#if tab?? && tab = 'live'> selected</#if>">live</a>
        <a href="${root}/popular" class="header-link<#if tab?? && tab = 'popular'> selected</#if>">popular</a>
        <a href="${root}/new" class="header-link<#if tab?? && tab = 'new'> selected</#if>">new</a>
    </div>

<#if userInfos.authenticated>
    <div id="user">
        <p>${userInfos.user.fullname}</p>
        <ul>
            <li><a class="header-button" href="${root}/getbookmarklet">bookmarklet</a></li>
            <li><a id="logout" class="header-button" href="javascript:void(0);">LOG OUT</a></li>
        </ul>
    </div>
<#else>
    <div id="login-panel">
        <a id="login-button" href="javascript:void(0);">login</a>
        <a id="signup-button" href="javascript:void(0);">signup</a>
    </div>
</#if>
</header>