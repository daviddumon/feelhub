define(["modules/parser/uri-parser"], function (parser) {

    describe("uri parser tests", function () {

        it("can analyze text", function () {
            var text = "";

            var result = parser.analyze(text);

            expect(result).toBeDefined();
            expect(result).not.toBeNull();
        });

        it("can parse complete uri", function () {
            var text = "i like http://www.feelhub.com";

            var result = parser.analyze(text);

            expect(result[0].name).toEqual("http://www.feelhub.com");
        });

        it("can parse uncomplete uri", function () {
            var text = "i like www.feelhub.com";

            var result = parser.analyze(text);

            expect(result[0].name).toEqual("www.feelhub.com");
        });

        it("can parse differents uris in same text", function () {
            var text = "i like www.feelhub.com and arpinum.fr and also https://google.fr which is secure !";

            var result = parser.analyze(text);

            expect(result[0].name).toEqual("www.feelhub.com");
            expect(result[1].name).toEqual("arpinum.fr");
            expect(result[2].name).toEqual("https://google.fr");
        });

        it("can parse uris with different cases", function () {
            var text = "i like www.feelhub.COM and ArpiNum.fr and also hTTpS://gOOgle.Fr which is secure !";

            var result = parser.analyze(text);

            expect(result[0].name).toEqual("www.feelhub.com");
            expect(result[1].name).toEqual("arpinum.fr");
            expect(result[2].name).toEqual("https://google.fr");
        });

        it("can parse complex situations", function () {
            var text = "I like: http://www.feelhub.com!! :)";

            var result = parser.analyze(text);

            expect(result.length).toEqual(1);
            expect(result[0].name).toEqual("http://www.feelhub.com");
        });

        it("set default sentiment to none", function () {
            var text = "i like www.feelhub.com";

            var result = parser.analyze(text);

            expect(result[0].sentiment).toBe("none");
        });

        it("can parse sentiment", function () {
            var text = "i like www.feelhub.com :)";

            var result = parser.analyze(text);

            expect(result[0].sentiment).toBe("good");
        });
    });
});