<#import "../layout.ftl" as layout/>
<@layout.adminLayout>
<div class="navbar">
    <div class="navbar-inner">
        <a class="brand" href="#">Analytic</a>
        <ul class="nav">
            <li><a href="/admin/analytic/live">Live</a></li>
            <li><a href="/admin/analytic/newuser">New user</a></li>
            <li class="active"><a href="/admin/analytic/dailybehavior">Daily behavior</a></li>
        </ul>
    </div>
</div>

<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
    google.load("visualization", "1", {packages:["corechart"]});
    google.setOnLoadCallback(drawChart);
    function drawChart() {

        drawDailyActivity();



        function drawDailyActivity() {
            var dailyStats = [
                ['Day', 'Actives', 'Posted topic', 'Posted feeling'],
                <#list dailybehaviors as data>
                    ['${data.date?date}', ${data.totalActives}, ${data.postTopic}, ${data.postFeeling}]<#if data_has_next>,</#if>
                </#list>
            ];

            var data = google.visualization.arrayToDataTable(dailyStats);
            var options = {
                title: 'Active user daily activity',
                hAxis: {title: 'Day', titleTextStyle: {color: 'red'}}
            };
            var chart = new google.visualization.LineChart(document.getElementById('dailyactivity'));
            chart.draw(data, options);
        }

    }
</script>

<div id="dailyactivity" style="width: 900px; height: 500px;">
</@>