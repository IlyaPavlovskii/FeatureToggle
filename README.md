# FeatureToggle
Android FeatureToggle library which helps to provide feature toggles on any flavors and manage it by different types:
harcode, resources, remote, with local file storage and with debug panel activity.

<img src="resources/main.gif" alt="FeatureToggle example" style="width:300px;"/>

# Using FeatureToggle 
TBD:
FeatureToggle modules can be obtained from the [github packages](https://maven.pkg.github.com/IlyaPavlovskii/FeatureToggle). 
It's also possible to clone the repository and depend on the modules locally.
Just add the next repository to the dependency resolution manager:

```
dependencyResolutionManagement {
    repositories {
    }
}
```

Connect `feature-toggle` library to your project to start using it:

```
implementation("io.github.ilyapavlovskii.feature.toggle:feature.toggle:X.X.X")
```

If you want to use `debug-panel`, just add the next dependency:

```
implementation("io.github.ilyapavlovskii.feature.toggle:debug.panel:X.X.X")
```

---------------------------------------------------------------------------
# How to use
