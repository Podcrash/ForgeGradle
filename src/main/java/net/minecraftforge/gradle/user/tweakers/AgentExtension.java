/*
 * A Gradle plugin for the creation of Minecraft mods and MinecraftForge plugins.
 * Copyright (C) 2013 Minecraft Forge
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
 * USA
 */
package net.minecraftforge.gradle.user.tweakers;

import net.minecraftforge.gradle.common.Constants;
import net.minecraftforge.gradle.user.UserBaseExtension;

public class AgentExtension extends UserBaseExtension
{
    private Object mainClass = "net.minecraft.client.main.Main";
    private Object premainClass;
    private boolean canRedefineClasses = false;
    private boolean canRetransformClasses = false;

    public AgentExtension(AgentPlugin plugin)
    {
        super(plugin);
    }

    public String getMainClass()
    {
        return Constants.resolveString(mainClass);
    }

    public void setMainClass(Object mainClass)
    {
        this.mainClass = mainClass;
    }

    public String getPremainClass()
    {
        return Constants.resolveString(premainClass);
    }

    public void setPremainClass(Object premainClass)
    {
        this.premainClass = premainClass;
    }

    public boolean isCanRedefineClasses()
    {
        return canRedefineClasses;
    }

    public void setCanRedefineClasses(boolean canRedefineClasses)
    {
        this.canRedefineClasses = canRedefineClasses;
    }

    public boolean isCanRetransformClasses()
    {
        return canRetransformClasses;
    }

    public void setCanRetransformClasses(boolean canRetransformClasses)
    {
        this.canRetransformClasses = canRetransformClasses;
    }
}
