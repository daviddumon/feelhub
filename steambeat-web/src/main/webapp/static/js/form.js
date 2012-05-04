/* Copyright bytedojo 2011 */
$(function () {
    if (typeof subjectId !== 'undefined') {
        $("#form_button").show();
        $.getJSON(root + "/related?&fromId=" + subjectId + "&limit=6", function (data) {
            var related_data = {"judgments":[]};

            $.each(data, function (index, subject) {
                var judgment_data = {
                    id:subject.id,
                    shortDescription:subject.shortDescription,
                    description:subject.description,
                    url:subject.url
                };
                related_data.judgments.push(judgment_data);
            });

            var subjects = ich.form_judgment(related_data);
            $("#opinion_form_subject").append(subjects);

            $("#opinion_form .judgment").click(function () {
                $(this).children(".judgment_tag").toggleClass("good_border");
                $(this).children(".judgment_tag").toggleClass("bad_border");
                $(this).children(".feeling_smiley").toggleClass("good");
                $(this).children(".feeling_smiley").toggleClass("bad");
                if ($(this).children(".judgment_tag").hasClass("bad_border")) {
                    $(this).find(".judgment_tag > [name='feeling']").attr("value", "bad");
                } else {
                    $(this).find(".judgment_tag > [name='feeling']").attr("value", "good");
                }
            });

            $("#opinion_form_submit").click(function (event) {
                event.preventDefault();
                event.stopImmediatePropagation();
                var form = $("#opinion_form").serializeArray();
                $.post(root + "/opinions", form, function (data, textStatus, jqXHR) {
                    location.href = jqXHR.getResponseHeader("Location");
                });
                return false;
            });
        });
    }
});

function openOpinionForm() {
    $("#blanket").css("height", $(window).height());
    $("#blanket").show();
    $("#opinion_form").css("top", 50);
    $("#opinion_form").css("left", ($(window).width() - $("#opinion_form").width()) / 2);
    $("#opinion_form").css("height", $(window).height() - 100);
    $("#opinion_form").show();
}

function closeOpinionForm() {
    $("#opinion_form").hide();
    $("#blanket").hide();
}