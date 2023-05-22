# Android **Ktor** Chat Client

## What does the app do?
The app connects to a server (build and deployed - only for testing, now it isn't deployed anymore - on a VM using google services) letting the user to chat with any other person that has the app.

## Programming Languages:
- Kotlin

## Frameworks & Libraries:
- Ktor (client) - https://ktor.io/docs/getting-started-ktor-client.html
- Gradle - https://gradle.org
- Dagger Hilt - https://developer.android.com/training/dependency-injection/hilt-android
- Jetpack Suite (including Jetpack Compose for UI) - https://developer.android.com/modern-android-development

## Implementation:
I've used a MVVM architectural pattern, so I've came with this main packages within the */app/src/main/java/com/dan/qchat/*:
- data (contains the DTOs & Services - interfaces & implementations for them)
- domain (contains the models, validators and utils - which perhaps should have their on package)
- presentation (contains the viewmodels and the functions defining the Compose UI elements)