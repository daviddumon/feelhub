<script id="opinion" type="text/html">
    <li class="{{opinion_classes}}" id="{{id}}">
        {{#text}}
        <p>{{{.}}}&nbsp;</p>
        {{/text}}
        <div class="related">
            {{#referenceDatas}}
                {{> reference}}
            {{/referenceDatas}}
        </div>
    </li>
</script>
