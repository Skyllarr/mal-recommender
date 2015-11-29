module.exports = function (grunt) {
    var src = 'src/main/webapp/js/',
        dest = 'target/MyAnimeListRecommender/js/';

    var configs = {
        watch_files: ['src/main/webapp/less/**']
    };

    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        browserify: {
            options: {
                transform: [
                    'babelify'
                ]
            },
            development: {
                options: {
                    watch: true, // use watchify for incremental builds!
                    keepAlive: true, // watchify will exit unless task is kept alive
                    browserifyOptions: {
                        debug: true
                    }
                },
                src: src + 'app.js',
                dest: dest + 'appRendered.js'
            },
            prod: {
                src: src + 'app.js',
                dest: src + 'appRendered.js'
            }
        },
        less: {
            production: {
                files: {
                    "target/MyAnimeListRecommender/css/combined.css": "src/main/webapp/less/main.less"
                }
            }
        },
        watch: {
            src: {
                files: configs.watch_files,
                tasks: ['less']
            }
        }
    });

    grunt.loadNpmTasks('grunt-browserify');
    grunt.loadNpmTasks('grunt-contrib-less');
    grunt.loadNpmTasks('grunt-contrib-watch');

    grunt.registerTask('watchify', ['browserify:development']);
};



