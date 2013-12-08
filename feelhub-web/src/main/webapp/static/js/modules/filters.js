define(["jquery"],

    function ($) {

        function init() {
            $(".filter").each(function (index, filter) {
                var currentValueElement = $(this).find(".current-value")[0];
                var currentValue = readFilter(filter);

                if (currentValue != "") {
                    var lis = $(this).find("li");
                    if (currentValue.match(/,/g)) {
                        $(currentValue.split(",")).each(function (index, insideValue) {
                            selectLi(lis, insideValue.trim());
                        });
                    } else {
                        selectLi(lis, currentValue);
                    }
                    $(currentValueElement).html(currentValue);
                } else {
                    $(currentValueElement).html($(this).find("li").first().text());
                    $(this).find("li").first().toggleClass("selected");
                }

                $(this).show();

                function selectLi(lis, value) {
                    $(lis).each(function (index, li) {
                        if ($(this).text() == value) {
                            $(this).toggleClass("selected");
                        }
                    })
                }
            });

            $(".top-filter:not('.disabled')").on("click", function () {
                $(this).parent().children("ul").toggleClass("open");
                $("#filters-overlay").addClass("open");
            });

            $("#filters-overlay").click(function () {
                $(".filter ul").removeClass("open");
                $(this).removeClass("open");
            });

            $("#filters li.select-unique").click(function () {
                $(this).parent().children("li").removeClass("selected");
                $(this).toggleClass("selected");
                $(this).parent().removeClass("open");
                $(this).parent().parent().find(".current-value").html($(this).text());
                storeFilter($(this).parent().parent(), $(this).text());
                reset();
            });

            $("#filters li.select-multiple ").click(function () {
                $(this).parent().children("li.select-unique").removeClass("selected");
                $(this).toggleClass("selected");

                var liSelected = $(this).parent().children("li.selected");
                var currentValue = "";
                if (liSelected.length > 0) {
                    currentValue = $(liSelected[0]).text();
                    for (var i = 1; i < liSelected.length; i++) {
                        currentValue += "," + $(liSelected[i]).text();
                    }
                }
                $(this).parent().parent().find(".current-value").html(currentValue);
                storeFilter($(this).parent().parent(), currentValue);
                reset();
            });
        }

        function readFilter(filter) {
            return getFilterById(filter.id);
        }

        function getFilterById(filterName) {
            var item = localStorage.getItem(filterName);
            if (item != null) {
                return JSON.parse(item);
            }
            return "";
        }

        function storeFilter(filter, value) {
            localStorage.setItem($(filter).attr("id"), JSON.stringify(value));
        }

        function reset() {
            $("#flow").trigger("flow-reset");
        }

        return {
            init: init,
            getFilter: getFilterById
        }
    });