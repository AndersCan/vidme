module.exports = function (grunt) {

  //load the tast
  grunt.loadNpmTasks("grunt-ts");
  // Configure grunt here
  grunt.initConfig({
    ts: {
      dev: { // a particular target
        src: ["typescript/**/*.ts"], // The source typescript files, http://gruntjs.com/configuring-tasks#files
        html: ["typescript/partialviews/**/*.html"],
        reference: "typescript/reference.ts",  // If specified, generate this file that you can use for your reference management
        out: 'public/javascript/out.js',         // If specified, generate an out.js file which is the merged js file
        watch: 'typescript'
      }
    }
  });

  grunt.registerTask("default", ["ts:dev"]);
};
