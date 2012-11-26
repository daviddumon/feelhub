requirejs.config(
    {
        paths:{
            'jquery':'https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min'
        }
    }
);

require(["modules/interface"], function (interface) {
    interface.init();
});