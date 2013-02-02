var tests = Object.keys(window.__testacular__.files).filter(function (file) {
    return /\.test\.js$/.test(file);
});

require.config({

});

// bootstrap - require, once loaded, kick off test run
//require(['test', '/base/shim.js'], function(test, shim) {
require(tests, function() {
  window.__testacular__.start();
});