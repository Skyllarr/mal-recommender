module.exports = function (grunt) {
    var src = 'src/main/webapp/js/',
        destProd = 'src/main/webapp/webapp-target/',
        dest = 'target/MyAnimeListRecommender/webapp-target/';


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
                dest: destProd + 'appRendered.js'
            }
        },
        less: {
            development: {
                files: {
                    "target/MyAnimeListRecommender/webapp-target/combined.css": "src/main/webapp/css/main.less"
                }
            },
            prod: {
                files: {
                    "src/main/webapp/webapp-target/combined.css": "src/main/webapp/css/main.less"
                }
            }
        }
    });

    grunt.loadNpmTasks('grunt-browserify');
    grunt.loadNpmTasks('grunt-contrib-less');

    grunt.registerTask('watchify', ['browserify:development']);
};



