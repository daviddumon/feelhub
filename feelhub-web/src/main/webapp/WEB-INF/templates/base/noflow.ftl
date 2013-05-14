<#macro jsprod>
    <@head.headbegin>
    </@head.headbegin>

    <@head.cssprod>
    <link rel="stylesheet" href="${root}/static/css/noflow.css?cache=${buildtime}"/>
    </@head.cssprod>

    <@head.cssdev>
    <link rel="stylesheet/less" type="text/css" href="${root}/static/css/noflow.less?cache=${buildtime}"/>
    </@head.cssdev>

    <@head.jsprod>
        <#nested/>
    </@head.jsprod>
</#macro>

<#macro jsdev>
    <@head.jsdev>
        <#nested/>
    </@head.jsdev>
</#macro>

<#macro js>
    <@head.js>
        <#nested/>
    </@head.js>

    <@head.headend>

    </@head.headend>
</#macro>

<#macro body>
<body>
<#--<header>-->
    <#--<a id="home_link" href="${root}">Feelhub<span>.com</span></a>-->

    <#--<form method="get" action="${root}/search" id="search">-->
        <#--<input name="q" type="text" autocomplete="off"/>-->
    <#--</form>-->
    <#--<div id="login_helper">-->
        <#--<#if userInfos.authenticated || !userInfos.anonymous>-->
            <#--<p>Hello ${userInfos.user.fullname} ! - <a href="javascript:void(0);" class="logout">logout</a></p>-->
        <#--</#if>-->
    <#--</div>-->
<#--</header>-->
    <#nested/>
</body>
</html>
</#macro>