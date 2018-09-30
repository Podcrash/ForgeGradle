package net.minecraftforge.gradle.common.util;

import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.ModuleVersionIdentifier;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

public class MavenArtifactDownloader {

    private static final Map<Project, Integer> COUNTERS = new WeakHashMap<>();
    private static final Map<String, Set<File>> CACHE = new HashMap<>();
    private static final Map<String, String> VERSIONS = new HashMap<>();

    public static Set<File> download(Project project, String artifact) {
        Set<File> ret = CACHE.get(artifact);
        if (ret == null) {
            Configuration cfg = project.getConfigurations().create("downloadDeps" + COUNTERS.getOrDefault(project, 0));
            Dependency dependency = project.getDependencies().create(artifact);
            cfg.getDependencies().add(dependency);
            cfg.resolutionStrategy(strat -> {
               strat.cacheDynamicVersionsFor(10, TimeUnit.MINUTES);
            });
            ret = cfg.resolve();

            Artifact mine = Artifact.from(artifact);
            cfg.getResolvedConfiguration().getResolvedArtifacts().forEach(art -> {
                ModuleVersionIdentifier resolved = art.getModuleVersion().getId();
                if (resolved.getGroup().equals(mine.getGroup()) && resolved.getName().equals(mine.getName())) {
                    if ((mine.getClassifier() == null && art.getClassifier() == null) || mine.getClassifier().equals(art.getClassifier()))
                        VERSIONS.put(artifact, resolved.getVersion());
                }
            });

            project.getConfigurations().remove(cfg);
            COUNTERS.compute(project, (proj, prev) -> (prev != null ? prev : 0) + 1);
            CACHE.put(artifact, ret);
        }
        return ret;
    }

    public static File single(Project project, String artifact) {
        Set<File> ret = download(project, artifact);
        return ret == null ? null : ret.iterator().next();
    }

    public static String getVersion(Project project, String artifact) {
        download(project, artifact);
        return VERSIONS.get(artifact);
    }
}
