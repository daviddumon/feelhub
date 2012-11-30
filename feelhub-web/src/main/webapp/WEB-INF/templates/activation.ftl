<@noflow.js>
<script type="text/javascript" data-main="${root}/static/js/controller/common-controller" src="${root}/static/js/require.js?${buildtime}"></script>
<script type="text/javascript">
    $(function () {
        setTimeout(function () {
            window.location.href = root + "/login";
        }, 2000);
    })
</script>
</@noflow.js>

<@noflow.body>
<div id="thankyou">
    <p>Thank you !</p>
</div>
</@noflow.body>
