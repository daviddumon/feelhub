<#macro head_production>
<!DOCTYPE html>
<!-- Copyright Feelhub 2012 -->
<html lang="en">
<head>
    <link href='//fonts.googleapis.com/css?family=Playball|Droid+Serif|Autour+One|Roboto+Condensed|Lobster' rel='stylesheet' type='text/css'>

    <#if topicData??>
        <title>Feelhub.com - ${topicData.name}</title>
        <#if topicData.description?has_content>
            <meta name="description" content="${topicData.description}"/>
        <#else>
            <meta name="description" content="${topicData.name}"/>
        </#if>
    <#else>
        <title>Feelhub.com - Share your feelings with the world!</title>
        <meta name="description" content="Share your feelings with the world!"/>
    </#if>

    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0, target-densitydpi=device-dpi">

    <#if !dev>
        <!--[if lt IE 9]>
        <script src="https://html5shim.googlecode.com/svn/trunk/html5.js?cache=${buildtime}"></script>
        <script src="https://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js?cache=${buildtime}"></script>
        <![endif]-->
        <#nested/>
    </#if>

</#macro>

<#macro head_development>

    <#if dev>

        <#nested/>

    <#-- Keep the loading of less below, as we need to list the lesscss files before !-->
        <script type="text/javascript" src="${root}/static/js/less-1.3.3.min.js?cache=${buildtime}"></script>
    </#if>

</#macro>

<#macro js>
    <script type="text/javascript">
            <#if root??>
            var root = "${root}";
            </#if>

            <#if cookie??>
            var cookie = "${cookie}";
            </#if>

            <#if userInfos??>

            var authentificated = ${userInfos.authenticated?string};
            var languageCode = "${userInfos.languageCode}";

                <#if !userInfos.anonymous>
                var userId = "${userInfos.user.id}";
                <#else>
                var userId = "";
                </#if>
            </#if>

            <#if topicData??>
            var topicData = ${topicData?string};
            <#else>
            var topicData = {};
            </#if>

        var initial_messages = [
            <#if messages??>
                <#list messages as message>
                    {
                        feeling: "${message.feeling}",
                        text: "${message.text?j_string}",
                        timer: "${message.secondTimer}"
                    }${message_has_next?string(",", "")}
                </#list>
            </#if>
        ];

            <#nested/>

    </script>

    <#if !dev>
        <script type="text/javascript">
            var _gaq = _gaq || [];
            _gaq.push(['_setAccount', 'UA-35946821-1']);
            _gaq.push(['_trackPageview']);

            (function () {
                var ga = document.createElement('script');
                ga.type = 'text/javascript';
                ga.async = true;
                ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
                var s = document.getElementsByTagName('script')[0];
                s.parentNode.insertBefore(ga, s);
            })();
        </script>
    </#if>
</head>
</#macro>

<#macro body>
<body>
<div id="messages"></div>

    <#nested/>

</body>
</html>
</#macro>


