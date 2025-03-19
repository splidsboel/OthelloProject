import java.util.ArrayList;

public class Minimax implements IOthelloAI {
    private int depthLimit;
    private Utility utilityTable;

    public Minimax(GameState s, int depthLimit){
        this.depthLimit = depthLimit;
        this.utilityTable = new Utility(s);
    }

    @Override
    public Position decideMove(GameState s) { // aka Minimax-search
        // if (legalMoves.isEmpty()) {
        //     return null;  // If no moves are available, return early
        // }

        ArrayList<Position> legalMoves = s.legalMoves();
        sortMovesByValue(legalMoves);

        int player = s.getPlayerInTurn();
        int value = Integer.MIN_VALUE;
        Position bestMove = null;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        for (Position move : legalMoves) {
            GameState nextState = new GameState(s.getBoard(), s.getPlayerInTurn());
            nextState.insertToken(move);

            int moveValue = minValue(nextState, player, depthLimit, alpha, beta);

            if (moveValue > value || bestMove == null) {
                value = moveValue;
                bestMove = move;
                alpha = Math.max(alpha, value);
            }
        }
        return bestMove;
    }

    public int maxValue(GameState s, int player, int depth, int alpha, int beta){
        if (depth == 0 || s.isFinished()) {
            return evaluateBoard(s, player);  // Use an evaluation function at depth limit
        }
        int value = Integer.MIN_VALUE;

        ArrayList<Position> legalMoves = s.legalMoves();
        sortMovesByValue(legalMoves);

        for (Position position : legalMoves) {
            GameState nexState = new GameState(s.getBoard(), s.getPlayerInTurn());
            nexState.insertToken(position);

            int moveValue = minValue(nexState, player, depth -1, alpha, beta);
            value = Math.max(value, moveValue);

            if(value >= beta){
                return value; // Beta cut-off
            }

            alpha = Math.max(alpha, value);
        }
        return value;
    }

    public int minValue(GameState s, int player, int depth, int alpha, int beta){
        if (depth == 0 || s.isFinished()) {
            return evaluateBoard(s, player);  // Use evaluation function instead of full recursion
        }
        
        int value = Integer.MAX_VALUE;

        ArrayList<Position> legalMoves = s.legalMoves();
        sortMovesByValue(legalMoves);

        for (Position position : legalMoves) {
            GameState nextState = new GameState(s.getBoard(), s.getPlayerInTurn());
            nextState.insertToken(position);

            int moveValue = maxValue(nextState, player, depth -1, alpha, beta);
            value = Math.min(value, moveValue);

            if(value <= alpha){
                return value; // Beta cut-off
            }

            beta = Math.min(beta, value);
        }
        return value;
    }

    // private int evaluateBoard(GameState s, int player) {
    //     int[] tokenCounts = s.countTokens();  // Get token counts for both players
    //     int playerTokens = (player == 1) ? tokenCounts[0] : tokenCounts[1]; // Current player's tokens
    //     int opponentTokens = (player == 1) ? tokenCounts[1] : tokenCounts[0]; // Opponent's tokens
    
    //     return playerTokens - opponentTokens;  // Higher score favors AI
    // }
    private int evaluateBoard(GameState s, int player) {
        Utility utilityTable = new Utility(s);
        int[] tokenCounts = s.countTokens();
        int playerTokens = (player == 1) ? tokenCounts[0] : tokenCounts[1];
        int opponentTokens = (player == 1) ? tokenCounts[1] : tokenCounts[0];
    
        int positionalScore = 0;
        int[][] board = s.getBoard();
    
        // Loop through board and sum up utility values for player's and opponent's tokens
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Position p = new Position(i, j);
                if (board[i][j] == player) {
                    positionalScore += utilityTable.moveUtility(p); // Add for AI player
                } else if (board[i][j] != 0) { // If opponent has a token there
                    positionalScore -= utilityTable.moveUtility(p); // Subtract for opponent
                }
            }
        }
    
        return (playerTokens - opponentTokens) * 10 + positionalScore;
    }

    // Sorts moves according to their strategic value for better alpha-beta pruning
    private void sortMovesByValue(ArrayList<Position> moves) {
        // Use the Utility class to asses moves
        moves.sort((p1, p2) -> {
            int value1 = utilityTable.moveUtility(p1);
            int value2 = utilityTable.moveUtility(p2);
            return Integer.compare(value2, value1); // Higher value first
        });
    }
}
