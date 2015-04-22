module.exports = function (grunt) {

    // Install commands are located in the comments above their load.
    //load the tast
    // npm install grunt-ts --save-dev
    grunt.loadNpmTasks("grunt-ts");
    //load uglif
    // npm install grunt-contrib-uglify --save-dev
    grunt.loadNpmTasks('grunt-contrib-uglify');
    // watcher
    // npm install grunt-contrib-watch --save-dev
    grunt.loadNpmTasks('grunt-contrib-watch');
    // html mini
    // npm install grunt-contrib-htmlmin --save-dev
    grunt.loadNpmTasks('grunt-contrib-htmlmin');
    // Configure grunt here
    grunt.initConfig({
        ts: {
            dev: { // a particular target
                src: ["typescript/**/*.ts"], // The source typescript files, http://gruntjs.com/configuring-tasks#files
                html: ["typescript/partialviews/**/*.html"],
                reference: "typescript/reference.ts",  // If specified, generate this file that you can use for your reference management
                out: 'public/javascripts/out.js'         // If specified, generate an out.js file which is the merged js file
            }
        },
        uglify: {
            my_target: {
                files: {
                    'public/javascripts/out.min.js': ['public/javascripts/out.js']
                }
            }
        },
        htmlmin: {
            dist: {
                options: {
                    removeComments: true,
                    collapseWhitespace: true
                },
                expand: true,
                flatten: true,
                src: ['typescript/partialviews/**/*.html'],
                dest: 'public/partialviews/'
            }
        },
        watch: {
            files: ['typescript/**/*'],
            tasks: ['uglify:my_target', 'htmlmin:dist', 'ts:dev']
        }
    });

    grunt.registerTask("default", ["ts:dev", "uglify:my_target", "htmlmin:dist", "watch"]);
};
