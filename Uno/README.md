# Uno Project
### Contributors: 
##### Mohammad Zayyad: Responsible for Front End
##### Dev Goyal: Responsible for game structures and testing
##### Nathan Gerstein: Responsible for creating game runtime and flow

## Introduction
This project was made because we were interested in created a true and accurate Uno game that would be interactive and entertaining for the User

### Functionality
When the applet is loaded, the user simply needs to click legal cards to play them when it's their turn. There is a red dot indicating who's turn it is. If the user doesn't have any cards, they need to click to draw until they draw a legal card. Additionally, the user needs to click in order for each bot to put down a legal card. There are also special card functions, for more information look up Uno special cards as the cards implemented in game are true to the authentic rules. Lastly, the first person to have no cards remaining wins.

### Purpose
Many Uno games already exist. However most of them are either online or don't provide a nice graphical interface for the user. This project utilizes 2d graphics to make the game as simple and entertaining for the user as possible. Additionally, we learned a lot about Java events and combining Java input with JFrame to create an interactive and simple user experience.

## Technical Architecture
The Project follows a very simple Java class architecture. All Classes are basic Java classes inside of src/main/java. The specific structure defines a Player, Card, and Deck objects all with Uno specific enumerators defined to make definition more intuitive. The Renderer is the main class in project. This class utilizes SpecialCards, MapCards, and PaintCard as well as the classes before to create a graphical runtime that the user can play Uno on.

## Reproducible Installation
To play a game of Uno simply follow the steps:
1. Download the Repository in a Java JDK environment
2. Import the Uno Game as a Maven Project
3. Make sure all dependencies are satisfied (this heavily includes JFrame and Java.awt.Event)
4. Run the main in Renderer
5. Enjoy the Game!
