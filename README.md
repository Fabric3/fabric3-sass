Fabric3 Sass Plugin
=========================

The Fabric3 Sass plugin provides compilation of Sass files as part of a Gradle build. The plugin is based on the LibSass library (http://sass-lang
.com/libsass) for fast compilation.   


Use
------------------------

Add the plugin dependency to your build script:
 
    buildscript {
         dependencies {
            classpath 'org.fabric3.gradle:fabric3-sass:3.0.0-SNAPSHOT'
         }
    }

apply the plugin:


    apply plugin: 'fabric3-sass'
    
and optionally configure it:
    
    sass {
        inputFilePath = "src/main/webapp/assets/scss/app.scss"
        includePaths "src/main/webapp/assets/app/scss"
        outputFilePath = "build/sass/scss/app.css"
    }
    
Note paths are interpreted relative to the current project path.

The plugin will add the 'compileSass' task to your project. Further tasks can be added to copy the compiled output and create a web app:

    task copyCompiledCss(type: Copy) {
         from("${buildDir}/sass/scss/app.css")
         into("${buildDir}")
    }

    task prepareWebAssets(dependsOn: ['someOtherTask', 'compileSass', 'copyCompiledCss']) << {

    }

Configuration
------------------------

The following plugin properties may be configured:

 - **inputSyntax**: The input syntax, 'scss' (default) or 'sass'  
 - **outputStyle**: The output style, 'nested' (default), 'compact', 'compressed', or 'expanded'
 - **inputFilePath**: The main Sass file path, relative to the current project path 
 - **outputFilePath**: The compiled file path, relative to the current project path 
 - **sourceMapPath**: The source map file path, relative to the current project path  
 - **includePaths**: A comma separated list of paths to include as part of the compilation,  relative to the current project path
 - **sourceComments**: true if inline source comments should be generated (default false)
 - **omitSourceMapping**: true if the sourceMappingUrl should be omitted in CSS output (default false)
 - **embedSourceMap**: true if sourceMappingUrl should be embedded as a data uri (default false)
 - **sourceMapContents**: true if include contents should be embedded in source maps (default false)
 - **precision**: sets the decimal precision (default 5)


Watch-based Compilation
------------------------

To execute Sass compilation based on a gradle watch (i.e. pickup Sass changes and compile automatically) use a Gradle watch:

    ./gradlew -t :project:compileSass


Building the Source
------------------------

Requirements are JDK 8.

To build the source, execute the Gradle script, e.g. on *Nix:

    ./gradlew 
