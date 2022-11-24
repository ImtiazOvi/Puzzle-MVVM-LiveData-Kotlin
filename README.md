# Puzzle-MVVM-LiveData-Kotlin
This is an practice project using MVVM and Live Data


##### mind orks copy #### https://blog.mindorks.com/what-is-flow-in-kotlin-and-how-to-use-it-in-android-project
https://proandroiddev.com/kotlin-flows-in-android-summary-8e092040fb3a


What is Flow in Kotlin and how to use it in Android Project?
What is Flow in Kotlin and How to use it in Android Project?
If you are an Android developer and looking to build an app asynchronously you might be using RxJava as it has an operator for almost everything. RxJava has become one of the most important things to know in Android.

But with Kotlin a lot of people tend to use Co-routines. With Kotlin Coroutine 1.2.0 alpha release Jetbrains came up with Flow API as part of it. With Flow in Kotlin now you can handle a stream of data that emits values sequentially.

In Kotlin, Coroutine is just the scheduler part of RxJava but now with Flow APIs coming along side it, it can be alternative to RxJava in Android
In this blog, we will see how Flow APIs work in Kotlin and how can we start using it in our android projects. We will cover the following topics,

What is Flow APIs in Kotlin Coroutines?
Start Integrating Flow APIs in your project
Builders in Flows
Few examples using Flow Operators.
Let's discuss them one by one.

What is Flow APIs in Kotlin Coroutines?
Flow API in Kotlin is a better way to handle the stream of data asynchronously that executes sequentially.

So, in RxJava, Observables type is an example of a structure that represents a stream of items. Its body does not get executed until it is subscribed to by a subscriber. and once it is subscribed, subscriber starts getting the data items emitted. Similarly, Flow works on the same condition where the code inside a flow builder does not run until the flow is collected.

Start Integrating Flow APIs in your project
Let us create an android project and then let's start integrating the Kotlin Flow APIs.

Step 01.
Add the following in the app's build.gradle,

implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3"
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.3"
and in the project's build.gradle add,

classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.61"
Step 02.
In MainActivity's layout file let's create a UI that will have a button.

<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout 
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">


    <Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:layout_constraintBottom_toBottomOf="parent"
        android:text="Launch Kotlin Flow"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
Step 03.
Now, let's begin the implementation of Flow APIs in MainActivity. In onCreate() function of Activity lets add two function like,

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setupFlow()
    setupClicks()
}
Here, setupFlow() is the function where we will define the flow and setupClicks() is the function where we will click the button to display the data which is emitted from the flow.

We will declare a lateinit variable of Flow of Int type,

lateinit var flow: Flow<Int>
Step 04.
Now, in setupFlow() I will emit items after 500milliseconds delay.

fun setupFlow(){
    flow = flow {
        Log.d(TAG, "Start flow")
        (0..10).forEach {
            // Emit items with 500 milliseconds delay
            delay(500)
            Log.d(TAG, "Emitting $it")
            emit(it)

        }
    }.flowOn(Dispatchers.Default)
}
Here,

We will emit numbers from 0 to 10 at 500ms delay.
To emit the number we will use emit() which collects the value emitted. It is part of FlowCollector which can be used as a receiver.
and, at last, we use flowOn operator which means that shall be used to change the context of the flow emission. Here, we can use different Dispatchers like IO, Default, etc.
flowOn() is like subscribeOn() in RxJava
Step 05.
Now, we need to write setupClicks() function where we need to print the values which we will emit from the flow.

private fun setupClicks() {
    button.setOnClickListener {
        CoroutineScope(Dispatchers.Main).launch {
            flow.collect {
                Log.d(TAG, it.toString())
            }
        }
    }
}
When we click the button we will print the values one by one.

Here,

flow.collect now will start extracting/collection the value from the flow on the Main thread as Dispatchers.Main is used in launch coroutine builder in CoroutineScope
Now, the screen looks like,
What is Flow in Kotlin and How to use it in Android Project?
And the output which will be printed in Logcat is,
D/MainActivity: Start flow
D/MainActivity: Emitting 0
D/MainActivity: 0
D/MainActivity: Emitting 1
D/MainActivity: 1
D/MainActivity: Emitting 2
D/MainActivity: 2
D/MainActivity: Emitting 3
D/MainActivity: 3
D/MainActivity: Emitting 4
D/MainActivity: 4
D/MainActivity: Emitting 5
D/MainActivity: 5
D/MainActivity: Emitting 6
D/MainActivity: 6
D/MainActivity: Emitting 7
D/MainActivity: 7
D/MainActivity: Emitting 8
D/MainActivity: 8
D/MainActivity: Emitting 9
D/MainActivity: 9
D/MainActivity: Emitting 10
D/MainActivity: 10
As you can see Flow starts only when the button is clicked as it prints Start Flow and then starts emitting. This is what I meant by Flows are cold.

Let's say we update the setupFlow() function like,

private fun setupFlow() {
    flow = flow {
        Log.d(TAG, "Start flow")
        (0..10).forEach {
            // Emit items with 500 milliseconds delay
            delay(500)
            Log.d(TAG, "Emitting $it")
            emit(it)
        }
    }.map {
        it * it
    }.flowOn(Dispatchers.Default)
}
Here you can see we added map operator which will take each and every item will square itself and print the value.

Anything, written above flowOn will run in background thread.

Builders in Flow
Flow builders are nothing but the way to build Flows. There are 4 types of flow builders,

flowOf() - It is used to create flow from a given set of values. For Eg.
flowOf(4, 2, 5, 1, 7).onEach { delay(400) }.flowOn(Dispatchers.Default)
Here, flowOf() takes fixed values and prints each of them after a delay of 400ms. When we attach a collector to the flow, we get the output

D/MainActivity: 4
D/MainActivity: 2
D/MainActivity: 5
D/MainActivity: 1
D/MainActivity: 7
asFlow() - It is an extension function that helps to convert type into flows. For Eg,
(1..5).asFlow().onEach{ delay(300)}.flowOn(Dispatchers.Default)
Here, we converted a range of values from 1 to 5 as flow and emitted each of them at a delay of 300ms. When we attach a collector to the flow, we get the output,

D/MainActivity: 1
D/MainActivity: 2
D/MainActivity: 3
D/MainActivity: 4
D/MainActivity: 5
The output is the number being printed from 1 to 5, whichever were present in the range.

flow{} - This example has been explained in the Android Example above. This is a builder function to construct arbitrary flows.
channelFlow{} - This builder creates cold-flow with the elements using send provided by the builder itself. For Example,
channelFlow {
    (0..10).forEach {
        send(it)
    }
}.flowOn(Dispatchers.Default)
This will print,

D/MainActivity: 0
D/MainActivity: 1
D/MainActivity: 2
D/MainActivity: 3
D/MainActivity: 4
D/MainActivity: 5
D/MainActivity: 6
D/MainActivity: 7
D/MainActivity: 8
D/MainActivity: 9
D/MainActivity: 10
flowOn() is like subscribeOn() in RxJava
Now, finally, let's discuss how to use an operator of Flow in your project

Zip Operator
If you remember from above Android Example, we have two functions setupFlow() and setupClicks(). We will modify both functions in MainActivity.

First, we will declare two lateinit variables of Flow of String type,

lateinit var flowOne: Flow<String>
lateinit var flowTwo: Flow<String>
Now, the two functions setupFlow() and setupClicks() will be called in onCreate() of MainActivity.

override fun onCreate(savedInstanceState: Bundle?) {
    ...
    setupFlow()
    setupClicks()

}
and in the setupFlow() we will initialize two flows to the two variables,

private fun setupFlow() {
    flowOne = flowOf("Himanshu", "Amit", "Janishar").flowOn(Dispatchers.Default)
    flowTwo = flowOf("Singh", "Shekhar", "Ali").flowOn(Dispatchers.Default)

}
and in setupClicks() we will zip both the flows using zip operator,

private fun setupClicks() {
    button.setOnClickListener {
        CoroutineScope(Dispatchers.Main).launch {
            flowOne.zip(flowTwo)
            { firstString, secondString ->
                "$firstString $secondString"
            }.collect {
                Log.d(TAG, it)
            }
        }
    }
}
Here,

when the button is clicked the scope is launched.
flowOne is zipped with flowTwo to give a pair of values that we have created a string and,
then we collect it and print it in Logcat. The resulting output will be,
D/MainActivity: Himanshu Singh
D/MainActivity: Amit Shekhar
D/MainActivity: Janishar Ali
Note: Consider if both flows doesn't have the same number of item, then the flow will stop as soon as one of the flow completes.
To checkout the project , click here

Happy learning.

Team MindOrks :)
