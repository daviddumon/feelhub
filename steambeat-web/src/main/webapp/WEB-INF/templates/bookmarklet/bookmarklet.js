(function () {
    try {
        if (!document.body) {
            throw(0);
        }
        if (document.location.href != 'about:blank') {
            var url = '${root}/topic/' + encodeURI(document.location.href);
            if (!window.open(url)) {
                location.href = url;
            }
        }
    }
    catch (e) {
        alert('Please wait until the page has loaded');
    }
})();