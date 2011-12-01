<@header.withHeader>
<script type="text/javascript" src="${root}/static/js/newwebpage.js"></script>
</@header.withHeader>

<@layout.left>

</@layout.left>

<@layout.right>
<script type="text/javascript">
    $(function(){
    newwebpage.create("${uri}");
    });
</script>

<div id="newwebpage">
    <p>Creating this awesome subject for you !</p>
</div>
</@layout.right>