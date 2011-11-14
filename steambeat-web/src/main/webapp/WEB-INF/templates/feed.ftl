<@layout.withHeader>

<script type="text/javascript" src="${root}/static/js/feed.js"></script>

<div id="resource">
    <span id="feedRoot"><a href="#" onclick="javascript:window.open('${feed.uri}');">${feed.uri}</a></span>
</div>

<form action="${root}/feeds/${feed.uri}/opinions" method="post" id="post_opinion" autocomplete="off">
    <div id="opiniontype"></div>
    <textarea id="newopinion" name="value" autofocus="on"></textarea>
    <!--<input id="opinion_submit" type="submit"/>-->
    <input id="submit_good" type="submit" value="good"/>
    <input id="submit_bad" type="submit" value="bad"/>
    <input id="submit_neutral" type="submit" value="neutral"/>
</form>

<ul id="feed"></ul>

<script type="text/javascript">
    runTimeLine("${root}/feeds/${feed.uri}/stats:");
</script>

<footer>
    <span class="copyright">&copy; steambeat.com 2011</span>
    <span class="contact"><a href="mailto:contact@steambeat.com">contact</a></span>
    <br>
    <span class="bytedojo">brought to you by
    <a href="http://www.bytedojo.com" target="_blank"><img src="${root}/static/images/bytedojo_logo.png" alt="logo bytedojo" id="bytedojologo"/></a> and
    <a href="http://www.arpinum.fr" target="_blank"><img src="${root}/static/images/arpinum.png" alt="logo arpinum" id="arpinumlogo"/></a></span>
    <br><br><br>
    <span class="alchemy">
        <img src="${root}/static/images/alchemyAPI.jpg" alt="AlchemyAPI - Transforming Text Into Knowledge" id="alchemylogo"/>
        steambeat is using Alchemy API
    </span>
</footer>

<script type="text/javascript">
    var flow = new Flow("main.css","feed","li",".opinion");
</script>

<#list page.opinions as opinion>
    <script type="text/javascript">
        var id = flow.drawBox("${opinion.text!}", "opinion " + "opinion_" + "${opinion.feeling!}");
        flow.compute(id);
    </script>
</#list>

</body>
</html>

</@layout.withHeader>