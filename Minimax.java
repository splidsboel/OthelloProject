import java.util.ArrayList;

/**
 * Implements the Minimax algorithm with Alpha-Beta pruning to determine the best move in Othello.
 * The AI evaluates moves based on token count and board position utility.
 */
public class Minimax implements IOthelloAI {
    private int depthLimit;
    private Utility utilityTable;

    /**
     * Constructs a Minimax AI with a given depth limit.
     *
     * @param s The initial game state.
     * @param depthLimit The maximum search depth for Minimax.
     */
    public Minimax(GameState s, int depthLimit){
        this.depthLimit = depthLimit;
        this.utilityTable = new Utility(s);
    }

    /**
     * Determines the best move using Minimax with Alpha-Beta pruning.
     *
     * @param s The current game state.
     * @return The best position for the AI's move.
     */
    @Override
    public Position decideMove(GameState s) { 
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

    /**
     * Evaluates the maximum possible utility value for the AI player.
     * This function recursively calls `minValue()` to explore possible opponent responses.
     * Uses Alpha-Beta pruning to optimize the search.
     *
     * @param s The current game state.
     * @param player The AI player (1 or 2).
     * @param depth The remaining search depth.
     * @param alpha The best already explored option for the maximizing player.
     * @param beta The best already explored option for the minimizing player.
     * @return The maximum utility value for the given state.
     */
    public int maxValue(GameState s, int player, int depth, int alpha, int beta){
        if (depth == 0 || s.isFinished()) {
            return evaluateBoard(s, player);
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
                return value; // Beta cutoff
            }

            alpha = Math.max(alpha, value);
        }
        return value;
    }

    /**
     * Evaluates the minimum possible utility value for the opponent.
     * This function recursively calls `maxValue()` to explore possible AI responses.
     * Uses Alpha-Beta pruning to optimize the search.
     *
     * @param s The current game state.
     * @param player The AI player (1 or 2).
     * @param depth The remaining search depth.
     * @param alpha The best already explored option for the maximizing player.
     * @param beta The best already explored option for the minimizing player.
     * @return The minimum utility value for the given state.
     */
    public int minValue(GameState s, int player, int depth, int alpha, int beta){
        if (depth == 0 || s.isFinished()) {
            return evaluateBoard(s, player); 
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
                return value; // Alpha cutoff
            }

            beta = Math.min(beta, value);
        }
        return value;
    }

    /**
     * Evaluates the utility of a given game state.
     * The evaluation considers both:
     * - The number of tokens controlled by the AI.
     * - The positional value of occupied board positions based on `Utility`.
     *
     * @param s The current game state.
     * @param player The AI player (1 or 2).
     * @return The heuristic value of the board state.
     */
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

    /**
     * Sorts legal moves in descending order based on their strategic value.
     * This helps Alpha-Beta pruning by prioritizing strong moves earlier.
     *
     * @param moves The list of available moves.
     */
    private void sortMovesByValue(ArrayList<Position> moves) {
        moves.sort((p1, p2) -> {
            int value1 = utilityTable.moveUtility(p1);
            int value2 = utilityTable.moveUtility(p2);
            return Integer.compare(value2, value1); // Higher value first
        });
    }
}
