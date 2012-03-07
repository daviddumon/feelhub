(function () {
var body = document.body;
try {
if (!body) {
throw(0);
}
if(document.location.href != 'about:blank') {
    var url = 'http://${domain}${root}/bookmarklet?q=' + encodeURIComponent(document.location.href);
    if (!window.open(url)) {
    location.href = url;
    }
}
}
catch(e) {
alert('Please wait until the page has loaded');
}
})();