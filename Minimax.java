import java.util.ArrayList;

public class Minimax implements IOthelloAI {
    private int depthLimit;
    private Utility utilityTable;
    private ArrayList<Position> legalMoves;

    public Minimax(GameState s, int depthLimit){
        this.depthLimit = depthLimit;
        ArrayList<Position> legalMoves = s.legalMoves();
        sortMovesByValue(legalMoves);
    }

    @Override
    public Position decideMove(GameState s) { // aka Minimax-search
        // if (legalMoves.isEmpty()) {
        //     return null;  // If no moves are available, return early
        // }

        utilityTable = new Utility(s);

        int player = s.getPlayerInTurn();
        int value = Integer.MIN_VALUE;
        Position bestMove = null;
        // int depthLimit = 5;
        int alpha = Integer.MAX_VALUE;
        int beta = Integer.MIN_VALUE;

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

    private int evaluateBoard(GameState s, int player) {
        int[] tokenCounts = s.countTokens();  // Get token counts for both players
        int playerTokens = (player == 1) ? tokenCounts[0] : tokenCounts[1]; // Current player's tokens
        int opponentTokens = (player == 1) ? tokenCounts[1] : tokenCounts[0]; // Opponent's tokens
    
        return playerTokens - opponentTokens;  // Higher score favors AI
    }

    // Sorterer træk efter deres strategiske værdi for bedre alpha-beta pruning
    private void sortMovesByValue(ArrayList<Position> moves) {
        // Brug Utility-klassen til at vurdere træk
        moves.sort((p1, p2) -> {
            int value1 = utilityTable.moveUtility(p1);
            int value2 = utilityTable.moveUtility(p2);
            return Integer.compare(value2, value1); // Højere værdi først
        });
    }
}
