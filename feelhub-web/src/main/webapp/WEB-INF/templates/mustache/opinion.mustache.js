<script id="opinion" type="text/html">
    <li class="{{opinion_classes}}" id="{{id}}">
    {{#opinion_feeling}}
        <img src="{{opinion_feeling_illustration}}" class="img_{{opinion_feeling}} opinion_feeling_illustration"/>
    {{/opinion_feeling}}
    {{^opinion_feeling}}
        <div class="opinion_spacer"></div>
    {{/opinion_feeling}}
        {{#text}}
        <p>{{{.}}}&nbsp;</p>
        {{/text}}
        <div class="opinion_related" style="height: {{height}}">
        {{#referenceDatas}}
        {{> reference}}
        {{/referenceDatas}}
        </div>
    </li>
</script>
