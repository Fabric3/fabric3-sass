package org.fabric3.gradle.plugin.sass;

import java.io.File;
import java.nio.file.Paths;

import io.bit3.jsass.CompilationException;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

/**
 * Task to compile a set of Sass files to CSS.
 */
class CompileSass extends DefaultTask {
    private SassCompiler compiler;

    public CompileSass() {
        compiler = new SassCompiler(getLogger());
    }

    public String getDescription() {
        return "Compiles SCSS/SASS to CSS";
    }

    @TaskAction
    public void compileSass() throws CompilationException {
        SassExtension extension = getProject().getExtensions().findByType(SassExtension.class);
        if (extension == null) {
            extension = new SassExtension();
        }

        File inputFile = resolveInputFile(extension);

        File outputFile = resolveOutputFile(extension);

        configureCompiler(extension);

        if (extension.getSourceMapPath() != null) {
            compiler.setSourceMapFile(resolveFile(extension.getSourceMapPath()));
        }

        compiler.compile(inputFile, outputFile);
    }

    private void configureCompiler(SassExtension extension) {
        compiler.setInputSyntax(SassCompiler.SassSyntax.valueOf(extension.getInputSyntax().toUpperCase()));
        compiler.setOutputStyle(SassCompiler.OutputStyle.valueOf(extension.getOutputStyle().toUpperCase()));
        compiler.addIncludePaths(extension.getIncludePaths().split(","));

        compiler.setSourceMapContents(extension.getSourceMapContents());
        compiler.setSourceMapEmbed(extension.getEmbedSourceMap());
        compiler.setSourceComments(extension.getSourceComments());
        compiler.setOmitSourceMappingUrl(extension.getOmitSourceMapping());
        compiler.setPrecision(extension.getPrecision());
    }

    private File resolveInputFile(SassExtension extension) {
        if (extension.getInputFilePath() == null) {
            throw new IllegalArgumentException("Parameter not specified: inputFilePath");
        }

        File inputFile = resolveFile(extension.getInputFilePath());
        if (!inputFile.exists()) {
            throw new IllegalArgumentException("Path does not exist: " + inputFile.getPath());
        }
        return inputFile;
    }

    private File resolveOutputFile(SassExtension extension) {
        if (extension.getOutputFilePath() == null) {
            throw new IllegalArgumentException("Parameter not specified: outputFilePath");
        }
        return resolveFile(extension.getOutputFilePath());
    }

    /**
     * Resolves the file relative to the current project directory.
     *
     * @param path the relative file path
     * @return the resolved file
     */
    private File resolveFile(String path) {
        return Paths.get(getProject().getProjectDir().getAbsolutePath(), path).toFile();
    }

}

