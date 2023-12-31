plugins {
    androidApp()
    kotlinAndroid()
}

android {
    setAppConfig("cast")
    useDefaultBuildTypes()
    activateJava17()
}

dependencies {

    implementation(Libraries.Kotlin.stdlib)
    implementation(Libraries.Androidx.appcompat)
    implementation(Libraries.Androidx.material)
    implementation(Libraries.Androidx.constraintlayout)
    implementation(Libraries.Androidx.Ktx.core)

    implementation(Libraries.Exoplayer.exoplayercore)
    implementation(Libraries.Exoplayer.exoplayerui)
    implementation(Libraries.Exoplayer.exoplayerhls)
    implementation(Libraries.Exoplayer.exoplayercast)

    implementation(Libraries.TestLibraries.junit)
    implementation(Libraries.AndroidTestLibraries.runner)
    implementation(Libraries.AndroidTestLibraries.Espresso.core)

}
