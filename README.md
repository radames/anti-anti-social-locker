####This is an Android Studio Version and English translation tentative.


I'm exploring alternative ways to lock the Android phone via code for an ongoing project

===
English translated by Google
ScreenLock-Android
==================

Another unique way to unlock the lock screen software

#Alpha 4.0.1
    Why write this log mainly do? Because there are different effects for different versions of the lock screen, the main is my code actually is not a problem
    The method is to lift the system lock screen keyguard manager, regardless of version, either millet or android2.X 4.0 4.1 4.2 or more, is now
    No problem, of course, because it is despatched, so some future version may useless, then there is a method which is now several city
    Pitch lock screen software with it, let the user to cancel their lock screen, on the line.
    Of course, another very important issue is the shielding home key, of course, this is resolved, the main thing is that locklayer use of,
    The problem of course is limited, first you have to apply for permission, 4.0 & 4.1 you can directly apply for permission to use, but there are more than 4.2
    The main problem is that users must manually give it permission, the next rights management software and the permission to add items that trust, you can, on millet
    The permission name is allowed to open the floating window, android is similar, as long as the key permissions that allow on the line, can be the perfect home by masked
    Key, I think the market is like this lock screen.

#Alpha 4.0.0
    Increase the optimization start lock screen, start faster
    Increase the key listener, prohibit off other bad keys, such as home button
    However, the existence of any problem that might occur toast sometimes show the wrong time, seemingly due to lock out
    home button shield problems, solutions, modeled on the wording LockLayer class, add a privilege
    <Uses-permission android: name = "android.permission.SYSTEM_ALERT_WINDOW" />
    While adding class LockLayer, last added lockLayer the logic code lock () unlock ()

#Alpha 3.0.0
    Implements almost all functions, change the UI and some logic, increased interactivity

#Alpha 2.0.1
    ----

#Alpha 2.0.0
    To achieve the most simple framework, built a gesture can monitor and communicate between the last two documents by the handler
    A process, so now the functions are:
    1.gestureListener achieve gesture listener and by handler passes the data to be processed inside Screen RelativeLayout
    2.RelativeLayout realized related listens obtained depending on the direction, the next version to do is to listen for this event

# Beta 1.0
    It implements a simple lock screen, but there is no logic to achieve unlocking
