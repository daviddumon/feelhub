<script id="reference" type="text/html">
{{#url}}
    <a href="{{url}}" class="reference {{classes}}">
{{/url}}
{{^url}}
    <div class="reference {{classes}}" style="width: {{width}}px; height: {{height}}px;" onresize="OnImageLoad(event);">
{{/url}}
        <img id="{{referenceId}}" src="{{illustration}}" onload="OnImageLoad(event);"/>
        <span>{{keyword}}</span>
{{#url}}
    </a>
{{/url}}
{{^url}}
    </div>
{{/url}}
</script>
