<@base.head_begin>
</@base.head_begin>

<@base.head_production>
<link rel="stylesheet" href="${root}/static/css/fixed.css?cache=${buildtime}"/>
<script type="text/javascript" data-main="${root}/static/js/controller-built/fixed-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@base.head_production>

<@base.head_development>
<link rel="stylesheet/less" type="text/css" href="${root}/static/css/fixed.less?cache=${buildtime}"/>
<script type="text/javascript" data-main="${root}/static/js/controller/fixed-controller" src="${root}/static/js/require.js?cache=${buildtime}"></script>
</@base.head_development>

<@base.head_end>
</@base.head_end>

<@base.body>
<div id="error" class="fixed-panel">
    <a id="home_link" href="${root}">Feelhub<span>.com</span></a>

    <p>There was a great disturbance in the Force</p>
</div>
</@base.body>