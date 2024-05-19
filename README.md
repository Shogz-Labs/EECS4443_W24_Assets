# LE/EECS4443: Mobile User Interfaces (Winter 2024)

> [!WARNING]  
> As of **May 19, 2024**, this repository is no longer being maintained.
> It is strongly advised that you only use this content as a reference moving forward.
>
> It has been a great pleasure being your TA for the term. I wish you all the best in your future endeavours 😄👋.


Welcome to LE/EECS 4443 (Section M)! The goal of this repository is to help students familiarize themselves with Android Studio by providing additional learning materials, resources, and demo programs. 
Please feel free to add any other helpful resources by making a pull request! 
## Helpful Resources
1. Course-Related Materials
     - [Lecture Programs / Lab Files](https://github.com/yorku-ease/EECS4443-Demos)
     - [TA Slides](https://github.com/stoyonaga/EECS4443_W24_Assets/tree/main/TA%20Slides)
     - [TA Demos](https://github.com/stoyonaga/EECS4443_W24_Assets/tree/main/TA%20Demos)
2. Video Tutorials
    - [Caleb Curry's 4 Hour Introduction to Android Studio](https://www.youtube.com/watch?v=tZvjSl9dswg&themeRefresh=1)
3. Documentation & Cheat Sheets
   - [Android Documentation](https://developer.android.com/docs) 
   - [Android Studio Keyboard Shortcuts (i.e., Autocomplete)](https://developer.android.com/studio/intro/keyboard-shortcuts)
4. Textbooks & Miscellaneous References
   - [Android Programming: The Big Nerd Ranch Guide (3rd)](https://www.amazon.ca/Android-Programming-Nerd-Ranch-Guide/dp/0134706056)
   - [Introduction to Java Programming & Data Structures Comprehensive Version (11th)](https://www.pearson.com/store/p/introduction-to-java-programming-and-data-structures-comprehensive-version-ebook-global-edition/GPROG_A101708554311_learnernz-availability/9781292221892)
## Frequently Asked Questions / Troubleshooting
### Installing Android Studio (Week 1)
Please refer to the [Week 1 slides](https://github.com/stoyonaga/EECS4443_W24_Assets/blob/main/TA%20Slides/Week%201%20-%20Introduction.pdf) for a comprehensive set of resources and step-by-step instructions. Additionally, I will be able to help you over Slack or during the lab session 😄
### Lab 1 FAQ
1. I'm getting errors when I import Demo_Android! What should I do?
     - Firstly, make sure that you've installed the lab from the right place! You should download the most up-to-date Android apps from [the course GitHub page!](https://github.com/yorku-ease/EECS4443-Demos).
     - If your errors involve the R package, please ensure that you **Sync Project with Gradle Files.**
     - The slogan of debugging in Android Studio is: ``Sync to Project, Rebuild Project, Clean Project, Invalidate Caches, etc., ``
2. How should I be documenting my code? To what extent?
     - Great question, you should be writing Javadoc for your helper methods and single-line and/or block comments for minor edits
     - You should be adding comments in your XML file. These are done by leveraging the following syntax:
       <br>
       ``<!-- This is a comment in your XML file!! 😎 -->`` 
### Lab 2 FAQ
1. I'm having trouble interpreting the background requirement. What exactly am I supposed to do?
     - The entire background of the application should be dark blue (or really, any colour of your choice). The point is that it should be one solid gradient.
     - Please refer [here](https://developer.android.com/develop/ui/views/theming/themes) for a hint!
2. My Views in my layout are being cropped when the app transitions from the vertical to horizontal layout!! What should I do!?
     - You will either need to re-arrange the views in your horizontal view or use a [ScrollView](https://developer.android.com/reference/android/widget/ScrollView). To be specific, you should make the ScrollView the topmost parent that encapsulate the LinearLayout. 
### Lab 3 FAQ
1. Can you explain the Zoom-In? I am a bit confused.
     - The Zoom-In function needs to be contextualized with relation to where you tap on the image. For example, if you tap on the upper right corner, it should expand the image ($\times 3$) and shift it to the left so that the image is centered around the tapped region.
### Lab 4 FAQ
1. How can I keep track of In-Path Time?
     - Try to think of the game logic first. If the ball is in the path, keep a running tab using the [Apache Commons StopWatch object](https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/time/StopWatch.html).
     - There are of course other ways to go about doing this such as doing scratchpad arithmetic with with the System [currentTimeMillis()](https://docs.oracle.com/javase/8/docs/api/java/lang/System.html#currentTimeMillis--) method.
### Lab 5 FAQ
1. I'm just lost in general. Where do I start?
     - I strongly encourage you to take a look at [Demo_GridView](https://github.com/yorku-ease/EECS4443-Demos/tree/main/Demo_GridView). A majority of the code required to complete the lab can be used from here with citation.
     - You can also refer to [Demo_Ink](https://github.com/yorku-ease/EECS4443-Demos/tree/main/Demo_Ink) to figure out how to share the images using the image sharing option. 
  

