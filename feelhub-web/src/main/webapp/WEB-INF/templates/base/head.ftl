<#macro headbegin>
<!DOCTYPE html>
<!-- Copyright Feelhub 2012 -->
<html lang="en">
<head>
    <link href='//fonts.googleapis.com/css?family=Droid+Serif|Autour+One|Spicy+Rice|Roboto+Condensed' rel='stylesheet' type='text/css'>
    <title>Feelhub.com - Share your feelings with the world!</title>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <meta name="keywords" content="sentiment analysis"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">

    <#nested/>

</#macro>

<#macro cssprod>

    <#if !dev>
        <#nested/>
    </#if>

</#macro>

<#macro cssdev>

    <#if dev>
        <#nested/>
    </#if>

</#macro>

<#macro jsprod>

    <#if !dev>
        <!--[if lt IE 9]>
        <script src="https://html5shim.googlecode.com/svn/trunk/html5.js?cache=${buildtime}"></script>
        <script src="https://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js?cache=${buildtime}"></script>
        <![endif]-->
        <#nested/>
    </#if>

</#macro>

<#macro jsdev>

    <#if dev>
        <script type="text/javascript" src="${root}/static/js/lib/less-1.3.3.min.js?cache=${buildtime}"></script>
        <#nested/>
    </#if>

</#macro>

<#macro js>
    <script type="text/javascript">
        var root = "${root}";
        var authentificated = ${userInfos.authenticated?string};
        var languageCode = "${userInfos.languageCode}";
            <#if !userInfos.anonymous>
            var userId = "${userInfos.user.id}";
            <#else>
            var userId = "";
            </#if>
    </script>

    <#nested/>

</#macro>

<#macro headend>

    <#nested/>

    <#if !dev>

        <script type="text/javascript">
            reformal_wdg_domain = "feelhub";
            reformal_wdg_mode = 0;
            reformal_wdg_title = "feelhub";
            reformal_wdg_ltitle = "Leave feedback";
            reformal_wdg_lfont = "";
            reformal_wdg_lsize = "";
            reformal_wdg_color = "#FFA000";
            reformal_wdg_bcolor = "#516683";
            reformal_wdg_tcolor = "#FFFFFF";
            reformal_wdg_align = "left";
            reformal_wdg_waction = 0;
            reformal_wdg_vcolor = "#9FCE54";
            reformal_wdg_cmline = "#E0E0E0";
            reformal_wdg_glcolor = "#105895";
            reformal_wdg_tbcolor = "#FFFFFF";
            reformal_wdg_bimage = "8489db229aa0a66ab6b80ebbe0bb26cd.png";
        </script>
        <script type="text/javascript" language="JavaScript" src="http://idea.informer.com/tab6.js?domain=feelhub"></script>
        <noscript><a href="http://feelhub.idea.informer.com">feelhub feedback </a> <a href="http://idea.informer.com"><img src="http://widget.idea.informer.com/tmpl/images/widget_logo.jpg"/></a></noscript>

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