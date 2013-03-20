<#macro headbegin>
<!DOCTYPE html>
<!-- Copyright Feelhub 2012 -->
<html lang="en">
<head>
    <link href='//fonts.googleapis.com/css?family=Playball|Droid+Serif|Autour+One|Spicy+Rice|Roboto+Condensed' rel='stylesheet' type='text/css'>
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
        <script type="text/javascript" src="${root}/static/js/less-1.3.3.min.js?cache=${buildtime}"></script>
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

        <script type='text/javascript'>
            var _ues = {
                host: 'feelhub.userecho.com',
                forum: '17423',
                lang: 'en',
                tab_icon_show: false,
                tab_corner_radius: 10,
                tab_font_size: 20,
                tab_image_hash: 'ZmVlZGJhY2s%3D',
                tab_chat_hash: 'Y2hhdA%3D%3D',
                tab_alignment: 'left',
                tab_text_color: '#FFFFFF',
                tab_text_shadow_color: '#00000055',
                tab_bg_color: '#57A957',
                tab_hover_color: '#F45C5C'
            };

            (function () {
                var _ue = document.createElement('script');
                _ue.type = 'text/javascript';
                _ue.async = true;
                _ue.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'cdn.userecho.com/js/widget-1.4.gz.js';
                var s = document.getElementsByTagName('script')[0];
                s.parentNode.insertBefore(_ue, s);
            })();

        </script>

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