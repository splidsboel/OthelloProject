/**
 * An AI implementation that uses the Minimax algorithm for decision-making.
 */
public class SørenAI implements IOthelloAI {
    
    /**
     * Uses Minimax to determine the best move.
     *
     * @param s The current game state.
     * @return The best move determined by Minimax.
     */
    @Override
    public Position decideMove(GameState s) {
        Minimax minimax = new Minimax(s, 7);
        return minimax.decideMove(s);
    }
}