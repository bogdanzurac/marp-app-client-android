<h1>MARP</h1>

## 💡 Introduction
MARP is a **scalable architecture** for mobile apps. It stands for Multi-Module-App-Repository-Platform. 

When we look at a scalable architecture, we need to factor in:
- how the code **performs** - Reactive, functional vs imperative
    - helps to improve code readability and effectiveness as well as the entire app lifecycle and functionality
- how the code is **layered** - MVVM vs Monster-View-Presenter
    - helps to make changes to individual layers without affecting the rest of the app
- how the code is **structured**  - modules, repositories
    - helps to improve reusability and interchangeability of components

While the project shows the progression of an app through all stages of scalable architecture, MARP mostly tries to tackle the last step, which revolves around **structuring** the project code in a multi-app & multi-platform environment.

A MARP architecture handles:
- splitting code into multiple modules in order to segregate features and libraries into their own dedicated space, following SRP principle of clean architecture
- sharing modules between multiple whitelabel apps with the same functionality inside the same code repository
- extracting modules into their own standalone repositories, in order to be used for apps with a different purpose altogether
- sharing common business logic between multiple platforms, in order to avoid code duplication and improve time to market

## 👷 Project Structure
The project consists of 2 types of apps (`client` and `admin`) each having its own purpose, built for 2 fictional client companies (`Elgoog` and `Macrosoft`) and on 2 mobile app platforms (`Android` and `iOS`).
The client apps can have any number of these 5 features:
1. Crypto tracker
2. Movies explorer
3. News feed
4. Notes taker
5. Weather checker

The admin apps are limited to only administering the notes taken from the client apps.

The project follows the evolution of the entire architecture for this setup, considering these steps:
1. Creation of client Android app for Elgoog company with all 5 features inside a single repository (`marp-app-client-android`) and a single module (`app-elgoog`), using a scalable MVVM architecture
2. Splitting the Elgoog Android client app into multiple modules by features and layers inside the `marp-app-client-android` repository
3. Creation of client Android app for Macrosoft company inside the same `marp-app-client-android` repository, reusing the modules from previous step
4. Extraction of shared modules from `marp-app-client-android` repository to individual repositories
5. Creation of the admin Android app for Elgoog company in another `marp-app-admin-android` repository, reusing artifacts from repositories created in previous steps
6. Creation of the client iOS app for Elgoog in `marp-app-client-ios` repository, reusing shared business logic from repositories created in previous steps

A diagram detailing how all repositories and modules of MARP come together in its final form is available in [Miro](https://miro.com/app/board/uXjVPllXa5A=/?share_link_id=460483441996)

## 📜 License

This project is licensed under the Apache License, Version 2.0 - see the [LICENSE.md](https://github.com/bogdanzurac/marp-app-client-android/blob/main/LICENSE) file for details
