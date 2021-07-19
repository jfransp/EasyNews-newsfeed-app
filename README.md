# EasyNews: news feed app
A news feed app developed using Retrofit, Room, Paging 3, Dagger Hilt, Preferences DataStore, Coroutines, GSON, Glide, ViewBinding and several JetPack architecture
and navigation components, following Material Design guidelines, an MVVM architecture and using the NewsApi.org's HTTP REST API.
# Overview
The app utilizes a custom toolbar, with action buttons according to the screen. It also features a side navigation drawer, allowing the user to navigate to several
of the news sources offered by the NewsApi.org free plan access as well as setting configurations for country and category through a custom settings screen.

The main app environment has a bottom navigation view, allowing the user to navigate in the app main environment, between a breaking news screen, a screen with
articles saved by the user and a search screen that allows the user to search through the api's offered sources by both language and sort order. The article screen
allows the user to read the article on a web view and also navigate to the source's website.

Some extra implementations: error messages/handling, load state listeners, recyclerviews (with both rvadapters, listdapters and paging adapters), safe args, custom adapters
for navigation and preference setting, web view, share function, custom xml shapes and drawables and more.

Here you can check out a short demosntration video: https://www.youtube.com/watch?v=QPwg-CprzHk
# Architecture
<img src="https://raw.githubusercontent.com/jfransp/EasyNews-newsfeed-app/master/Flowchart%20Template.jpg" width="500" height="500" />



Credits:
Icons by Gregor Cresnar (www.flaticon.com/authors/gregor-cresnar)

Some ideas I've learned from Florian Walther, Phillip Lackner and Ryan M. Kay
