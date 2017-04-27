# Java Threads and Synchronizers (Assignment 1 - b)

## Overview of the Assignment
In this assignment, you will use the Java Thread, Runnable, Semaphore, and AtomicLong classes, as well as Java synchronized statements, to develop an Android app containing a resource manager that restricts the number of Beings from Middle-Earth who can concurrently gaze into a fixed number of Palantiri. If you're still not a fan of Tolkein's Lord of the Ring's trilogy, you can learn more about Palantiri [here][1].

## Assignment Structure and Functionality
The assignment is split into the following three directories:
- [app/src/main][d1], which conains the skeletons you'll need to fill in, as described below. You'll need to open this project in Android Studio.  
- [app/src/test][d2], which is a JUnit test that exercises all the PalantiriManager features and can be used to help test the functionality created in the assignment.
- [app/src/androidTest][d3], which is an Android Studio test that runs your app automatically. You'll automatically import this project into Android Studio.

To compile this code you need to use the provided Android Studio project. You can run this project by clicking the green "Run 'app'" button in the Android Studio IDE, which should automatically select an Android Emulator to run, assuming you have one created. If you do not have one created you can create it by clicking on the "AVD Manager" button in the Android Studio IDE.

## Program Description and "TODO" Tasks
In the `app/src/main/java/edu/vandy` directory you'll find a number of files, all of which you should read. In particular, this Android program is structured in accordance with the [Model-View-Presenter][2] (MVP) pattern. The classes in the View layer are provided to you. The main PalantiriActivity first prompts the user to enter various configuration parameters, such as the number of Palantiri and Beings. It then starts the `GazingSimulationActivity`, which initializes the Presenter layer and then starts the Presenter layer's processing logic. The bulk of the concurrent processing is performed by the Presenter and Model layers. If you correctly complete the implementation of skeletons in these layers the Palantiri app should successfully run to completion without throwing any exceptions.
 
In addition to reading the existing code, you'll need to modify several files containing the skeleton Java code by completing the "TODO - You fill in here" tasks to provide a working solution. DO NOT CHANGE THE OVERALL STRUCTURE OF THE SKELETON - just fill in the "TODO.." portions!!! In particular, you'll need to do the following:
- Finish implementing the class methods defined by the Presenter layer residing in `app/src/main/java/edu/vandy/presenter/PalantiriPresenter.java` and `app/src/main/java/edu/vandy/presenter/BeingRunnable.java` using your implementation from part a of this assignment.
- Finish implementing the Model layer in `app/src/main/java/edu/vandy/model/PalantiriManager.java`. You'll use a Java Semaphore, HashMap, and synchronized statements to mediate concurrent access to the Palantiri resources and keep track of which Palantiri are currently free and which are in use. Java Semaphores and synchronized statements are described [here][y1] and [here][y2], respectively.

Your app will be considered "correct" if it successfully completes an Espresso-based simulation of N Palantiri and M Beings without crashing or throwing any exceptions. The Palantir and Beings are color coded to represent their state:
- __Beings__ - Yellow means the Being is idle, Red means it's waiting to acquire a Palantir, Green means it's gazing into a Palantir, and Purple means the gazing was interrupted due to the lease expiring.
- __Palantiri__ - Gray means the Palantir is inactive, Green means it is available for use, and Red means it is in use (*i.e.*, a Being is currently gazing into it).

A "correct" simulation should restrict the number of gazing Beings to the number of Palantiri. In other words, if there are four Palantiri in the simulation, then only four Being dots should ever be Green on the screen at a time. 

## Additional "TODO" Tasks for Graduate Students
Graduate students (or students who are taking this course for graduate credit) must perform the following additional tasks:
- Use Java 8 features whenever possible in your implementation. These features include (but are not limited to) lambda expression, method references, and the forEach() method, which are described [here][y3]. We'll also cover these features in class.

Undergraduates are welcome to implement these methods/features, but it's not required.

## Skeleton Code
Skeleton code for this assignment is available from my [GitHub][3] account. Once you've setup your GitLab account you can pull this skeleton code in your repository, read it carefully, and complete the "TODO" markers. The additional work required by graduate students is clearly marked.

## Concluding Remarks
This assignment is designed to help you get familiar with writing multi-threaded programs using the Android Studio IDE, core Java threading and synchronizer mechanisms, and some Java 8 features (for graduate students or optionally for undergraduates). It doesn't require you to program any Android concurrency or communication frameworks, which we'll cover in upcoming assignments. I recommend you watch the videos listed above and look at the code to ensure you understand how to program Java threads and synchronizers. Naturally, we'll cover these topics in class, as well.

[1]: http://en.wikipedia.org/wiki/Palantir
[d1]: app/src/main
[d2]: app/src/test
[d3]: app/src/androidTest
[2]: https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter
[y1]: https://www.youtube.com/watch?v=UoaZTkot6-g&list=PLZ9NgFYEMxp4tbiFYip6tDNIEBRUDyPQK&index=20
[y2]: https://www.youtube.com/watch?v=g3rcTTF45cA&list=PLZ9NgFYEMxp4tbiFYip6tDNIEBRUDyPQK&index=25 
[y3]: https://www.youtube.com/watch?v=MPH4R4qAdrM&feature=youtu.be
[3]: https://github.com/douglascraigschmidt/CS892/tree/master/assignments/assignment1b/
