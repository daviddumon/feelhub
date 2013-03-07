<#setting locale="en_US">
<?xml version="1.0" encoding="UTF-8"?>
<urlset xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd" xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
<#list entries as entry>
<url>
<loc>${prefix}${entry.loc}</loc>
<lastmod>${entry.lastMod}</lastmod>
<changefreq>${entry.frequency}</changefreq>
<priority>${entry.priority}</priority>
</url>
</#list>
</urlset>