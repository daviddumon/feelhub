<script id="opinion" type="text/html">
    <li class="{{classes}}" id="{{id}}" style="position: absolute; display: none">
            {{#texts}}
                <p>{{.}}&nbsp;</p>
            {{/texts}}
        <div class="judgments">
            <div class="judgments_header font_title">related</div>
        {{#judgments}}
            {{> judgment}}
        {{/judgments}}
        </div>
    </li>
</script>
