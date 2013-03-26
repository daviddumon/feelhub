<#import "../layout.ftl" as layout/>
<@layout.adminLayout>
<div class="navbar">
    <div class="navbar-inner">
        <a class="brand" href="#">Analytic</a>
        <ul class="nav">
            <li class="active"><a href="/admin/analytic/live">Live</a></li>
            <li><a href="/admin/analytic/newuser">New user</a></li>
            <li><a href="/admin/analytic/dailybehavior">Daily behavior</a></li>
        </ul>
    </div>
</div>

<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
    google.load("visualization", "1", {packages:["corechart"]});
    google.setOnLoadCallback(drawChart);
    function drawChart() {

        drawLive();



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

</div>
</@>