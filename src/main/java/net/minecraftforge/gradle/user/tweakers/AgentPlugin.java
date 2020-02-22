package net.minecraftforge.gradle.user.tweakers;

import com.google.common.base.Strings;
import net.minecraftforge.gradle.user.UserVanillaBasePlugin;
import net.minecraftforge.gradle.util.GradleConfigurationException;
import org.gradle.api.tasks.bundling.Jar;

import java.util.List;

public abstract class AgentPlugin extends UserVanillaBasePlugin<AgentExtension>
{
    @Override
    protected void applyVanillaUserPlugin()
    {
    }

    @Override
    protected void afterEvaluate()
    {
        super.afterEvaluate();

        AgentExtension ext = getExtension();

        if (Strings.isNullOrEmpty(ext.getPremainClass()))
        {
            throw new GradleConfigurationException("You must set the agent class of your agent!");
        }

        // add fml tweaker to manifest
        Jar jarTask = (Jar) project.getTasks().getByName("jar");
        jarTask.getManifest().getAttributes().put("Premain-Class", ext.getPremainClass());
        jarTask.getManifest().getAttributes().put("Can-Redefine-Classes", ext.isCanRedefineClasses());
        jarTask.getManifest().getAttributes().put("Can-Retransform-Classes", ext.isCanRetransformClasses());
    }

    @Override
    protected String getClientTweaker(AgentExtension ext)
    {
        return "";// nothing, put it in as an argument
    }

    @Override
    protected String getServerTweaker(AgentExtension ext)
    {
        return "";// nothing, put it in as an argument
    }

    @Override
    protected String getClientRunClass(AgentExtension ext)
    {
        return ext.getMainClass();
    }

    @Override
    protected List<String> getClientRunArgs(AgentExtension ext)
    {
        return super.getClientRunArgs(ext);
    }

    @Override
    protected String getServerRunClass(AgentExtension ext)
    {
        return ext.getMainClass();
    }

    @Override
    protected List<String> getServerRunArgs(AgentExtension ext)
    {
        return super.getServerRunArgs(ext);
    }

    @Override
    protected List<String> getClientJvmArgs(AgentExtension ext)
    {
        List<String> out = ext.getResolvedClientJvmArgs();

        Jar jarTask = (Jar) project.getTasks().getByName("jar");
        out.add("-javaagent:" + jarTask.getArchiveFile().get().getAsFile().getAbsolutePath());

        return out;
    }

    @Override
    protected List<String> getServerJvmArgs(AgentExtension ext)
    {
        List<String> out = ext.getResolvedServerJvmArgs();

        Jar jarTask = (Jar) project.getTasks().getByName("jar");
        out.add("-javaagent:" + jarTask.getArchiveFile().get().getAsFile().getAbsolutePath());

        return out;
    }
}
