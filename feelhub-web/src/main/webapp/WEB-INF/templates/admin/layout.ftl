<#macro adminLayout page>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Feelhub.com - Administration</title>
        <meta charset="utf-8"/>
        <link href='//fonts.googleapis.com/css?family=Droid+Serif|Autour+One|Spicy+Rice|Roboto+Condensed' rel='stylesheet' type='text/css'>
        <#if !dev>
        <link rel="stylesheet" href="${root}/static/css/reset.css?cache=${buildtime}"/>
        <link rel="stylesheet" type="text/css" href="${root}/static/css/admin.css?cache=${buildtime}"/>
        <#else>
        <link rel="stylesheet/less" type="text/css" href="${root}/static/css/reset.less?cache=${buildtime}"/>
        <link rel="stylesheet/less" type="text/css" href="${root}/static/css/admin.less?cache=${buildtime}"/>
        <script type="text/javascript" src="${root}/static/js/lib/less-1.3.0.min.js?cache=${buildtime}"></script>
        </#if>
    </head>
    <body>
        <div id="${page}">
            <#nested>
        </div>
    </body>
</#macro>