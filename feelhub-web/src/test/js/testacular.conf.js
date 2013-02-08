// Testacular configuration
// Generated on Fri Feb 01 2013 16:46:45 GMT+0100 (CET)


// base path, that will be used to resolve files and exclude
basePath = '../../..';


// list of files / patterns to load in the browser
files = [
    JASMINE,
    JASMINE_ADAPTER,
    REQUIRE,
    REQUIRE_ADAPTER,

    {pattern: 'src/test/js/lib/**/*.js', included: false},
    {pattern: 'src/main/webapp/static/js/modules/**/*', included: false},
    {pattern: 'src/main/webapp/static/js/plugins/**/*', included: false},
    {pattern: 'src/main/webapp/static/js/view/**/*', included: false},
    {pattern: 'src/main/webapp/static/js/templates/**/*', included: false},
    {pattern: 'src/test/js/jasmine/**/*.test.js', included: false},
    'src/test/js/main-test.js'
];


// list of files to exclude
exclude = [

];


// test results reporter to use
// possible values: 'dots', 'progress', 'junit'
reporters = ['dots', 'junit'];

junitReporter = {
    outputFile: 'test-results.xml'
};

// web server port
port = 9876;


// cli runner port
runnerPort = 9100;


// enable / disable colors in the output (reporters and logs)
colors = true;


// level of logging
// possible values: LOG_DISABLE || LOG_ERROR || LOG_WARN || LOG_INFO || LOG_DEBUG
logLevel = LOG_INFO;


// enable / disable watching file and executing tests whenever any file changes
autoWatch = false;


// Start these browsers, currently available:
// - Chrome
// - ChromeCanary
// - Firefox
// - Opera
// - Safari (only Mac)
// - PhantomJS
// - IE (only Windows)
browsers = ['Chrome'];


// If browser does not capture in given timeout [ms], kill it
captureTimeout = 60000;


// Continuous Integration mode
// if true, it capture browsers, run tests and exit
singleRun = false;
