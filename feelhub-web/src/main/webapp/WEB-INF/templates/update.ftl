<@base.head_production>
<link rel="stylesheet" href="${root}/static/css/fixed.css?cache=${buildtime}"/>
<script type="text/javascript" data-main="${root}/static/js/controller-built/fixed-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@base.head_production>

<@base.head_development>
<link rel="stylesheet/less" type="text/css" href="${root}/static/css/fixed.less?cache=${buildtime}"/>
<script type="text/javascript" data-main="${root}/static/js/controller/fixed-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@base.head_development>

<@base.js>
</@base.js>

<@base.body>
<div id="update" class="fixed-panel">
    <p>We are updating Feelhub !</p>

    <p>We apologize for the inconvenience</p>
</div>
</@base.body>