<#import "../layout.ftl" as layout/>
<@layout.adminLayout>
<div class="navbar">
    <div class="navbar-inner">
        <a class="brand" href="#">Analytic</a>
        <ul class="nav">
            <li><a href="/admin/analytic/live">Live</a></li>
            <li><a href="/admin/analytic/newuser">New user behavior</a></li>
            <li class="active"><a href="/admin/analytic/dailybehavior">Active user behavior</a></li>
        </ul>
    </div>
</div>

<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
    google.load("visualization", "1", {packages: ["corechart"]});
    google.setOnLoadCallback(drawChart);
    function drawChart() {

        drawDailyActivity1();
        drawDailyActivity2();
        drawDailyActivity3();

        function drawDailyActivity1() {
            var dailyStats = [
                ['Day', 'Active users'],
                <#list dailybehaviors as data>
                    ['${data.date?date}', ${data.totalActives}]<#if data_has_next>,</#if>
                </#list>
            ];

            var data = google.visualization.arrayToDataTable(dailyStats);
            var options = {
                title: 'Active user daily activity',
                hAxis: {title: 'Day', titleTextStyle: {color: 'red'}}
            };
            var chart = new google.visualization.LineChart(document.getElementById('dailyactivity1'));
            chart.draw(data, options);
        }

        function drawDailyActivity2() {
            var dailyStats = [
                ['Day', 'Posted topics'],
                <#list dailybehaviors as data>
                    ['${data.date?date}', ${data.postTopic}]<#if data_has_next>,</#if>
                </#list>
            ];

            var data = google.visualization.arrayToDataTable(dailyStats);
            var options = {
                title: 'Active user daily activity',
                hAxis: {title: 'Day', titleTextStyle: {color: 'red'}}
            };
            var chart = new google.visualization.LineChart(document.getElementById('dailyactivity2'));
            chart.draw(data, options);
        }

        function drawDailyActivity3() {
            var dailyStats = [
                ['Day', 'Posted feelings'],
                <#list dailybehaviors as data>
                    ['${data.date?date}', ${data.postFeeling}]<#if data_has_next>,</#if>
                </#list>
            ];

            var data = google.visualization.arrayToDataTable(dailyStats);
            var options = {
                title: 'Active user daily activity',
                hAxis: {title: 'Day', titleTextStyle: {color: 'red'}}
            };
            var chart = new google.visualization.LineChart(document.getElementById('dailyactivity3'));
            chart.draw(data, options);
        }

    }
</script>

<div id="dailyactivity1" style="width: 900px; height: 500px;"></div>
<div id="dailyactivity2" style="width: 900px; height: 500px;"></div>
<div id="dailyactivity3" style="width: 900px; height: 500px;"></div>
</@>