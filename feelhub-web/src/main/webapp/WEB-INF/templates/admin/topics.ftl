<#import "layout.ftl" as layout/>
<@layout.adminLayout>
<h1>Topics</h1>
<button type="button" class="btn pull-right" id="update-all-thumbnails">Update all (one by one)</button>
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
            <td class="actions">
                <button type="submit" class="btn update-thumbnail" data-topicid="${topic.id}">Update thumbnail</button>
            </td>
        </tr>
    </#list>
    </tbody>
</table>
<script type="text/javascript">
    $(function() {
        $(".update-thumbnail").click(function(e) {
            updateThumbnail($(e.target));
            return false;
        });

        $("#update-all-thumbnails").click(function(e) {
            updateAllThumbnails(0);
            return false;
        });
    });

    function updateThumbnail(button) {
        update(button, function() {
                onSuccess(button);
            },
            function() {
                onError(button);
            }
        );
    }

    function update(button, callbackOk, callbackErreur) {
        button.attr("disabled", "disabled");
        $.ajax({
            url: "${root}/admin/topics/" + button.attr("data-topicid") + "/thumbnail",
            type: 'PUT',
            success: function() {
                callbackOk();
            },
            error: function() {
                callbackErreur();
            }
        });
    }

    function updateAllThumbnails(index) {
        var button = $(".actions:eq(" + index + ")").find("button");
        if (button.length > 0) {
            update(button, function() {
                onSuccess(button);
                updateAllThumbnails(index + 1);
            }, function() {
                onError(button);
                updateAllThumbnails(index + 1);
            });
        }
    }

    function onSuccess(button) {
        removeButtonWithText(button, "Wait few seconds and reload the page");
    }

    function onError(button) {
        removeButtonWithText(button, "Error");
    }

    function removeButtonWithText(button, text) {
        var td = button.closest("td");
        button.remove();
        td.text(text);
    }

</script>
</@layout.adminLayout>