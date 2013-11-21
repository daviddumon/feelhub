<#import "../layout.ftl" as layout/>
<@layout.adminLayout>
<div class="navbar">
    <div class="navbar-inner">
        <a class="brand" href="#">Analytic</a>
        <ul class="nav">
            <li><a href="/admin/analytic/live">Live</a></li>
            <li class="active"><a href="/admin/analytic/newuser">New user behavior</a></li>
            <li><a href="/admin/analytic/dailybehavior">Active user behavior</a></li>
        </ul>
    </div>
</div>

<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
    google.load("visualization", "1", {packages: ["corechart"]});
    google.setOnLoadCallback(drawChart);
    function drawChart() {

        drawDailyBehavior1();
        drawDailyBehavior2();
        drawDailyBehavior3();
        drawDailyActivity1();
        drawDailyActivity2();
        drawDailyActivity3();
        drawDailyActivity4();

        function drawDailyBehavior1() {
            var dailyStats = [
                ['Day', '% Login'],
                <#list dailybehaviors as data>
                    ['${data.date?date}', ${data.percentageLogins}]<#if data_has_next>,</#if>
                </#list>
            ];
            console.log(dailyStats);
            var data = google.visualization.arrayToDataTable(dailyStats);
            var options = {
                title: 'New user daily behavior',
                hAxis: {title: 'Day', titleTextStyle: {color: 'red'}},
                vAxis: {minValue: 0, maxValue: 100}
            };
            var chart = new google.visualization.AreaChart(document.getElementById('dailybehavior1'));
            chart.draw(data, options);
        }

        function drawDailyBehavior2() {
            var dailyStats = [
                ['Day', '% Topic created'],
                <#list dailybehaviors as data>
                    ['${data.date?date}', ${data.percentageTopic}]<#if data_has_next>,</#if>
                </#list>
            ];

            var data = google.visualization.arrayToDataTable(dailyStats);
            var options = {
                title: 'New user daily behavior',
                hAxis: {title: 'Day', titleTextStyle: {color: 'red'}},
                vAxis: {minValue: 0, maxValue: 100}
            };
            var chart = new google.visualization.AreaChart(document.getElementById('dailybehavior2'));
            chart.draw(data, options);
        }

        function drawDailyBehavior3() {
            var dailyStats = [
                ['Day', '% Feeling posted'],
                <#list dailybehaviors as data>
                    ['${data.date?date}', ${data.percentageFeeling}]<#if data_has_next>,</#if>
                </#list>
            ];

            var data = google.visualization.arrayToDataTable(dailyStats);
            var options = {
                title: 'New user daily behavior',
                hAxis: {title: 'Day', titleTextStyle: {color: 'red'}},
                vAxis: {minValue: 0, maxValue: 100}
            };
            var chart = new google.visualization.AreaChart(document.getElementById('dailybehavior3'));
            chart.draw(data, options);
        }

        function drawDailyActivity1() {
            var dailyStats = [
                ['Day', 'Signups'],
                <#list dailybehaviors as data>
                    ['${data.date?date}', ${data.totalSignups}]<#if data_has_next>,</#if>
                </#list>
            ];

            var data = google.visualization.arrayToDataTable(dailyStats);
            var options = {
                title: 'New user daily activity',
                hAxis: {title: 'Day', titleTextStyle: {color: 'red'}}
            };
            var chart = new google.visualization.LineChart(document.getElementById('dailyactivity1'));
            chart.draw(data, options);
        }

        function drawDailyActivity2() {
            var dailyStats = [
                ['Day', 'Logins'],
                <#list dailybehaviors as data>
                    ['${data.date?date}', ${data.totalLogins}]<#if data_has_next>,</#if>
                </#list>
            ];

            var data = google.visualization.arrayToDataTable(dailyStats);
            var options = {
                title: 'New user daily activity',
                hAxis: {title: 'Day', titleTextStyle: {color: 'red'}}
            };
            var chart = new google.visualization.LineChart(document.getElementById('dailyactivity2'));
            chart.draw(data, options);
        }

        function drawDailyActivity3() {
            var dailyStats = [
                ['Day', 'Feelings'],
                <#list dailybehaviors as data>
                    ['${data.date?date}', ${data.totalFeelings}]<#if data_has_next>,</#if>
                </#list>
            ];

            var data = google.visualization.arrayToDataTable(dailyStats);
            var options = {
                title: 'New user daily activity',
                hAxis: {title: 'Day', titleTextStyle: {color: 'red'}}
            };
            var chart = new google.visualization.LineChart(document.getElementById('dailyactivity3'));
            chart.draw(data, options);
        }

        function drawDailyActivity4() {
            var dailyStats = [
                ['Day', 'Topics'],
                <#list dailybehaviors as data>
                    ['${data.date?date}', ${data.totalTopics}]<#if data_has_next>,</#if>
                </#list>
            ];

            var data = google.visualization.arrayToDataTable(dailyStats);
            var options = {
                title: 'New user daily activity',
                hAxis: {title: 'Day', titleTextStyle: {color: 'red'}}
            };
            var chart = new google.visualization.LineChart(document.getElementById('dailyactivity4'));
            chart.draw(data, options);
        }

    }
</script>
<div id="dailybehavior1" style="width: 900px; height: 500px;"></div>
<div id="dailybehavior2" style="width: 900px; height: 500px;"></div>
<div id="dailybehavior3" style="width: 900px; height: 500px;"></div>
<div id="dailyactivity1" style="width: 900px; height: 500px;"></div>
<div id="dailyactivity2" style="width: 900px; height: 500px;"></div>
<div id="dailyactivity3" style="width: 900px; height: 500px;"></div>
<div id="dailyactivity4" style="width: 900px; height: 500px;"></div>
</@>