<#import "../layout.ftl" as layout/>
<@layout.adminLayout>
<h1>Analytic</h1>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
    google.load("visualization", "1", {packages:["corechart"]});
    google.setOnLoadCallback(drawChart);
    function drawChart() {

        drawLive();

        function drawDailyTrends() {
            var dailyStats = [
                ['Day', 'Activation', 'Login'],
                <#list datas as data>
                    ['${data.date?date}',  1, 1]<#if data_has_next>,</#if>
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

        function drawLive() {
            var dailyStats = [
                ['Day', 'New Users', 'Logins', 'Http Topics', 'Real Topics', 'Cumulated'],
                <#list datas as data>
                    ['${data.date?date}',  ${data.signups}, ${data.logins}, ${data.httpTopics}, ${data.realTopics}, ${data.topics}]<#if data_has_next>,</#if>
                </#list>
            ];

            var data = google.visualization.arrayToDataTable(dailyStats);
            var options = {
                title: 'Live stats'
            };

            var chart = new google.visualization.LineChart(document.getElementById('live'));
            chart.draw(data, options);
        }
    }
</script>
<div id="live" style="width: 900px; height: 500px;"></div>

</@>