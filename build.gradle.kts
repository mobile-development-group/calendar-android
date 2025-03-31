// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Plugins.Application.id) version Plugins.Application.version apply false
    id(Plugins.KotlinAndroid.id) version Plugins.KotlinAndroid.version apply false
    id(Plugins.KotlinCompose.id) version Plugins.KotlinCompose.version apply false
    id(Plugins.Library.id) version Plugins.Library.version apply false
    id(Plugins.MavenPublish.id) version Plugins.MavenPublish.version apply false
    id(Plugins.NMCP.id) version Plugins.NMCP.version apply false
}