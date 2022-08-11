# mesbglister
Create, edit, track, and share custom army lists for Middle-Earth Strategy Battle Game.

This app was created so my friends and I can quickly brainstorm and share army lists with each other. App is currently under review for Google Play store release, link will be shared here.

Ideally plan to add more functionality, fixes, and features in future releases.

## Installation:
MESBGLister will be released on Google Play Store.
Currently, fork it or import the zip file into Android Studio. Run it from an emulator or transfer it onto the users device.


## Screenshots:

<img src="https://user-images.githubusercontent.com/5241162/182973692-b0b00ada-93ce-4ca3-9bbb-2e7904901cad.jpg" alt="drawing" width="250"/> | <img src="https://user-images.githubusercontent.com/5241162/182973708-641f8d14-178f-466b-9d23-ed28f145bc2d.png" alt="drawing" width="250"/> | <img src="https://user-images.githubusercontent.com/5241162/182973713-d743749a-d829-4eb6-8d94-b4d70eeb4c01.png" alt="drawing" width="250"/>


<img src="https://user-images.githubusercontent.com/5241162/182973714-efd6194b-41f8-4dac-aa7e-2ea119623d43.png" alt="drawing" width="250"/> | <img src="https://user-images.githubusercontent.com/5241162/182973710-7de9cb23-4085-4322-b294-2232858ee7bf.png" alt="drawing" width="250"/> | <img src="https://user-images.githubusercontent.com/5241162/182973711-4dcd15ec-5c60-4c77-8fc1-5f6361099572.png" alt="drawing" width="250"/>


## MVVM Architecture:
Model: This layer is responsible for the abstraction of the data sources. Model and ViewModel work together to get and save the data.

View: The purpose of this layer is to inform the ViewModel about the user’s action. This layer observes the ViewModel and does not contain any kind of application logic.

ViewModel: It exposes those data streams which are relevant to the View. Moreover, it serve as a link between the Model and the View.

<img src="https://user-images.githubusercontent.com/5241162/183707618-2d6fdd46-b176-4ab7-9ccf-1c695e12e895.png" alt="drawing"/>

## Room Database:
<img src="https://user-images.githubusercontent.com/5241162/183706076-4acaf672-6097-4a34-80b6-481b2d3670a8.png" alt="drawing"/>


## Built With:

- [Kotlin](https://kotlinlang.org/) - Official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous tasks.
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [Navigation](https://developer.android.com/guide/navigation) - A set of libraries, a plugin, and tooling that simplifies Android navigation.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
  - [Room](https://developer.android.com/topic/libraries/architecture/room) - SQLite object mapping library.
- [Glide](https://github.com/bumptech/glide) - An image loading library for Android.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.
- [SDP](https://github.com/intuit/sdp) - An android library that provides a new size unit - sdp (scalable dp). This size unit scales with the screen size.


App icon by Icons8 - https://icons8.com
