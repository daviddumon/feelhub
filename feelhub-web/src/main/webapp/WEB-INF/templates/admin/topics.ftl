<#import "layout.ftl" as layout/>
<@layout.adminLayout>
<h1>Topics</h1>
<h2>Without thumbnail (${topics?size})</h2>
<table class="table table-striped">
    <thead>
        <tr>
            <th>Name</th>
            <th>Type</th>
            <th>Creation date</th>
            <th>Url</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
    <#list topics as topic>
        <tr>
            <td>${topic.name}</td>
            <td>${topic.type}</td>
            <td>${topic.creationDate?number_to_date}</td>
            <td><a href="${root}/topic/${topic.id}">${root}/topic/${topic.id}</a></td>
            <td>
                <button type="submit" class="btn try-thumbnail" data-topicid="${topic.id}">Try to find a thumbnail</button>
            </td>
        </tr>
    </#list>
    </tbody>
</table>
<script type="text/javascript">
    $(function() {
        $(".try-thumbnail").click(function(e) {
            var button = $(e.target);
            button.attr("disabled", "disabled");
            $.ajax({
                url: "${root}/admin/topics/" + button.attr("data-topicid") + "/thumbnail",
                type: 'PUT',
                success: function() {
                    button.closest("tr").remove();
                },
                error: function() {
                    button.removeAttr("disabled");
                }
            });
            return false;
        });
    });
</script>
</@layout.adminLayout>