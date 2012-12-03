<@flow.js>
<script type="text/javascript" data-main="${root}/static/js/controller/topic-controller" src="${root}/static/js/require.js?${buildtime}"></script>
</@flow.js>

<@flow.dashboard>
</@flow.dashboard>

<@flow.command>
<form id="feeling_form" autocomplete="off">
    <div class="box_title">My feeling about this</div>
    <select name="language">
        <option value="fr" selected="selected">fr</option>
    </select>
    <textarea name="text"></textarea>
    <input type="hidden" name="topicId" value="${topicData.id}"/>
    <button type="submit">
        share
    </button>
</form>
</@flow.command>