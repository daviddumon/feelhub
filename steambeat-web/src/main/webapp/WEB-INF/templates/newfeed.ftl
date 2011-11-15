<@layout.withHeader>

<script type="text/javascript" src="${root}/static/js/newfeed.js"></script>

<script type="text/javascript">
    $(function(){
        setTimeout(newfeed.create("${uri}"), 2000);
    });
</script>

<div id="newfeed">
    <p>Creating this awesome subject for you !</p>
</div>

</@layout.withHeader>