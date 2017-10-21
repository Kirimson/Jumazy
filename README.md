# Jumazy

<h1>READ THIS PLEASE WHEN IMPORTING</h1>
This project is strange at best on how it all works. But after an hour of figuring out why it didnt work on my laptop, then it not working on my desktop after fixing my laptop. I've got this stuff down to an art.
These instructions are for Eclipse only, as  thats what I use. For netbeans, I think you just open the gradle.build and you *might* be fine, havent tested this however.

1. Clone/pull/whatever to get the repo onto your machine
2. File -> Import
3. Select Gradle -> Exisitng Gradle Project
4. Find the root folder of the project (The "Jumazy" folder, not any the "core" or "desktop" folders)
5. Click Next, let the page load, it may take a while!
6. Click Finish

 The project is now imported, but it WILL NOT like anything in this state. Now we need to fix some things...

Firstly, It's trying to use JRE 1.6. This JRE doesnt allow switch statements with Strings, among other things, lets chnage that now. Notw: These steps need to be done to the projects "Jumazy-core" and "Jumazy-desktop" but NOT "Jumazy"
1. Right click project folder: Build Path -> Configure Build Path...
2. Libraries tab, double click the JRE
3. Select JRE 1.8 from the dropdown list
4. (for "Jumazy-desktop" only) select the Source tab, click Add Folder, select "assets"

Now, hopefully, it'll all work! If not, I might have missed something, send me a message if you can't sort it, and I'll try and think of what I forgot