<@layout.withHeader>

<script type="text/javascript" src="${root}/static/js/newfeed.js"></script>

<script type="text/javascript">
    $(function(){
        newfeed.create("${uri}");
    });
</script>

<div id="newfeed">
    <p>Creating this awesome subject for you !</p>
</div>

</@layout.withHeader>