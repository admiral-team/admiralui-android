# Admiral UI Kit ![version](https://img.shields.io/badge/dynamic/json.svg?label=release&url=https://raw.githubusercontent.com/admiral-team/admiralui-android/main/version.json&query=$.external_version)

Visual components library for `Android`

<p align="center">
<img src="/docs/readme-preview.gif?raw=true" align="middle">
</p>

#TEST
## What is UI Kit?
**UI Kit** - is a set of user interface elements.

### Pros of using UI Kit:
- Allows you to adhere to a single visual style and interface solutions
- Speeds up prototyping
- Saves time for designers and developers

## Why Admiral UI Kit?

- 💎 **Lots of components** - 30+ components based on `Android View` from buttons and text fields to timepicker and calendar. *Components based on `Compose` are under development.*
- 🛠 **Customizable** - components have many customizable properties.
- 🎨 **Theming** - support for different color palettes, the ability to create your own and instantly switch between them.
- 🏗 **Maintained** - releases come out every two weeks.
- 📱 **Single style across platforms** - there is a library for [iOS](https://github.com/admiral-team/admiralui-ios)
- 📄 **License** - *TODO..*

## Demo app
A live example of using the `Admiral UI Kit` library is our demo application. With it, you can evaluate all the components implemented in the library in different states, with the ability to switch built-in color themes and create your own.

#### Run demo app
- Clone the `git@github.com:admiral-team/admiralui-android.git` repository
- Switch to the `main` branch
- In Android Studio, run the demo on an emulator or on a real device

## Language and API version
- Main development language `Kotlin`
- Minimum supported Android version: `API 21`

## Download
There are two ways to include the library: `GitHubPackages` and `MavenLocal`.
Read details in the [wiki](https://github.com/admiral-team/admiralui-android/wiki/Download).

## Using components

### Adding a button component
```
<com.admiral.uikit.components.button.Button
   android:id="@+id/btnPrimaryBig"
   android:layout_width="match_parent"
   android:layout_height="wrap_content"
   android:text="Big primary button"
   app:admiralButtonSize="big"
   app:admiralButtonStyle="primary" />
```

Examples of using all other components can be found in the [source](https://github.com/admiral-team/admiralui-android/tree/develop/demo) application demo.

See the [readme](docs/COMPONENTS_USAGE.md) for more instructions on using components.

## Generation of documentation
All public methods and classes are documented. You can also generate code documentation using the [Dokka](https://github.com/Kotlin/dokka) plugin.

## Contributors
We welcome any help, and you can help us with the development of this project. Details in the [wiki](https://github.com/admiral-team/admiralui-android/wiki/Contributors).

## Project team
<table>
  <tr>
    <td align="center"><a href="https://github.com/ton252"><img src="https://avatars.githubusercontent.com/u/13065321?v=4" width="80px;" alt="" /><br /><sub><b>Anton Polyakov</b></sub></a><br /><a href="https://github.com/admiral-team/admiralui-android/commits?author=ton252" title="Code">👑</a></td>
    <td align="center"><a href="https://github.com/timbaton"><img src="https://avatars.githubusercontent.com/u/20974161?v=4" width="80px;" alt="" /><br /><sub><b>Timur Badretdinov </b></sub></a><br /><a href="https://github.com/admiral-team/admiralui-android/commits?author=timbaton" title="Code">💻</a></td>
    <td align="center"><a href="https://github.com/Samuel-Unknown"><img src="https://avatars.githubusercontent.com/u/4298267?v=4" width="80px;" alt="" /><br /><sub><b>Semyon Babakaev</b></sub></a><br /><a href="https://github.com/admiral-team/admiralui-android/commits?author=Samuel-Unknown" title="Code">💻</a></td>
    <td align="center"><a href="https://github.com/Evgeniy-93"><img src="https://avatars.githubusercontent.com/u/101252323?v=4" width="80px;" alt="" /><br /><sub><b>Evgeny Krutsky</b></sub></a><br /><a href="https://github.com/admiral-team/admiralui-android/commits?author=Evgeniy-93" title="Code">🛠</a></td>
  </tr>
</table>
