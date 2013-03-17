<#import "layout.ftl" as layout/>
<@layout.adminLayout>
<h1>Statistics</h1>
<h2>Current</h2>
<table class="current">
    <tbody>
    <#list statistics as statisticByApi>
        <#if statisticByApi.statistics?size != 0>
            <#assign currentStat=statisticByApi.statistics[0] />
            <#if currentStat.month == .now?string("MMyyyy")>
            <tr>
                <td>${statisticByApi.api}</td>
                <td>${currentStat.count}</td>
            </tr>
            </#if>
        </#if>
    </#list>
    </tbody>
</table>

<#list statistics as statisticByApi>
<@statistic statisticByApi />
</#list>
</@layout.adminLayout>

<#macro statistic statisticByApi>
<h2>${statisticByApi.api}</h2>
<table>
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