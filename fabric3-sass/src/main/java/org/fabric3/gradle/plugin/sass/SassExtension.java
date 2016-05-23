package org.fabric3.gradle.plugin.sass;

/**
 * Plugin configuration extension.
 */
@SuppressWarnings("WeakerAccess")
public class SassExtension {

    /**
     * Sass syntax defined by @{link {@link SassCompiler.SassSyntax}.
     */
    private String inputSyntax = "scss";

    /**
     * Output styles defined by @{link {@link SassCompiler.OutputStyle}.
     */
    private String outputStyle = "nested";

    private String inputFilePath;
    private String outputFilePath;
    private String sourceMapPath;
    private String includePaths = "";

    private boolean sourceComments;
    private boolean omitSourceMapping;
    private boolean embedSourceMap;
    private boolean sourceMapContents;
    private int precision = 5;

    public SassExtension() {
    }

    public String getInputFilePath() {
        return inputFilePath;
    }

    public void setInputFilePath(String path) {
        this.inputFilePath = path;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public void setOutputFilePath(String path) {
        this.outputFilePath = path;
    }

    public String getSourceMapPath() {
        return sourceMapPath;
    }

    public void setSourceMapPath(String path) {
        this.sourceMapPath = path;
    }

    public String getIncludePaths() {
        return includePaths;
    }

    public void setIncludePaths(String paths) {
        this.includePaths = paths;
    }

    public String getInputSyntax() {
        return inputSyntax;
    }

    public void setInputSyntax(String inputSyntax) {
        this.inputSyntax = inputSyntax;
    }

    public String getOutputStyle() {
        return outputStyle;
    }

    public void setOutputStyle(String outputStyle) {
        this.outputStyle = outputStyle;
    }

    public boolean getSourceComments() {
        return sourceComments;
    }

    public void setSourceComments(boolean sourceComments) {
        this.sourceComments = sourceComments;
    }

    public boolean getOmitSourceMapping() {
        return omitSourceMapping;
    }

    public void setOmitSourceMapping(boolean value) {
        this.omitSourceMapping = value;
    }

    public boolean getEmbedSourceMap() {
        return embedSourceMap;
    }

    public void setEmbedSourceMap(boolean value) {
        this.embedSourceMap = value;
    }

    public boolean getSourceMapContents() {
        return sourceMapContents;
    }

    public void setSourceMapContents(boolean value) {
        this.sourceMapContents = value;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }
}
