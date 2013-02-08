define(["modules/parser/sentiment-detector"], function (detector) {

    describe("sentiment dectector tests", function () {

        it("can detect happy smiley", function () {
            var token = "apple";
            var text = "i like apple :)";

            var sentiment = detector.get_sentiment(token, text);

            expect(sentiment).toBe("good");
        });

        it("can detect sad smiley", function () {
            var token = "apple";
            var text = "i do not like apple :(";

            var sentiment = detector.get_sentiment(token, text);

            expect(sentiment).toBe("bad");
        });

        it("can return sentiment none if no smileys", function () {
            var token = "apple";
            var text = "i do not like apple";

            var sentiment = detector.get_sentiment(token, text);

            expect(sentiment).toBe("none");
        });

        it("can detect smileys with radius", function () {
            var token = "oranges";
            var text = "I do not like oranges :(, but i like apple :)";

            var sentiment = detector.get_sentiment(token, text, 5);

            expect(sentiment).toBe("bad");
        });

        it("can detect plus sign", function () {
            var token = "apple";
            var text = "i like apple +";

            var sentiment = detector.get_sentiment(token, text);

            expect(sentiment).toBe("good");
        });

        it("can detect minus sign", function () {
            var token = "apple";
            var text = "i do not like apple -";

            var sentiment = detector.get_sentiment(token, text);

            expect(sentiment).toBe("bad");
        });

        it("can detect equal sign", function () {
            var token = "apple";
            var text = "i don't care about apple =";

            var sentiment = detector.get_sentiment(token, text);

            expect(sentiment).toBe("neutral");
        });

        it("can detect neutral smiley", function () {
            var token = "apple";
            var text = "i don't care about apple :|";

            var sentiment = detector.get_sentiment(token, text);

            expect(sentiment).toBe("neutral");
        });
    });
});