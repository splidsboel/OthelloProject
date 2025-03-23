# Othello Project

Implementation of minimax-algorithm with alpha-beta pruning for the game of Othello. 

Evaluation function currently contains a weighing between tokens gathered and positional state. This can be adjusted in the Minimax class. 

See Utility class for any adjustments to the weighing of what positions are important. 


## How to play
Files should be compiled, but if any issues, run:
```sh
javac *.java
``` 
To play run 
```sh
java Othello {player1} {player2} {boardSize}
```

- Player 1 can be "human" for human player or an AI client. 
- Player 2 has to be an AI client. 
- Board size can be any even integer>=4

## Available AIs:
- SørenAI - our implementation of Minimax 
- DumAI - incredibly stupid AI - picks the first move available
- RandoAI - picks moves randomly

## Authors: 
- Birk Bregendahl, bibr@itu.dk
- Niels Schjerbeck Lund, nlun@itu.dk
- Jakob Splidsboel Eg, jaeg@itu.dk

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details