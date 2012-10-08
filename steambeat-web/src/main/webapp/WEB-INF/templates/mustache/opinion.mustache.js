<script id="opinion" type="text/html">
    <li class="{{opinion_classes}}" id="{{id}}">
        {{#text}}
        <p>{{{.}}}&nbsp;</p>
        {{/text}}
        {{#referenceDatas}}
         <div class="related">
            {{> reference}}
        </div>
        {{/referenceDatas}}
    </li>
</script>
