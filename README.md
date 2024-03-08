# Movies

This project showcases a modern approach to [Android](https://www.android.com/)
 application development using [Kotlin](https://kotlinlang.org/)
 and the latest technology stack and focusing on listing movies using [TMDB  API](https://developer.themoviedb.org/docs/getting-started).
 
## 📸 Screenshots 

![Screenshot](images_frame.png)
 
## 🛠️ Setup

- You must create an account at https://www.themoviedb.org/ and follow the https://developer.themoviedb.org/docs/getting-started instructions.
- When you follow the instructions, you can have an API KEY.
- You have to add the API KEY in `local.properties`.

```
API_KEY = xxxxxxxxxxxxxxxxxxx
```

## 📐 Architecture

- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - is a collection of libraries that help you design robust, testable, and maintainable apps.
- [Model-View-ViewModel](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) (MVVM) - is a software architectural pattern that separates the user interface logic (View) from the application's business logic and data (Model), facilitating separation of concerns using an intermediary component called ViewModel.
- [Modular App Architecture](https://developer.android.com/topic/modularization) - is an architectural approach where a project is divided into multiple modules, each focusing on a specific aspect or functionality, promoting modularization and better organization of code.
- [S.O.L.I.D](https://en.wikipedia.org/wiki/SOLID) - design principles intended to make software designs more understandable, flexible and maintainable.
- [Clean Architecture](https://developer.android.com/topic/architecture) - is an architectural approach in software projects that ensures code cleanliness, flexibility, and maintainability by separating business logic from external dependencies.

### Modules

Modules are collection of source files and build settings that allow you to divide a project into discrete units of functionality. In this case apart from dividing by functionality/responsibility, existing the following dependence between them :
    
#### App Module

 The `:app` module is an [com.android.application](https://developer.android.com/build), which is needed to create the app bundle. It is also responsible for initiating the [dependency modules (Hilt)](https://developer.android.com/training/dependency-injection/hilt-android#hilt-modules) and another project global libraries, differentiating especially between different app environments.
 
#### Data Module

The `:data` module represents the [data layer](https://developer.android.com/topic/architecture/data-layer) of the application. This module provides access to both local and remote data sources, manages data models and mappings, and handles data streams. Additionally, repository classes in this module manage the interaction and business logic between data sources. This architecture is used to ensure data management within the application and abstract the data layer from other modules. Also, it contains unit test about this structures.

#### Domain Module

The `:domain` module represents the [domain layer](https://developer.android.com/topic/architecture/domain-layer) of the application. Domain module contains the core logic of the application and typically defines the basic functionalities, business rules, and data management. This module represents the core logic of the application and usually communicates with other layers (data, view). Includes domain models, use cases and interface for interaction with data module. This module provides the core functionality of the application and reduces dependencies between other modules. Also, it contains unit test about this structures.

#### Common-ui Module

The `:common-ui` module contains common structures used in the feature modules. In the `res` directory, there are `drawable` items, `font`, `navigation`  graph, `values` (colors, strings) files.

#### Common-test Module

The `:common-test` module contains common structures used when writing tests in other modules. There is an object `DataPlaceholder` used for fake data and a class to create `TestDispatcher` for rule in coroutine test.

#### Feature-main Module

The `:feature-main` module contains the structures related to the main screen of the application. Main screen is the greeting screen when the application is started. Feature-main module contains the following structures related to main: fragment and its xml design, viewmodel and its unit test.

#### Feature-fav Module

The `:feature-fav` module contains the structures related to the favorites screen of application. Favorites screen is the screen that lists the movies that have been added as favorites. Feature-fav module contains the following structures related to favorites: fragment and its xml design, viewmodel and its unit test.

#### Feature-details Module

The `:feature-details` module contains the structures related to the details screen of the application. The details screen is a screen that can be navigated by clicking on the movie card from the main screen or favorite screens, contains more information about the movie, and allows us to add or remove the movie to favorites. The feature-details module contains the following structures related to details: fragment and its xml design, viewmodel and its unit test.


## 🏭 Tech Stacks

**Mainly on:**

- [Kotlin](https://kotlinlang.org/) - Kotlin is a modern but already mature programming language designed to make developers happier. It's concise, safe, interoperable with Java and other languages, and provides many ways to reuse code between multiple platforms for productive programming.

- [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) - Gradle’s Kotlin DSL provides an alternative syntax to the traditional Groovy DSL with an enhanced editing experience in supported IDEs, with superior content assist, refactoring, documentation, and more.

- [KSP](https://developer.android.com/build/migrate-to-ksp) - KSP (Kotlin Symbol Processing) is a Kotlin-first alternative to kapt. KSP analyzes Kotlin code directly, which is up to 2x faster.

- [Version Catalog](https://developer.android.com/build/migrate-to-catalogs) - Gradle version catalogs enable you to add and maintain dependencies and plugins in a scalable way. Using Gradle version catalogs makes managing dependencies and plugins easier when you have multiple modules.

### Patterns 

- [Repository Pattern](https://developer.android.com/topic/architecture) - The Repository Pattern is one of the most popular patterns to create an enterprise level application. It restricts us to work directly with the data in the application and creates new layers for database operations, business logic, and the application's UI.
- [UseCase Pattern](https://caminao.blog/how-to-implement-symbolic-representations/patterns/functional-patterns/use-case-patterns/) - This pattern means to convert and pass user actions to inner layers of the application.

- [Observer Pattern](https://code.tutsplus.com/android-design-patterns-the-observer-pattern--cms-28963t) - The observer pattern is a software design pattern in which an object, called the subject, maintains a list of its dependents, called observers, and notifies them automatically of any state changes, usually by calling one of their methods.
 

### Dependencies

- [Jetpack](https://developer.android.com/jetpack) :
    - [Android KTX](https://developer.android.com/kotlin/ktx.html) - provide concise, idiomatic Kotlin to Jetpack and Android platform APIs.
    
    - [AndroidX](https://developer.android.com/jetpack/androidx) - major improvement to the original Android [Support Library](https://developer.android.com/topic/libraries/support-library/index), which is no longer maintained.

    - [Data Binding](https://developer.android.com/topic/libraries/data-binding) - allows you to bind UI components in your layouts to data sources in your app using a declarative format rather than programmatically.
    
    - [Fragment](https://developer.android.com/jetpack/androidx/releases/fragment) - represents a segment of a user interface or behavior within an activity, allowing for modularization and reusability of UI components and logic in Android.
    
    - [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform actions in response to a change in the lifecycle status of another component, such as activities and fragments.
    
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - designed to store and manage UI-related data in a lifecycle conscious way. The ViewModel class allows data to survive configuration changes such as screen rotations.
    
    - [Hilt](https://dagger.dev/hilt/) - provides a standard way to incorporate Dagger dependency injection into an Android application.
    
    - [Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - helps you load and display pages of data from a larger dataset from local storage or over network.
    
    - [Room](https://developer.android.com/training/data-storage/room) - is an Android library that provides an abstraction layer over SQLite to allow for more robust database access while leveraging the power of SQLite database queries.
    
    - [Navigation](https://developer.android.com/jetpack/androidx/releases/navigation) - is a component and library in Android that simplifies and manages navigation between screens in applications.
    
    

- [Retrofit](https://square.github.io/retrofit/) - is a type-safe HTTP client for Android and Java that simplifies the process of making network requests and handling REST APIs by allowing developers to define API interfaces with annotated methods.

- [OkHttp-Logging-Interceptor](https://github.com/square/okhttp) - is a tool used to intercept, modify, and process HTTP requests and responses, enabling customization of network requests by adding customized behaviors.

- [Gson](https://github.com/google/gson) - makes it easy to parse JSON into Kotlin objects.

- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) - managing background threads with simplified code and reducing needs for callbacks.

- [Flow](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - is a cold asynchronous data stream that sequentially emits values and completes normally or with an exception.

- [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow) - is a state-holder observable flow that emits the current and new state updates to its collectors.

- [Coil](https://github.com/coil-kt/coil) - is an image loading library for Android backed by Kotlin Coroutines.

- [R8](https://developer.android.com/build/shrink-code) - is a code shrinker and optimizer tool used in Android development to reduce the size of the application's code and improve performance by removing unused code and applying various optimizations during the build process.
 
- [LeakCanary](https://square.github.io/leakcanary/) - is a memory leak detection library for Android.

- [Ktlint](https://github.com/pinterest/ktlint) - an anti-bikeshedding Kotlin linter with built-in formatter.

- [Detekt](https://github.com/detekt/detekt) - a static code analysis tool for the Kotlin programming language.

- [Test](https://developer.android.com/training/testing) : 

    - [AndroidX](https://developer.android.com/jetpack/androidx/releases/test) - the androidx test library provides an extensive framework for testing Android apps.
 
    - [JUnit](https://github.com/junit-team/junit4) - is a simple framework to write repeatable tests. It is an instance of the xUnit architecture for unit testing frameworks.
    
    - [Mockito](https://github.com/mockito/mockito-kotlin) - is a most popular Mocking framework for unit tests written in Java.
    
    - [Mockk](https://github.com/mockk/mockk) - provides DSL to mock behavior. Built from zero to fit Kotlin language.
    
   
    
    - [Turbine](https://github.com/cashapp/turbine) - is a small testing library for kotlinx.coroutines Flow
    
    - [Truth](https://github.com/google/truth) - is a library for performing assertions in tests.
