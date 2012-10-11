<@layout.headbegin>
<script type="text/javascript">
        <#if authentificated?has_content>
        var authentificated = ${authentificated?string};
        <#else >
        var authentificated = false;
        </#if>

        <#if referenceData??>
        var referenceId = "${referenceData.referenceId}";
        var keywordValue = "${referenceData.keywordValue}";
        var languageCode = "${referenceData.languageCode}";
        var illustrationLink = "${referenceData.illustrationLink}";
        <#else>
        var referenceId = "";
        var keywordValue = "";
        var languageCode = "";
        var illustrationLink = "";
        </#if>

    console.log("authentificated : " + authentificated);
    console.log("root : " + root);
    console.log("referenceId : " + referenceId);
    console.log("keywordValue : " + keywordValue);
    console.log("languageCode : " + languageCode);
    console.log("illustrationLink : " + illustrationLink);
    var flow;
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

<@layout.body>
<div id="wrapper">
    <div id="left">
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

        <div class="box">
            <#--<#if authentificated?has_content>-->
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
                    <#--<#else>-->
                        <#--<div id="login_help">-->
                            <#--<#if user?has_content>-->
                                <#--<p>Hello ${user.fullname} <#if authentificated><a href="javascript:void(0);" id="logout">logout</a><#else><a href="${root}/login">login</a></#if>   </p>-->
                            <#--<#else>-->
                                <#--<p><a href="${root}/login">login</a> or <a href="${root}/signup">create account</a></p>-->
                            <#--</#if>-->
                        <#--</div>-->
                    <#--</#if>-->
        </div>

        <div id="related" class="box" style="display: none;">
            <div class="box_title">most related</div>
        </div>

    </div>

    <div id="right">


        <ul id="opinions">
        </ul>
    </div>
</div>
</@layout.body>