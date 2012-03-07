(function () {
    try {
        if (!document.body) {
            throw(0);
        }
        if (document.location.href != 'about:blank') {
            var url = 'http://${domain}${root}/bookmarklet?q=' + encodeURIComponent(document.location.href) + '&version=1';
            if (!window.open(url)) {
                location.href = url;
            }
        }
    }
    catch (e) {
        alert('Please wait until the page has loaded');
    }
})();