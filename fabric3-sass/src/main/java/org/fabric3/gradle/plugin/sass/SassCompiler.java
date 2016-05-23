package org.fabric3.gradle.plugin.sass;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.stream.Stream;

import io.bit3.jsass.CompilationException;
import io.bit3.jsass.Options;
import io.bit3.jsass.Output;
import org.gradle.api.logging.Logger;

/**
 * Configures and executes the JSass compiler.
 */
class SassCompiler {
    enum SassSyntax {
        SCSS,
        SASS
    }

    enum OutputStyle {
        COMPACT,
        COMPRESSED,
        EXPANDED,
        NESTED
    }

    private Options options;
    private Logger logger;

    SassCompiler(Logger logger) {
        options = new Options();
        this.logger = logger;
    }

    void compile(File inputFile, File outputFile) throws CompilationException {

        try {
            io.bit3.jsass.Compiler compiler = new io.bit3.jsass.Compiler();
            Output output = compiler.compileFile(inputFile.toURI(), outputFile.toURI(), options);
            String css = output.getCss();
            write(outputFile, css);
            generateSourceMap(output);

            logger.info("Sass compilation completed");
        } catch (CompilationException e) {
            // do not throw the error as it will abort watch loops
            logger.error("Sass compilation failed", e);
        }
    }

    /**
     * Adds paths for included Sass files.
     *
     * @param paths the paths
     */
    void addIncludePaths(String... paths) {
        Stream<File> files = Arrays.stream(paths).map(File::new);
        if (logger.isInfoEnabled()) {
            files = files.peek(file -> logger.info(file.getPath()));
        }
        files.forEach(options.getIncludePaths()::add);
    }

    void setSourceMapFile(File file) {
        options.setSourceMapFile(file.toURI());
    }

    void setOutputStyle(OutputStyle outputStyle) {
        options.setOutputStyle(io.bit3.jsass.OutputStyle.values()[outputStyle.ordinal()]);
    }

    void setInputSyntax(SassSyntax syntax) {
        options.setIsIndentedSyntaxSrc(syntax == SassSyntax.SASS);
    }

    void setSourceComments(boolean value) {
        options.setSourceComments(value);
    }

    void setOmitSourceMappingUrl(boolean url) {
        options.setOmitSourceMapUrl(url);
    }

    void setSourceMapEmbed(boolean value) {
        options.setSourceMapEmbed(value);
    }

    void setSourceMapContents(boolean value) {
        options.setSourceMapContents(value);
    }

    void setPrecision(int precision) {
        options.setPrecision(precision);
    }

    private void generateSourceMap(Output output) {
        String sourceMap = output.getSourceMap();
        if (sourceMap == null || sourceMap.trim().length() == 1) {
            return;
        }
        File sourceMapFile = new File(options.getSourceMapFile());

        write(sourceMapFile, sourceMap);
    }

    private void write(File file, String data) {
        //noinspection ResultOfMethodCallIgnored
        file.getParentFile().mkdirs();
        try (OutputStream stream = new FileOutputStream(file, false)) {
            stream.write(data.getBytes());
            stream.close();
        } catch (IOException e) {
            logger.error("Error writing file: " + file.getName(), e);
        }
    }

}
