<!DOCTYPE html>
<!-- Copyright Bytedojo SAS 2011 -->
<html lang="en">
<head>
    <title>Kikiyoo</title>
    <meta charset="utf-8"/>
    <link rel="stylesheet" href="${root}/static/css/reset.css"/>
    <link rel="stylesheet" href="${root}/static/css/newfeed.css"/>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
    <script type="text/javascript" src="${root}/static/js/kikiyoo.jquery.js"></script>
    <script type="text/javascript" src="${root}/static/js/newfeed.js"></script>
    <script type="text/javascript">
        var root = "${root}";
    </script>
</head>
<body>

<script type="text/javascript">
    $(function(){
    newfeed.create("${uri}");
    });
</script>

<div id="newfeed">
    <p>Creating this awesome feed for you !</p>
</div>

</body>
</html>