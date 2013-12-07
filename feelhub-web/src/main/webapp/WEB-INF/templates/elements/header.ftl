<header>
    <a href="${root}" id="home-link"><span>beta</span></a>

<#if userInfos.authenticated>
    <div id="user">
        <a href="">
            <img src="//www.gravatar.com/avatar/${userInfos.hashedEmail}?s=31&d=mm" alt="user's avatar" class="img-circle"/>

            <p>${userInfos.user.fullname}</p>
        </a>
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