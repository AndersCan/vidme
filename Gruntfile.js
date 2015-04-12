module.exports = function (grunt) {

  //load the tast
  grunt.loadNpmTasks("grunt-ts");
  //load uglif
  grunt.loadNpmTasks('grunt-contrib-uglify');
  // Configure grunt here
  grunt.initConfig({
    ts: {
      dev: { // a particular target
        src: ["typescript/**/*.ts"], // The source typescript files, http://gruntjs.com/configuring-tasks#files
        html: ["typescript/partialviews/**/*.html"],
        reference: "typescript/reference.ts",  // If specified, generate this file that you can use for your reference management
        out: 'public/javascripts/out.js',         // If specified, generate an out.js file which is the merged js file
        watch: 'typescript'
      }
    },
    uglify: {
      my_target: {
        files: {
          'public/javascripts/out.min.js': ['public/javascripts/out.js']
        }
      }
    }
  });

  grunt.registerTask("default", ["ts:dev","uglify:my_target"]);
  //grunt.registerTask("uglify", []);
};
