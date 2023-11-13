# Card-Match-Game

## Description
- Simple card matching desktop app where you click two cards and hope they match. 
- Matching pairs of cards will stay face-up and no longer be selectable. 
- Non-matching pairs of cards will flip back over, hope you remember what symbols they had and where they are located!
- The number of cards on the "board" can be changed according to user input before the game starts (with some limitations). 
- <i>note: there are currently only 8 unique card symbols implemented, games that contain more than 16 cards will have more than 1 pairs of cards with certain symbols</i>

### Technologies
Built entirely in Java, utilizing the Java Swing library and Maven project framework

### Future Features
- A leaderboard: ability to track the least amount of attempts taken to win a game
- More unique card symbols
- Card flash animation when selecting a pair of matching cards

## How to run
1) Clone repository

2) Ensure Java is installed on your system

3) Open a command line and cd into the repository folder `/Card-Match-Game/`

4) run the command `mvnw package` to compile the .jar file

5) cd into `/Card-Match-Game/target/`

6) Run: `java -jar Memory-Game-1.0.jar`