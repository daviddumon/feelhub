<div id="welcome" class="popup">
    <a class="top-close-button close-button" href="javascript:void(0);">CLOSE</a>

    <div class="popup-title">
        Welcome to Feelhub ${userInfos.user.fullname} !
    </div>

    <div id="welcome-1" class="welcome-item">
        <div class="welcome-description">
            Feelhub lets you express your feelings to anything.
        </div>

        <div class="welcome-illustration">

            <div class="canvas-button">
                <canvas id="welcome-feeling-good" feeling-value="good" class="feeling-canvas"></canvas>
                <div class="canvas-welcome-text">like</div>
            </div>

            <div class="canvas-button">
                <canvas id="welcome-feeling-neutral" feeling-value="neutral" class="feeling-canvas"></canvas>
                <div class="canvas-welcome-text">don't care</div>
            </div>

            <div class="canvas-button">
                <canvas id="welcome-feeling-bad" feeling-value="bad" class="feeling-canvas"></canvas>
                <div class="canvas-welcome-text">dislike</div>
            </div>

        </div>
    </div>

    <div id="welcome-2" class="welcome-item">
        <div class="welcome-description">
            You can discover topics of interest and add your feeling to them.
        </div>

        <div class="welcome-illustration">
            <div class="wrapper">
            <#if topicDatas??>
                <img src="${topicDatas[0].thumbnail?json_string}" class="illustration"/>
                <span>${topicDatas[0].name?json_string}</span>
            </#if>
            </div>
        </div>
    </div>

    <div id="welcome-3" class="welcome-item">
        <div class="welcome-description">
            Check analytics for things you like or dislike, and react to feelings of other users.
        </div>

        <div class="welcome-illustration">
            <canvas id="help-pie" class="pie-canvas" data-good="1" data-neutral="1" data-bad="1"></canvas>
        </div>
    </div>

    <div class="popup-bottom">
        <a class="close-button" href="javascript:void(0);">OK</a>
    </div>
</div>