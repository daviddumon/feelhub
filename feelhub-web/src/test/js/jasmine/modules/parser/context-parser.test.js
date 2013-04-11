define(["modules/parser/context-parser"], function (parser) {

    describe("context parser tests", function () {

        it("can analyze text", function () {
            var text = "";

            var result = parser.analyze(text, []);

            expect(result).toBeDefined();
            expect(result).not.toBeNull();
        });

        it("can parse token in context", function () {
            var text = "i'm in the context yeah";

            var result = parser.analyze(text, [
                {"value": "yeah"}
            ]);

            expect(result[0].name).toEqual("yeah");
        });

        it("can parse complex context's token", function () {
            var text = "i'm in the context Yeah!";

            var result = parser.analyze(text, [
                {"value": "yeah"}
            ]);

            expect(result[0].name).toEqual("yeah");
        });

        it("can bind context tag to topic's id", function () {
            var text = "i'm in the context Yeah!";

            var result = parser.analyze(text, [
                {"value": "yeah", "id": "myid"}
            ]);

            expect(result[0].id).toEqual("myid");
        });

        it("can match loosely context token", function () {
            var text = "i like pascal!";

            var result = parser.analyze(text, [
                {"value": "pascal lamy", "id": "myid"}
            ]);

            expect(result[0].name).toEqual("pascal lamy");
        });

        it("can match multiple context tokens", function () {
            var text = "i like pascal!";
            var context = [
                {"value": "pascal lamy", "id": "myid"},
                {"value": "pascal georges", "id": "myid"},
            ];

            var result = parser.analyze(text, context);

            expect(result[0].name).toEqual("pascal lamy");
            expect(result[1].name).toEqual("pascal georges");
        });

        it("do not match small words", function () {
            var text = "a spring";
            var context = [
                {"value": "a thing", "id": "myid"},
                {"value": "spring", "id": "myid"},
            ];

            var result = parser.analyze(text, context);

            expect(result.length).toEqual(1);
            expect(result[0].name).toEqual("spring");
        });

        it("matches only full words", function () {
            var text = "i like pas and pascal!";

            var result = parser.analyze(text, [
                {"value": "pascal lamy", "id": "myid"}
            ]);

            expect(result.length).toEqual(1);
            expect(result[0].name).toEqual("pascal lamy");
        });

        it("has thumbnail", function () {
            var text = "i'm in the context yeah";
            var context = [
                {"value": "yeah", "thumbnail": "thumb"}
            ];

            var result = parser.analyze(text, context);

            expect(result[0].thumbnail).toEqual("thumb");
        });

        it("has good sentiment", function () {
            var text = "i'm in the context yeah :)";
            var context = [
                {"value": "yeah", "thumbnail": "thumb"}
            ];

            var result = parser.analyze(text, context);

            expect(result[0].sentiment).toEqual("good");
        });
    });
});