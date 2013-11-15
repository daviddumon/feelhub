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
                <canvas id="welcome-feeling-happy" feeling-value="happy" class="feeling-canvas"></canvas>
                <div class="canvas-welcome-text">like</div>
            </div>

            <div class="canvas-button">
                <canvas id="welcome-feeling-bored" feeling-value="bored" class="feeling-canvas"></canvas>
                <div class="canvas-welcome-text">don't care</div>
            </div>

            <div class="canvas-button">
                <canvas id="welcome-feeling-sad" feeling-value="sad" class="feeling-canvas"></canvas>
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
            <canvas id="help-pie" class="pie-canvas" data-happy="1" data-bored="1" data-sad="1"></canvas>
        </div>
    </div>

    <div class="popup-bottom">
        <a class="close-button" href="javascript:void(0);">OK</a>
    </div>
</div>