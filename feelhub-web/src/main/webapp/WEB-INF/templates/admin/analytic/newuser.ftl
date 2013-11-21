<#import "../layout.ftl" as layout/>
<@layout.adminLayout>
<div class="navbar">
    <div class="navbar-inner">
        <a class="brand" href="#">Analytic</a>
        <ul class="nav">
            <li><a href="/admin/analytic/live">Live</a></li>
            <li class="active"><a href="/admin/analytic/newuser">New user</a></li>
            <li><a href="/admin/analytic/dailybehavior">Daily behavior</a></li>
        </ul>
    </div>
</div>

<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
    google.load("visualization", "1", {packages: ["corechart"]});
    google.setOnLoadCallback(drawChart);
    function drawChart() {

        drawDailyBehavior();
        drawDailyActivity();

        function drawDailyBehavior() {
            var dailyStats = [
                ['Day', '% Login', '% Topic created', '% Feeling posted'],
                <#list dailybehaviors as data>
                    ['${data.date?date}', ${data.percentageLogins}, ${data.percentageTopic}, ${data.percentageFeeling}]<#if data_has_next>,</#if>
                </#list>
            ];

            var data = google.visualization.arrayToDataTable(dailyStats);
            var options = {
                title: 'New user daily behavior',
                hAxis: {title: 'Day', titleTextStyle: {color: 'red'}},
                vAxis: {minValue: 0, maxValue: 100}
            };
            var chart = new google.visualization.AreaChart(document.getElementById('dailybehavior'));
            chart.draw(data, options);
        }

        function drawDailyActivity() {
            var dailyStats = [
                ['Day', 'Signups', 'Logins', 'Feelings', 'Topics'],
                <#list dailybehaviors as data>
                    ['${data.date?date}', ${data.totalSignups}, ${data.totalLogins}, ${data.totalFeelings}, ${data.totalTopics}]<#if data_has_next>,</#if>
                </#list>
            ];

            var data = google.visualization.arrayToDataTable(dailyStats);
            var options = {
                title: 'New user daily activity',
                hAxis: {title: 'Day', titleTextStyle: {color: 'red'}}
            };
            var chart = new google.visualization.LineChart(document.getElementById('dailyactivity'));
            chart.draw(data, options);
        }

    }
</script>
<div id="dailybehavior" style="width: 900px; height: 500px;"></div>
<div id="dailyactivity" style="width: 900px; height: 500px;">
</@>