# Blackjack Game

<img width="344" alt="Screenshot 2024-06-20 at 11 54 25 PM" src="https://github.com/JosephS1618/blackjack-game/assets/46387707/302433e8-1e72-4976-9cb4-054f9732168d">

<img width="345" alt="Screenshot 2024-06-20 at 11 55 08 PM 1" src="https://github.com/JosephS1618/blackjack-game/assets/46387707/a0d1c6a8-f0f9-4fcb-a2df-7528595a17aa">


**About The Project**:
- _What will the application do?_
  - My application is a Java based blackjack game that can be played from the terminal or from a GUI. The game will keep track of the player's virtual money, as well as useful statistics such as win rate and play accuracy (according to the strategy chart).
- _Who will use it?_
  - Anyone from a student to a professional casino company. Anyone interested in playing or learning blackjack in their free time! 
- _Why is this project of interest to you?_
  - I'm an avid black jack player and I loved watching the movie 21 (even though I can't card count). 

**User Stories**
- As a user, I want to be able to select a game difficulty that adds up to six decks to the game (each added deck increases the difficulty).
- As a user, I want to be able to view a list of past games and their outcomes, showing cash gained/lost.
- As a user, I want to be able to see overall player statistics such as tracking virtual money, win rate, play accuracy.
- As a user, I want to be able to have the option of resetting their progress (cash, statistics), or automatically doing so when they run out of money.
- As a user, I want to be able to save the state of the game that they are currently playing (score and stats) or have the option to not save.
- As a user, I want to be able to have the option to retrieve the last saved game stats and scores from file. 
- As a user, I want to be able to view the game stats in a clear game UI
- As a user, I want to have buttons that allow me to click save and quit at all times. 


## Instructions:
- Add an X by playing the game. Select the play on the sidebar and press play.
- View the Y by selecting the stats tab and seeing the list of games played.
- Save and load the Y (game outcomes) by selecting save, followed by load. 
- Select the update button in the stats tab to see the visual component (showing list of game outcomes)
- You can also select to view specifics of the X (game outcomes). select the "Get wins" and "get losses" buttons in the stats panel to get the number of wins and losses

Sample of console logs:
* WindowListener method called: windowClosing.
* Sat Apr 06 19:39:55 PDT 2024
* Started a new game
* Sat Apr 06 19:39:57 PDT 2024
* Game ended
* Sat Apr 06 19:39:57 PDT 2024
* Added a win. Win total: 1.0
* Sat Apr 06 19:39:58 PDT 2024
* Started a new game
* Sat Apr 06 19:40:04 PDT 2024
* Game ended
* Sat Apr 06 19:40:04 PDT 2024
* Added a loss. loss total: 1.0

Events are logged each time a game is played, when the game ends, and also 
when a win or loss occurs. 

My design for the game shown in the UML diagram shows a central class in the 
ui package called Game that is called from the Main class. This class took 
control of all the primary aspects of the games function directly from the 
ui package, including printing to the console and displaying the GUI.

While I was satisfied with how the game functioned, I thought that I could have 
further improved my design by refactoring some of my classes. For instance, 
the Player and Dealer classes shared some of the same methods but also had 
a few methods that were unique to each other. With more time, I could have made 
them both extend a shared parent class to abstract away the duplicated methods 
while also leaving room for unique methods to be implemented by each class. 

Another design choice that I would refactor is incorporating the JPanel 
BoardPanel directly into the Game class. While all the other tabs in the sidebar extended
the Tab class, the BoardPanel where the main game was played was left in the Game class.
I could have refactored BoardPanel into 
its own unique tab by having it extend the abstract class Tab. However, due to a lot of the 
functionality of the BoardPanel being tied directly into the Game functionalities,
I chose to follow the simple route by directly adding it to the JFrame 
in the Game class. 

My final refactoring choice would be to remove or change the MessagePrinter class.
I found the class to be quite redundant, as it performed 
some of the tasks that should have been dedicated to the primary Game class. 
I chose to include it as it helped simplify printing messages from 
the model package. However, with more time I would have refactored its 
methods directly into the Game class. 
