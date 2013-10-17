(function () {
    try {
        if (!document.body) {
            throw(0);
        }
        if (document.location.href != 'about:blank') {
            var url = 'http://localtest.me:8080/bookmarklet?q=' + encodeURIComponent(document.location.href);
            if (!window.open(url)) {
                location.href = url;
            }
        }
    }
    catch (e) {
        alert('Please wait until the page has loaded');
    }
})();