# `devnindo-schemagen` Plugin

The `devnindo-schemagen` is a companion plugin designed for the [`devnindo-datatype`](https://github.com/devnindo/devnindo-datatype) library. It assists in generating
schema representations for a DataBean class, making JSON conversion and field validation seamless.

## Installation

1. Add the Devnindo data type dependency in your `build.gradle`:

```groovy
dependencies {
    // ... other dependencies
    implementation 'io.devnindo.core:devnindo-datatype:0.9.8'
}
```

2. Incorporate the schemagen plugin:

```groovy
plugins {
    // ... other plugins
    id 'io.devnindo.devnindo-schemagen' version '0.9.17'
}
```

Upon setup, a Gradle task `generateSchema` will be available. Running `compileJava` or `generateSchema`  task will create the schema representation
for your `DataBean` class.



 
