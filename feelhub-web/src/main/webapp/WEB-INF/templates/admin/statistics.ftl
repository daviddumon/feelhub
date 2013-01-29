<h1>Statistics</h1>
<h2>Alchemy</h2>
<table>
    <thead>
        <tr>
            <th>Month</th>
            <th>Count</th>
        </tr>
    </thead>
    <tbody>
    <#list alchemyStatistics as stat>
        <tr>
            <td>${stat.month}</td>
            <td>${stat.count}</td>
        </tr>
    </#list>
    </tbody>
</table>