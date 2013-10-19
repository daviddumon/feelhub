(function () {
    try {
        if (!document.body) {
            throw(0);
        }
        if (document.location.href != 'about:blank') {
            var url = 'https://www.feelhub.com/bookmarklet?q=' + encodeURIComponent(document.location.href);
            location.href = url;
        }
    }
    catch (e) {
        alert('Please wait until the page has loaded');
    }
})();