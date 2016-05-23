package org.fabric3.gradle.plugin.sass;

import javax.inject.Inject;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * Compiles Sass to CSS.
 */
public class Fabric3SassPlugin implements Plugin<Project> {

    @Inject
    public void apply(Project project) {
        project.getExtensions().create("sass", SassExtension.class);
        project.getTasks().create("compileSass", CompileSass.class);
    }

}
