import java.util.ArrayList;

public class Minimax implements IOthelloAI {

    public Minimax(GameState s){
    }

    @Override
    public Position decideMove(GameState s) { // aka Minimax-search
        // if (s.legalMoves().isEmpty()) {
        //     return null;  // If no moves are available, return early
        // }

        int player = s.getPlayerInTurn();
        int value = Integer.MIN_VALUE;
        Position bestMove = null;
        int depthLimit = 5;

        for (Position move : s.legalMoves()) {
            GameState nextState = new GameState(s.getBoard(), s.getPlayerInTurn());
            nextState.insertToken(move);

            int moveValue = minValue(nextState, player, depthLimit);

            if (moveValue > value || bestMove == null) {
                value = moveValue;
                bestMove = move;
            }
        }
        return bestMove;// != null ? bestMove : s.legalMoves().get(1); // Ensure it never returns null

    }

    public int maxValue(GameState s, int player, int depth){
        if (depth == 0 || s.isFinished()) {
            return evaluateBoard(s, player);  // Use an evaluation function at depth limit
        }
        int bestMoveUtil = Integer.MIN_VALUE;

        for (Position position : s.legalMoves()) {
            GameState nexState = new GameState(s.getBoard(), s.getPlayerInTurn());
            nexState.insertToken(position);

            int moveValue = minValue(nexState, player, depth -1);
            bestMoveUtil = Math.max(bestMoveUtil, moveValue);
        }
        return bestMoveUtil;
    }

    public int minValue(GameState s, int player, int depth){
        if (depth == 0 || s.isFinished()) {
            return evaluateBoard(s, player);  // Use evaluation function instead of full recursion
        }
        
        int bestMoveUtil = Integer.MAX_VALUE;

        for (Position position : s.legalMoves()) {
            GameState nextState = new GameState(s.getBoard(), s.getPlayerInTurn());
            nextState.insertToken(position);

            int moveValue = maxValue(nextState, player, depth -1);
            bestMoveUtil = Math.min(bestMoveUtil, moveValue);
        }
        return bestMoveUtil;
    }

    private int evaluateBoard(GameState s, int player) {
        int[] tokenCounts = s.countTokens();  // Get token counts for both players
        int playerTokens = (player == 1) ? tokenCounts[0] : tokenCounts[1]; // Current player's tokens
        int opponentTokens = (player == 1) ? tokenCounts[1] : tokenCounts[0]; // Opponent's tokens
    
        return playerTokens - opponentTokens;  // Higher score favors AI
    }
}
