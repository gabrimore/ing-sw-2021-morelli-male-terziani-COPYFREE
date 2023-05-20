![masterOfReinassence](https://user-images.githubusercontent.com/72922439/124121796-9de52100-da75-11eb-8bc1-ee08380856c4.png)


Masters of Renaissance
License: Cranio creations


A family strategy game for 1-4 players in the acclaimed world of Lorenzo il Magnifico.

In Masters of Renaissance, you are an important citizen of Florence and your goal is to increase your fame and prestige. 
Take resources from the market and use them to buy new cards. Expand your power both in the city and in the surrounding territories! 
Every card gives you a production power that transforms the resources so you can store them in your strongbox. Try to use the leaders’ abilities to your advantage and don’t forget to show your devotion to the Pope!

Masters of Renaissance is a game with simple rules offering deep strategic choices of action selection and engine building. What will your best score be?









Masters of Renaissance game is the final test of "Software Engineering", course of "Computer Science Engineering" held at Politecnico di Milano (2020/2021).

Teacher Pierluigi San Pietro

Project specification
The project consists of a Java version of the board game Masters of Renaissance, made by Cranio creations.

The final version includes:

- initial UML diagram;
- final UML diagram, generated from the code by automated tools;
- working game implementation, which has to be rules compliant;
- source code of the implementation;
- source code of unity tests.
- sequence diagrams requested



The team:
- Morelli Gabriele
- Male Lorenzo
- Terziani Michele


About the requirements implemented, we've decided to implement all the game-specific requirements, and so the complete rules(the game is playable from 1 to 4 players).
Besides, we've implemented different game-agnostic requiremnts: the game can be played both utilizing the GUI or the CLI, there's the connection between multiple clients and the server, so the "socket" part, and finally two more advanced functionalities: the persistence of the game(persistenza) and the resilience to the disconnections(resilienza alle disconnessioni).
