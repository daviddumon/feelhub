var topicData = {"id": "myid"};
var root = "root";

define(["modules/parser/parser"], function (parser) {

    describe("parser tests", function () {

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

        it("lowercase real tokens", function () {
            var text = "I like #OraNge";

            var result = parser.analyze(text);

            expect(result[0].name).toEqual("orange");
        });

        it("can stack exact same tokens", function () {
            var text = "I like #Orange and google.Fr and http://www.google.fr and #orANGe";

            var result = parser.analyze(text);

            expect(result.length).toEqual(3);
            expect(result[0].name).toEqual("google.fr");
            expect(result[1].name).toEqual("http://www.google.fr");
            expect(result[2].name).toEqual("orange");
        });

        it("can handle punctuation near real tokens", function () {
            var text = "I like #oranges! But not, you know ,#apples";

            var result = parser.analyze(text);

            expect(result[0].name).toEqual("oranges");
            expect(result[1].name).toEqual("apples");
        });

        it("can parse complex situations", function () {
            var text = "I like:#apPles,#oranges and http://www.feelhub.com!! :)";

            var result = parser.analyze(text);

            expect(result.length).toEqual(3);
            expect(result[0].name).toEqual("http://www.feelhub.com");
            expect(result[1].name).toEqual("apples");
            expect(result[2].name).toEqual("oranges");
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

        it("can parse token in context", function () {
            var text = "i'm in the context yeah";
            parser.set_context([
                {"value": "yeah"}
            ]);

            var result = parser.analyze(text);

            expect(result[0].name).toEqual("yeah");
        });

        it("can parse complex context's token", function () {
            var text = "i'm in the context Yeah!";
            parser.set_context([
                {"value": "yeah"}
            ]);

            var result = parser.analyze(text);

            expect(result[0].name).toEqual("yeah");
        });

        it("can bind context tag to topic's id", function () {
            var text = "i'm in the context Yeah!";
            parser.set_context([
                {"value": "yeah", "id": "myid"}
            ]);

            var result = parser.analyze(text);

            expect(result[0].id).toEqual("myid");
        });

        it("has thumbnail", function () {
            var text = "i'm in the context yeah";
            parser.set_context([
                {"value": "yeah", "thumbnail": "thumb"}
            ]);

            var result = parser.analyze(text);

            expect(result[0].thumbnail).toEqual("thumb");
        });

        it("can parse sentiments", function () {
            var text = "i like www.feelhub.com :)";

            var result = parser.analyze(text);

            expect(result[0].name).toEqual("www.feelhub.com");
            expect(result[0].sentiment).toEqual("happy");
        });
    });
});