<header>
    <div id="add-topic">
        <div id="symbol">+</div>
        <span id="add">ADD</span>
    </div>

    <div id="top-bar">
        <a href="${root}" id="home-link"><span>beta</span></a>

        <form method="get" action="${root}/search" id="search">
            <input id="seach-query" name="q" type="text" autocomplete="off" disabled="disabled"/>
            <input id="search-submit" type="submit" value="search" disabled="disabled"/>
        </form>

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
            <a class="login-button" href="javascript:void(0);">login</a>
            <a class="signup-button" href="javascript:void(0);">signup</a>
        </div>
    </#if>

    </div>

    <div id="filters">
        <div id="filters-overlay"></div>
    <#--<div id="choose-filter">-->
    <#--<span>choose filter</span>-->
    <#--<ul>-->
    <#--<li>order</li>-->
    <#--<li>from</li>-->
    <#--<li>languages</li>-->
    <#--<li>visibility</li>-->
    <#--<li>tags</li>-->
    <#--<li>sources</li>-->
    <#--<li>feelings</li>-->
    <#--<li>safety</li>-->
    <#--</ul>-->
    <#--</div>-->
        <div id="filter-order" class="filter select-unique">
            <div class="top-filter">
                <div class="current-value"></div>
                <span class="down_arrow"></span>
            </div>
            <ul>
                <li class="select-unique">New feelings</li>
                <li class="select-unique">Popular topics</li>
                <li class="select-unique">New topics</li>
            </ul>
        </div>
        <div id="filter-from-people" class="filter select-unique">
            <div class="top-filter disabled">
                <div class="current-value"></div>
                <span class="down_arrow"></span>
            </div>
            <ul>
                <li class="select-unique">From everyone</li>
                <li class="select-unique">From me</li>
            </ul>
        </div>
        <div id="filter-languages" class="filter">
            <div class="top-filter disabled">
                <div class="current-value"></div>
                <span class="down_arrow"></span>
            </div>
            <ul>
                <li class="select-unique">All languages</li>
            <#if locales??>
                <#list locales as locale>
                    <li class="select-multiple" value="${locale.code}">${locale.localizedName}</li>
                </#list>
            </#if>
            </ul>
        </div>
        <div id="filter-visibility" class="filter" role="button">
            <div class="top-filter disabled">
                <div class="current-value"></div>
                <span class="down_arrow"></span>
            </div>
            <ul>
                <li class="select-unique">All visibility</li>
                <li class="select-multiple">Seen</li>
                <li class="select-multiple">Unseen</li>
            </ul>
        </div>
        <div id="filter-tags" class="filter select-multiple">
            <div class="top-filter disabled">
                <div class="current-value"></div>
                <span class="down_arrow"></span>
            </div>
            <ul>
                <li class="select-unique">All tags</li>
            </ul>
        </div>
        <div id="filter-sources" class="filter select-unique">
            <div class="top-filter disabled">
                <div class="current-value"></div>
                <span class="down_arrow"></span>
            </div>
            <ul>
                <li class="select-unique">All sources</li>
            </ul>
        </div>
        <div id="filter-feelings" class="filter select-unique">
            <div class="top-filter disabled">
                <div class="current-value"></div>
                <span class="down_arrow"></span>
            </div>
            <ul>
                <li class="select-unique">All feelings</li>
            <#list feelingValues as value>
                <li class="select-multiple">${value}</li>
            </#list>
            </ul>
        </div>
        <div id="filter-safety" class="filter select-unique">
            <div class="top-filter disabled">
                <div class="current-value"></div>
                <span class="down_arrow"></span>
            </div>
            <ul>
                <li class="select-unique">Not safe for work</li>
                <li class="select-unique">Safe for work</li>
            </ul>
        </div>
    </div>

</header>