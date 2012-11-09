<@layout.headbegin>
<script type="text/javascript">
    var authentificated = ${userInfos.authenticated?string};
        <#if !userInfos.anonymous>
        var userLanguageCode = "${userInfos.user.languageCode}";
        </#if>

    var topicId = "${topicData.topicId}";
    var keywordValue = "${topicData.keywordValue}";
    var languageCode = "${topicData.languageCode}";
    var illustrationLink = "${topicData.illustrationLink}";

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
    <#include "mustache/topic.html">
    <#include "mustache/feeling.html">
</@layout.mustache>

<@layout.headend>

</@layout.headend>

<@layout.body>

<div id="dashboard">
    <div class="box">
        <div id="main_topic" class="box">
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

        <div id="related" class="box" style="display: none;">
            <div class="box_title">related</div>
        </div>
    </div>
</div>

<div id="feeling_form" class="box" style="display:none">
    <div class="box_title">My feeling about this</div>
    <#if userInfos.authenticated!false>
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
        <div>Want to give your feeling ? login or create account !</div>
    </#if>
</div>

<ul id="feelings">
    <span id="no-feelings">There are no feelings about ${topicData.keywordValue}!</span>
</ul>
</@layout.body>