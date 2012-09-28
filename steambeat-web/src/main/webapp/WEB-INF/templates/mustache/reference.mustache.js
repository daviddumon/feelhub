<script id="reference" type="text/html">
{{#url}}
    <a href="{{url}}" class="reference {{classes}}" id="{{referenceId}}">
{{/url}}
{{^url}}
    <div class="reference {{classes}}" id="{{referenceId}}">
{{/url}}
        <img src="{{illustrationLink}}" onload="OnImageLoad(event);"/>
        <span>{{keywordValue}}</span>
{{#url}}
    </a>
{{/url}}
{{^url}}
    </div>
{{/url}}
</script>
