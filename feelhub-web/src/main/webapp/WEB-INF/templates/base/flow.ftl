<#macro jsprod>
    <@head.headbegin>
    </@head.headbegin>

    <@head.cssprod>
    <link rel="stylesheet" href="${root}/static/css/flow.css?cache=${buildtime}"/>
    </@head.cssprod>

    <@head.cssdev>
    <link rel="stylesheet/less" type="text/css" href="${root}/static/css/flow.less?cache=${buildtime}"/>
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
    <script type="text/javascript">
            <#if topicData??>
            var topicData = ${topicData?string};
            <#else>
            var topicData = {};
            </#if>
    </script>
        <#nested/>
    </@head.js>

    <@head.headend>

    </@head.headend>
</#macro>

<#macro dashboard>
<body>

<div id="messages">
    <#if messages??>
        <#list messages as message>
            <div class="message feeling-${message.feeling}" data-second-timer="${message.secondTimer}">
                <p class="message-text">${message.text?j_string}</p>
                <div class="message-close">click to close this message</div>
            </div>
        </#list>
    </#if>
</div>

    <#nested/>

</#macro>

<#macro command classes>
<div id="right-panel" class="${classes}">
    <div id="command">
        <#if userInfos.authenticated>
            <#nested/>
        <#elseif !userInfos.anonymous>
            <div id="login-panel">
                <a href="${root}/login" class="login-button call-to-action">LOGIN</a>
            </div>
        <#else>
            <div id="login-panel">
                <a href="${root}/login" class="login-button call-to-action">LOGIN</a> <span>or</span> <a href="${root}/signup" class="signup-button call-to-action">SIGN UP</a>
            </div>
        </#if>
    </div>
</#macro>

<#macro feelings>
    <ul id="flow">
        <#nested/>
    </ul>
</div>
</body>
</html>
</#macro>

