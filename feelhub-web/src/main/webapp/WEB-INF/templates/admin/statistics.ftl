<h1>Statistics</h1>
<h2>Alchemy</h2>
<@tableau alchemyStatistics />
<h2>Bing Search</h2>
<@tableau bingSearchStatistics />

<#macro tableau collection>
<table>
    <thead>
    <tr>
        <th>Month</th>
        <th>Count</th>
    </tr>
    </thead>
    <tbody>
        <#list collection as stat>
        <tr>
            <td>${stat.month}</td>
            <td>${stat.count}</td>
        </tr>
        </#list>
    </tbody>
</table>
</#macro>