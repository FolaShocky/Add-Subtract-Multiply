# Add-Subtract-Multiply
Note: In Android development, any of the various screens/displays that comprise an application is known as an activity; it is by this term that I refer to them over the duration of this document.

## Overview
This repository contains code from an android application I developed named Add-Subtract-Multiply, which has undergone several iterations. As part of the application, an algorithm is implemented, enabling the number of questions set for the player to be directly proportional to their time limit; this is applicable to the determination of the highest score and the time allotted to the player. Regarding time, if for example, the player decided chose 2 minutes, they would have 40 questions; whereas, if they decided to set it to 5, they would have 100 questions. 

## Main Activity
From here, players can opt to select a particular series of question to answer or to view their high scores.

![MainActivity](https://lh3.googleusercontent.com/OxVzbmQGBpbMPNo2sXbOgJiYeFHJbKlPxk6CztxcSKfx9C6GUVyV1HyVfJc_pkKHoQ=w720-h310-rw)

## Play Activity
This is the activity where the user actually plays the game. Prior to the display of other widgets, a text field showing a 3-second countdown is shown; afterwards, multiple widgets used to show the current question number, time remaining and question to answer are displayed.

![PlayActivity](https://lh3.googleusercontent.com/0e5AOk1RtiUOQHB8CDXLS8jWnj7VkNAWdMGjFCRGqdcXrpp62odNwOyKbrf-FNmKHQ=w1228-h647-rw)

## Difficulty Activity
This activity allows the player to select the game's difficulty.

## Options Activity
Here, the players is able to select an option which determines both the time they are granted, as well as the number of questions they will be required to answer.

![OptionsActivity](https://lh3.googleusercontent.com/VK7rpBMRu_EUDVg9nmHcD4OZr_w-4rsutTHC3Lx_Cxvp-_Ap4MXFKgoMMVIRydWWhQ=w720-h310-rw)

## Results Activity
This activity's display will vary depending on whether or not the player attained a high score; in the event that they did, they are prompted to supply a name. 

![ResultsActivity](https://lh3.googleusercontent.com/LivoW2u9DW4Th3meqnG2AH6gkac1Eybo9_prnbM76OEekV9U50GI0dyDky18Wh-63To=w720-h310-rw)

## Score Activity
High scores are shown here. As well as the aforementioned, an animation is displayed, showing the operator in question (question marks are used for the "Random" category).

![ScoreActivity](https://lh3.googleusercontent.com/uzzrVWXfeuZkfrZb1z-XfyalfHx8EgkltYBz4Bq7phRL-264xR8tNlDqe4YPQHsA1yo=w720-h310-rw)
