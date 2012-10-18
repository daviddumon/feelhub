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

        <#if user??>
        var userLanguageCode = "${user.languageCode}";
        //console.log("userLanguageCode : " + userLanguageCode);
        </#if>

    //console.log("authentificated : " + authentificated);
    //console.log("root : " + root);
    //console.log("referenceId : " + referenceId);
    //console.log("keywordValue : " + keywordValue);
    //console.log("languageCode : " + languageCode);
    //console.log("illustrationLink : " + illustrationLink);
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
<script type="text/javascript" src="${root}/static/js/flow.js?${buildtime}"></script>
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

        <div id="opinion_form" class="box" style="display:none">
            <div class="box_title">My feeling about this</div>
            <#if authentificated!false>
                <form id="form" method="post" action="" autocomplete="off" class="box">
                    <textarea></textarea>
                    <button type="submit" id="form_button_good" name="good">
                        <img src="${root}/static/images/smiley_good_white.png"/>
                    </button>
                    <button type="submit" id="form_button_neutral" name="neutral">
                        <img src="${root}/static/images/smiley_neutral_white.png"/>
                    </button>
                    <button type="submit" id="form_button_bad" name="bad">
                        <img src="${root}/static/images/smiley_bad_white.png"/>
                    </button>
                </form>
            <#else>
                <div>Want to give your opinion ? login or create account !</div>
            </#if>
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