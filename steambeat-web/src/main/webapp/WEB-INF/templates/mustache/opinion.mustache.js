<script id="opinion" type="text/html">
    <li class="{{classes}}" id="{{id}}">
        <div class="judgments_top">
            {{#judgments}}
                {{> judgment}}
            {{/judgments}}
            </div>
            {{#texts}}
        <p>{{.}}&nbsp;</p>
            {{/texts}}
        <div class="judgments">
            <div class="judgments_header font_title">related</div>
        </div>
    </li>
</script>
