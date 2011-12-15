<@header.withHeader>
<link rel="stylesheet" href="${root}/static/css/newwebpage.css"/>
<script type="text/javascript" src="${root}/static/js/newwebpage.js"></script>
</@header.withHeader>

<@layout.panel>
<script type="text/javascript">
    $(function(){
    newwebpage.create("${uri}");
    });
</script>

<div id="newwebpage" class="good rounded">
    <img src="${root}/static/images/smiley_good_white.png" />
    <p class="font_title">Creating this awesome subject for you !</p>
</div>
</@layout.panel>