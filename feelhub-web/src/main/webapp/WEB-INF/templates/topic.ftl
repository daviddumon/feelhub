<@flow.js>
<script type="text/javascript">
    var topicId = "${keywordData.topicId}";
    var keywordValue = "${keywordData.keywordValue}";
    var languageCode = "${keywordData.languageCode}";
    var illustrationLink = "${keywordData.illustrationLink}";
    var typeValue = "${keywordData.typeValue}";

    var flow;
</script>
<script type="text/javascript" src="${root}/static/js/form.js?${buildtime}"></script>
<script type="text/javascript" src="${root}/static/js/flow.js?${buildtime}"></script>
<script type="text/javascript" src="${root}/static/js/main.js?${buildtime}"></script>
</@flow.js>

<@flow.dashboard>
<div class="box">
    <div id="main_keyword" class="box">
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
        <div class="box_title">related</div>
    </div>
</div>
</@flow.dashboard>

<@flow.command>
<div id="feeling_form" class="box" style="display:none">
    <div class="box_title">My feeling about this</div>
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
</div>
</@flow.command>