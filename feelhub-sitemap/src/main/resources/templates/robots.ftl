User-agent: *
Disallow: /social/facebook
Disallow: /help
Disallow: /login
Disallow: /signup
Disallow: /admin
Disallow: /sessions
Disallow: /activation
Disallow: /bookmarklet
<#list indexes as index>
Sitemap: ${prefix}${index.loc}
</#list>
