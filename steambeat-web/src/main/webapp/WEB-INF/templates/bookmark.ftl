(function () {
var body = document.body;
try {
if (!body) {
throw(0);
}
var url = 'http://${domain}${root}/webpages/' + document.location.href;
if (!window.open(url)) {
location.href = url;
}
}
catch(e) {
alert('Please wait until the page has loaded');
}
})();