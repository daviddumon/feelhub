<script id="reference" type="text/html">
<div class="reference">
{{#url}}
    <a href="{{url}}" class="{{classes}}" id="{{referenceId}}">
{{/url}}
{{^url}}
    <div class="{{classes}}" id="{{referenceId}}">
{{/url}}
        <img src="{{illustrationLink}}" onload="OnImageLoad(event);"/>
        <span>{{keywordValue}}</span>
{{#url}}
    </a>
{{/url}}
{{^url}}
    </div>
{{/url}}
</div>
</script>
