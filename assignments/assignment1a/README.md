# Java Threads and Synchronizers (Assignment 1 - part a)

## Overview of the Assignment
In the first part of this assignment, you will use the Java Thread class and Runnable interface to develop an Android app containing a resource manager that restricts the number of Beings from Middle-Earth who can concurrently gaze into a fixed number of Palantiri. If you're not yet a fan of Tolkein's Lord of the Ring's trilogy, you can learn more about Palantiri [here][1].

## Assignment Structure and Functionality
The assignment is split into the following three directories:
 - [app/src/main][d1], which conains the skeletons you'll need to fill in, as described below. You'll need to open this project in Android Studio. 
 - [app/src/test][d2], which is a JUnit test that exercises all the PalantiriManager features and can be used to help test the functionality created in the assignment.
 - [app/src/androidTest][d3], which is an Android Studio test that runs your app automatically. You'll automatically import this project into Android Studio.

To compile this code you need to use the provided Android Studio project. You can run this project by clicking the green "Run 'app'" button in the Android Studio IDE, which should automatically select an Android Emulator to run, assuming you have one created. If you do not have one created you can create it by clicking on the "AVD Manager" button in the Android Studio IDE.   
 
## Program Description and "TODO" Tasks
In the `app/src/main/java/edu/vandy` directory you'll find a number of files, all of which you should read. In particular, this Android program is structured in accordance with the Model-View-Presenter (MVP) pattern. The classes in the View layer are provided to you. The main `PalantiriActivity` first prompts the user to enter various configuration parameters, such as the number of `Palantiri` and `Beings`. It then starts the `GazingSimulationActivity`, which initializes the Presenter layer and then starts the Presenter layer's processing logic. The bulk of the concurrent processing is performed by the Presenter and Model layers. If you correctly complete the implementation of skeletons in the Presenter layer the Palantiri app should successfully run to completion without throwing any exceptions.

In addition to reading the existing code, you'll need to modify several files containing the skeleton Java code by completing the "TODO - You fill in here" tasks to provide a working solution. DO NOT CHANGE THE OVERALL STRUCTURE OF THE SKELETON - just fill in the "TODO.." portions!!! In particular, you'll need to do the following:

- Finish implementing the class methods defined by the Presenter layer residing in `app/src/main/java/edu/vandy/presenter/PalantiriPresenter.java`. You'll need to complete the (1) `beginBeingThreads()` method to create/start a Thread and (Being)Runnable for each Being, which concurrently acquires a Palantir from the Model layer and gazes into it for a certain amount of time, (2) `waitForBeingThreads()` method to start a thread that waits for all the Being threads to finish and then inform the View layer that the simulation is done, and (3) `shutdown()` method to interrupt all the threads when an error occurs. Java Threads and Runnables are described [here][y1] and [here][y2].
- Finish implementing the class methods defined by the Presenter layer residing in app/src/main/java/edu/vandy/presenter/BeingRunnable.java. Everyone needs to complete the gazeIntoPalantir() method. Grad students have some additional work do to in this file, as discussed below.

Your app will be considered "correct" if it successfully completes an Espresso-based simulation of N Palantiri and M Beings without crashing or throwing any exceptions. The Palantir and Beings are color coded to represent their state:
- __Beings__  - Yellow means the Being is idle, Red means it's waiting to acquire a Palantir, Green means it's gazing into a Palantir, and Purple means the gazing was interrupted due to the lease expiring.
- __Palantiri__ - Gray means the Palantir is inactive, Green means it is available for use, and Red means it is in use (*i.e.*, a Being is currently gazing into it.

A "correct" simulation should restrict the number of gazing Beings to the number of Palantiri. In other words, if there are four Palantiri in the simulation, then only four Being dots should ever be Green on the screen at a time.

## Additional "TODO" Tasks for Graduate Students
Graduate students (or students who are taking this course for graduate credit) must perform the following additional tasks:
- In `app/src/main/java/edu/vandy/presenter/BeingRunnable.java` there are two unimplemented methods: `incrementGazingCountAndCheck()` and `decrementGazingCount()`. You must use the Java `AtomicInteger` class to implement those method as defined in the comments to double check that the `PalantirManager ` is properly limiting the number of threads that have a Palantir. `AtomicInteger` and `AtomicLong` are described [here][y3].
 
Undergraduates are welcome to implement these methods/features, but it's not required.

## Skeleton Code
Skeleton code for this assignment is available from my [GitHub][2] account. Once you've setup your [GitLab][3] account you can pull this skeleton code in your repository, read it carefully, and complete the "TODO" markers. The additional work required by graduate students is clearly marked.

[1]: http://en.wikipedia.org/wiki/Palantir
[d1]: app/src/main
[d2]: app/src/test
[d3]: app/src/androidTest
[y1]: https://www.youtube.com/watch?v=eY5E0o56gaw&list=PLZ9NgFYEMxp4tbiFYip6tDNIEBRUDyPQK&index=12
[y2]: https://www.youtube.com/watch?v=wpEeZUjTiS4&list=PLZ9NgFYEMxp4tbiFYip6tDNIEBRUDyPQK&index=13
[y3]: https://www.youtube.com/watch?v=cz1R-bnMkd0&list=PLZ9NgFYEMxp4KSJPUyaQCj7x--NQ6kvcX&index=40
[2]: https://github.com/douglascraigschmidt/CS892/tree/master/assignments/assignment1a/
[3]: https://about.gitlab.com/
