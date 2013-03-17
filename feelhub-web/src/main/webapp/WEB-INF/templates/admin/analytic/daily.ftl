<#import "../layout.ftl" as layout/>
<@layout.adminLayout>
<h1>Analytic</h1>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
    google.load("visualization", "1", {packages:["corechart"]});
    google.setOnLoadCallback(drawChart);
    function drawChart() {
        drawDailyTrends();
        drawCumulative();

        function drawDailyTrends() {
            var dailyStats = [
                ['Day', 'Activation', 'Login'],
                <#list datas as data>
                    ['${data.date?date}',  ${data.activationsPercentage}, ${data.loginsPercentage}]<#if data_has_next>,</#if>
                </#list>
            ];

            var data = google.visualization.arrayToDataTable(dailyStats);
            var options = {
                title: 'User behavior',
                hAxis: {title: 'Day', titleTextStyle: {color: 'red'}},
                vAxis: {minValue:0, maxValue:100}
            };
            var chart = new google.visualization.AreaChart(document.getElementById('daily'));
            chart.draw(data, options);
        }

        function drawCumulative() {
            var dailyStats = [
                ['Day', 'Users'],
                <#list datas as data>
                    ['${data.date?date}',  ${data.creations}]<#if data_has_next>,</#if>
                </#list>
            ];

            var data = google.visualization.arrayToDataTable(dailyStats);
            var options = {
                title: 'User per day'
            };

            var chart = new google.visualization.LineChart(document.getElementById('cumulative'));
            chart.draw(data, options);
        }
    }
</script>
<div id="daily" style="width: 900px; height: 500px;"></div>
<div id="cumulative" style="width: 900px; height: 500px;"></div>
</@>