<#macro adminLayout>
<!DOCTYPE html>
<html>
    <head>
        <title>Feelhub.com - Administration</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Bootstrap -->
        <link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </a>
                    <a class="brand" href="/admin">Feelhub.com - Administration</a>
                    <div class="nav-collapse">
                        <ul class="nav">
                            <li><a href="${root}/admin/analytic/live">Analytic</a></li>
                            <li><a href="${root}/admin/statistics">Statistics</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>
            </div>
        </div>
        <div class="container" style="margin-top:50px">
            <#nested>
        </div>
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
        <script src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/js/bootstrap.min.js"></script>
    </body>
</html>
</#macro>