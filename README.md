# OpenToast

OpenToast is a lightweight and customizable Java library designed for creating elegant, animated toast notifications in Swing-based applications. With built-in support for themes, modifiers, and various animation effects, OpenToast helps you easily enhance the user experience with sleek, unobtrusive notifications.

## Features

- **Customizable Toast Notifications**: Display toast notifications with custom titles, messages, icons, and styles.
- **Animated Effects**: Smooth fade-in and fade-out animations for a polished user experience.
- **Automatic Stacking**: Notifications automatically stack without overlapping, ensuring they fit well on the screen.
- **Theme Support**: Apply predefined or custom themes to easily control the look and feel of your toasts.
- **Modifier Support**: Modify the behavior of notifications with `ToastModifier`s such as sound effects or custom actions.
- **Dark Mode**: Built-in support for light and dark themes.

## Installation

1. Add OpenToast as a dependency in your project.
2. Ensure you're using Java 8 or higher.

For Maven:
```xml
<dependency>
    <groupId>io.github.nozyx12</groupId>
    <artifactId>opentoast</artifactId>
    <version>1.0</version> <!-- Update with the latest version -->
</dependency>
```

For Gradle (Groovy DSL):
```groovy
implementation "io.github.nozyx12:opentoast:1.0" // Update with the latest version
```

For Gradle (Kotlin DSL):
```kotlin
implementation("io.github.nozyx12:opentoast:1.0") // Update with the latest version
```

# Custom Themes

OpenToast includes the following default themes:

- Light Theme
- Dark Theme

You can also build your own themes by extending the `ToastStyle` class or creating a new instance of `ToastStyle` class.

# Modifiers

Modifiers allow you to add extra behavior when the notification is displayed or closed.
By default, OpenToast includes two modifiers :
- `SoundToastModifier`: Plays a custom sound when the notification opens.
- `SwipeToastModifier`: Customize notification open and close animations with a slide animation.

You can create custom modifiers by implementing the `ToastModifier` interface.

# Contributing

Feel free to contribute to OpenToast by submitting issues or pull requests on GitHub. Contributions are welcome!