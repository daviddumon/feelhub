<@layout.headbegin>
<script type="text/javascript">
        <#if authentificated?has_content>
        var authentificated = ${authentificated?string};
        <#else >
        var authentificated = false;
        </#if>

    var referenceId = "${referenceData.referenceId}";
    var keywordValue = "${referenceData.keywordValue}";
    var languageCode = "${referenceData.languageCode}";
    var illustrationLink = "${referenceData.illustrationLink}";

    console.log("authentificated : " + authentificated);
    console.log("root : " + root);
    console.log("referenceId : " + referenceId);
    console.log("keywordValue : " + keywordValue);
    console.log("languageCode : " + languageCode);
    console.log("illustrationLink : " + illustrationLink);
</script>
</@layout.headbegin>

<@layout.cssprod>
<link rel="stylesheet" href="${root}/static/css/flow_layout.css?${buildtime}"/>
</@layout.cssprod>

<@layout.cssdev>
<link rel="stylesheet/less" type="text/css" href="${root}/static/css/flow_layout.less?${buildtime}"/>
</@layout.cssdev>

<@layout.jsprod>
</@layout.jsprod>

<@layout.jsdev>
</@layout.jsdev>

<@layout.js>
<script type="text/javascript" src="${root}/static/js/form.js?${buildtime}"></script>
<script type="text/javascript" src="${root}/static/js/lib/flow.js?${buildtime}"></script>
<script type="text/javascript" src="${root}/static/js/main.js?${buildtime}"></script>
</@layout.js>

<@layout.mustache>
    <#include "mustache/reference.mustache.js">
    <#include "mustache/opinion.mustache.js">
</@layout.mustache>

<@layout.headend>

</@layout.headend>

<@layout.left>
<div id="main_reference" class="box">
    <div id="counters" style="display: none;">
        <div id="counter_good" class="counter">
            <img class="smiley" src="${root}/static/images/smiley_good_white.png"/>

            <p class="counter_text">0</p>
        </div>
        <div id="counter_neutral" class="counter">
            <img class="smiley" src="${root}/static/images/smiley_neutral_white.png"/>

            <p class="counter_text">0</p>
        </div>
        <div id="counter_bad" class="counter">
            <img class="smiley" src="${root}/static/images/smiley_bad_white.png"/>

            <p class="counter_text">0</p>
        </div>
    </div>
</div>

<div id="related" class="box">
</div>

<#--<div id="header_id_panel">-->
<#--<#if user?has_content>-->
<#--<p>Hello ${user.fullname} <#if authentificated><a href="javascript:void(0);" id="logout">logout</a><#else><a href="${root}/login">login</a></#if>   </p>-->
<#--<#else>-->
<#--<p><a href="${root}/login">login</a> or <a href="${root}/signup">create account</a></p>-->
<#--</#if>-->
<#--</div>-->

<#--<div id="header_help_panel">-->
<#--<a href="${root}/help">help</a>-->
<#--</div>-->
</@layout.left>

<@layout.right>
<form id="form" method="post" action="" autocomplete="off" class="box">
<#--<p id="form_language">english</p>-->
<#--<a id="form_close" href="">close</a>-->
<#--<div id="form_block_{{id}}" class="form_block">-->
<#--<div id="form_illustration_{{id}}" class="form_illustration"></div>-->
<#--<div id="form_text_{{id}}" class="form_text" contentEditable="true"></div>-->
<#--</div>-->
    <textarea></textarea>
    <input type="button" id="form_button_good" value="good"/>
    <input type="button" id="form_button_bad" value="bad"/>
    <input type="button" id="form_button_neutral" value="neutral"/>
</form>

<ul id="opinions">
    <#--<li style="border-radius: 10px;">-->
        <#--<img src="http://test.localhost:8080/steambeat-web/static/images/smiley_bad_white.png" id="feeling" style="background-color: red; border-radius: 100px; width: 32px; position: relative; top: -30px; padding: 5px;"/>-->
        <#--<p>lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum </p>-->
        <#---->
        <#--<div style="width: 100%; margin-top: 20px;">-->
            <#--<img src="http://www.cnetfrance.fr/i/edit/2010/pr/07/iphone-4.jpg" onload="OnImageLoad(event);" style="width: 96px; height: 54px; display: inline-block; margin: 5px;"/>-->
            <#--<img src="http://www.cnetfrance.fr/i/edit/2010/pr/07/iphone-4.jpg" onload="OnImageLoad(event);" style="width: 96px; height: 54px; display: inline-block; margin: 5px;"/>-->
        <#--</div>-->
    <#--</li>-->
</ul>
</@layout.right>