<h1>Statistics</h1>
<#list statistics as statisticByApi>
<@statistic statisticByApi />
</#list>

<#macro statistic statisticByApi>
<h2>${statisticByApi.api}</h2>
<table>
    <thead>
        <tr>
            <th>Month</th>
            <th>Count</th>
        </tr>
    </thead>
    <tbody>
        <#list statisticByApi.statistics as stat>
        <tr>
            <td>${stat.month}</td>
            <td>${stat.count}</td>
        </tr>
        </#list>
    </tbody>
</table>
</#macro>