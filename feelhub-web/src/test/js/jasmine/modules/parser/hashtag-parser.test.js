define(["modules/parser/hashtag-parser"], function (parser) {

    describe("hashtag parser tests", function () {

        it("can analyze text", function () {
            var text = "";

            var result = parser.analyze(text);

            expect(result).toBeDefined();
            expect(result).not.toBeNull();
        });

        it("can detect hashtag", function () {
            var text = "I like #orange and #apples";

            var result = parser.analyze(text);

            expect(result[0].name).toEqual("orange");
            expect(result[1].name).toEqual("apples");
        });

        it("lowercase hashtab", function () {
            var text = "I like #OraNge";

            var result = parser.analyze(text);

            expect(result[0].name).toEqual("orange");
        });

        it("can handle punctuation near hashtags", function () {
            var text = "I like #oranges! But not, you know ,#apples";

            var result = parser.analyze(text);

            expect(result[0].name).toEqual("oranges");
            expect(result[1].name).toEqual("apples");
        });

        it("can parse complex situations", function () {
            var text = "I like:#apPles,#oranges !! :)";

            var result = parser.analyze(text);

            expect(result.length).toEqual(2);
            expect(result[0].name).toEqual("apples");
            expect(result[1].name).toEqual("oranges");
        });

        it("can parse accents", function () {
            var text = "i like #éléphants";

            var result = parser.analyze(text);

            expect(result[0].name).toEqual("éléphants");
        });

        it("can use apostrophe", function () {
            var text = "j'aime #l'agilité!"

            var result = parser.analyze(text);

            expect(result[0].name).toEqual("agilité");
        });

        it("set default sentiment to none", function () {
            var text = "i like #marie";

            var result = parser.analyze(text);

            expect(result[0].sentiment).toBe("none");
        });

        it("set parse happy sentiment", function () {
            var text = "i like #marie :)";

            var result = parser.analyze(text);

            expect(result[0].sentiment).toBe("happy");
        });
    });
});