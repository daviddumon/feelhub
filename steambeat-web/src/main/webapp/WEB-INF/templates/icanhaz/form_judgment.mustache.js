<script id="form_judgment" type="text/html">
{{#judgments}}
    <div class="judgment button no_select">
        <div class="feeling_smiley good rounded_top">
        </div>
        <div id="add_judgment1" class="judgment_tag good_border font_title">
            {{shortDescription}}
            <input type="hidden" name="feeling" value="good"/>
            <input type="hidden" name="subjectId" value="{{id}}"/>
            <input type="hidden" name="redirect" value="{{url}}"/>
        </div>
    </div>
{{/judgments}}
</script>
