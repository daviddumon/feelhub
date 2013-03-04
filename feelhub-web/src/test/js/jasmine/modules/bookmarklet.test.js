var uri = "uri";

define(["jquery", "modules/bookmarklet"],

    function ($, bookmarklet) {

        var callback_data = [
            {
                "id": "good_id"
            }
        ];

        describe("bookmarklet tests", function () {

            it("searches for existing topics", function () {
                spyOn($, "ajax");

                bookmarklet.search_topic_for();

                expect($.ajax).toHaveBeenCalled();
            });

            it("calls the good api end point", function () {
                var ajax_data;
                spyOn($, "ajax").andCallFake(function (data) {
                    ajax_data = data;
                });

                bookmarklet.search_topic_for();

                expect(ajax_data.url).toEqual(root + "/api/topics?q=" + uri);
            });

            it("calls ajax with good type", function () {
                var ajax_data;
                spyOn($, "ajax").andCallFake(function (data) {
                    ajax_data = data;
                });

                bookmarklet.search_topic_for();

                expect(ajax_data.type).toEqual("GET");
            });

            it("calls handle on succes", function () {
                var ajax_data;
                spyOn($, "ajax").andCallFake(function (data) {
                    ajax_data = data;
                });

                bookmarklet.search_topic_for();

                expect(ajax_data.success.name).toEqual("handle");
            });

            it("calls create if empty data", function () {
                var ajax_data;
                spyOn($, "ajax").andCallFake(function (data) {
                    ajax_data = data;
                });

                bookmarklet.handle([]);

                expect($.ajax).toHaveBeenCalled();
                expect(ajax_data.url).toEqual(root + "/api/topics");
                expect(ajax_data.type).toEqual("POST");
                expect(ajax_data.data).toEqual({"name": uri});
            });
        });
    });