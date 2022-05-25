

# Admiral UI-KIT ![version](https://img.shields.io/badge/dynamic/json.svg?label=release&url=https://raw.githubusercontent.com/admiral-team/admiralui-android/main/version.json&query=$.external_version)

Библиотека визуальных компонентов для `Android`

<p align="center">
<img src="/docs/readme-preview.png?raw=true" align="middle">
</p>

## Что такое UI-KIT?

`UI-KIT` - это готовый набор элементов пользовательского интерфейса. 
### Что нам даёт UI-KIT:
- Позволяет придерживаться единого визуального стиля и интерфейсных решений
- Ускоряет прототипирование
- Экономит время дизайнеров и разработчиков

## Почему Admiral UI-KIT?

- 💎 **Множество готовых компонентов** - 30+ компонентов на базе `Android View` от кнопок и тектовых полей до таймпикера и календаря. *Компоненты на базе `Compose` находятся в разработке.
- 🛠 **Кастомизируемость** - компоненты имеют множество настраиваемых свойств.
- 🎨 **Темизация** - поддержка разных цветовых палитр, возможность создания собственных и мгновенное переключение между ними.
- 🏗 **Поддержка** - релизы выходят раз в две недели.
- 📱 **Единый стиль на разных платформах** - есть библиотека под [iOS](https://github.com/admiral-team/admiralui-ios)
- 📄 **Лицензия** - ...

## Демо приложение
Живым примером использования библиотеки `Admiral UI-KIT` является наше демо-приложение. С ним вы сможете оценить все реализованные в библиотеке компоненты в разных стейтах, с возможностью переключения встроенных цветовых тем и созданию собственных.

## Подключение
Подключение можно осуществить двумя способами:
1) Подключив зависимость от репозитория `GitHubPackages`
2) Локально развернуть репозиторий с артефактами нужной версии

### Подключение репозитория GitHubPackages
Этот способ предпочтителен для подключения релизных версий библиотеки
<details>
    <summary>Инструкция</summary>

1. Зайти в аккаунт на Github и сгенерировать токен https://github.com/settings/tokens (из доступов выбрать `read:packages`)
2. Добавить в файл `local.properties` логин от Github и сгенерированный токен (без кавычек):
```
GITHUB_USERNAME=...
GITHUB_TOKEN=...
```
3. Добавить зависимость от репозитория. Например это можно сделать в `settings.gradle` прописав путь к репозиторию + логин и токен Github-пользователя:
```
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/admiral-team/admiralui-android")

                credentials {
                    def versionPropsFile = file('local.properties')
                    Properties versionProps = new Properties()
                    
                    if (versionPropsFile.canRead()) {
                        versionProps.load(new FileInputStream(versionPropsFile))
                    }
                    def gitHubUsername = versionProps['GITHUB_USERNAME']
                    def gitHubToken = versionProps['GITHUB_TOKEN']
                    username = gitHubUsername
                    password = gitHubToken
                }
            }
        }
    }
}
```
4. Добавить необходимые зависимости в `build.gradle`. Релизные версии можно посмотреть [тут](https://github.com/admiral-team/admiralui-android/releases)
```
dependencies {
    def admiralui_version = "4.1.0"
    implementation "admiralui-android:admiral-uikit:$admiralui_version"
    implementation "admiralui-android:admiral-uikit-compose:$admiralui_version"
 // implementation "admiralui-android:admiral-resources:$admiralui_version"
 // implementation "admiralui-android:admiral-uikit-common:$admiralui_version"
 // implementation "admiralui-android:admiral-themes:$admiralui_version"
 // implementation "admiralui-android:admiral-themes-compose:$admiralui_version"
}

```
</details>

### Подключение локального репозитория
Этот способ можно использовать для подключения develop версий библиотеки
<details>
    <summary>Инструкция</summary>

1. Скачать репозиторий этого проекта и переключиться на develop ветку
2. Собрать артефакты для локального репозитория. Для этого запустить в терминале студии команду
```
./gradlew publishToMavenLocal -PartifactIdSuffix="-develop"
```
3. В проекте в котором необходимо подключить библиотеку добавить зависимость от локального репозитория `mavenLocal`. Файл `settings.gradle`:
```
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
}
```
4. Добавить необходимые зависимости. У зависимостей develop сбороки есть суффикс `-develop` и версия всегда `0.0.0`
```
dependencies {
    def admiralui_develop_version = "0.0.0"
    implementation "admiralui-android:admiral-uikit-develop:$admiralui_develop_version"
    implementation "admiralui-android:admiral-uikit-compose-develop:$admiralui_develop_version"
 // implementation "admiralui-android:admiral-resources-develop:$admiralui_develop_version"
 // implementation "admiralui-android:admiral-uikit-common-develop:$admiralui_develop_version"
 // implementation "admiralui-android:admiral-themes-develop:$admiralui_develop_version"
 // implementation "admiralui-android:admiral-themes-compose-develop:$admiralui_develop_version"
}

```
**NB**: Следует учитывать что Gradle кэширует зависимости и если пересобрать артефакты для дев сборки, по умолчанию будут
использоваться закешированные версии. Следует выполнить команду `./gradlew build --refresh-dependencies` или просто удалить папку
с кешем для Gradle
</details>

## Язык и версия API
- Основной язык разработки `Kotlin`
- Минимально поддерживаемая версия Android: `API 21`

## Структура проекта
Библиотека состоит из следующих основных модулей:

- `admiral-resources` - иконки и шритфы
- `admiral-themes` - механизм темизации на базе `Android View` / `XML`
- `admiral-themes-compose` - механизм темизации для `Compose`
- `admiral-uikit` - UI компоненты на базе `Android View` / `XML`
- `admiral-uikit-compose` - UI компоненты для `Compose`
- `admiral-uikit-common` - классы и extension-методы общие для модулей `admiral-uikit` и `admiral-uikit-compose`

## Использование компонентов
Дополнительная инструкция по использованию компонентов [Readme](docs/COMPONENTS_USAGE.md)

## Генерация документации 
Сгенерировать документацию к коду можно с помощью плагина [Dokka](https://github.com/Kotlin/dokka)

## Контрибьютерам
Мы рады любой помощи, и вы можете помочь нам с развитием этого проекта.
Что бы завести `issue` пожалуйста используйте готовые [шаблоны](https://github.com/admiral-team/admiralui-android/issues/new/choose).
Для создания `Pull Request`ов необходимо создать `fork` проекта, внести изменения в код и создать `Pull Request` в этот проект.
