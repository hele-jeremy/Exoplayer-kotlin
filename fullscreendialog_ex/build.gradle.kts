import org.gradle.api.internal.artifacts.DefaultModuleIdentifier
import org.gradle.api.internal.artifacts.DefaultModuleVersionSelector
import org.gradle.api.internal.artifacts.ModuleVersionSelectorStrictSpec

plugins {
    androidApp()
    kotlinAndroid()
}

android {
    setAppConfig("fullscreendialog")
    useDefaultBuildTypes()
//    activateJava17()
}
configurations.all {
    resolutionStrategy {
        cacheChangingModulesFor(30,TimeUnit.SECONDS)
//        cacheChangingModulesFor 30, 'seconds'
        force(DefaultModuleVersionSelector.newSelector(DefaultModuleIdentifier.newId("androidx.core","core-ktx"),"1.6.0"))
        force(DefaultModuleVersionSelector.newSelector(DefaultModuleIdentifier.newId("androidx.core","core"),"1.6.0"))
        force(DefaultModuleVersionSelector.newSelector(DefaultModuleIdentifier.newId("androidx.appcompat","appcompat"),"1.3.1"))
//        force 'androidx.core:core-ktx:1.6.0'
//        force 'androidx.core:core:1.6.0'
//        force 'androidx.navigation:navigation-runtime:2.3.5'
//        force 'androidx.appcompat:appcompat:1.3.1'
//        force 'org.jetbrains.kotlin:kotlin-stdlib:1.7.10'
    }
}
dependencies {

    implementation(Libraries.Kotlin.stdlib)
    implementation(Libraries.Androidx.appcompat)
    implementation(Libraries.Androidx.constraintlayout)
    implementation(Libraries.Androidx.Ktx.core)

//    implementation(Libraries.Exoplayer.exoplayer)
    implementation(Libraries.Exoplayer.exoplayercore)
    implementation(Libraries.Exoplayer.exoplayerui)

    implementation(Libraries.TestLibraries.junit)
    implementation(Libraries.AndroidTestLibraries.runner)
    implementation(Libraries.AndroidTestLibraries.Espresso.core)

}
