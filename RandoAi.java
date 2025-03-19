import java.util.ArrayList;

/**
 * A simple AI that selects a random valid move.
 */
public class RandoAI implements IOthelloAI {

    /**
     * Chooses a random legal move from the available options.
     *
     * @param s The current game state.
     * @return A random valid move, or (-1, -1) if no moves are available.
     */
    @Override
    public Position decideMove(GameState s) {
        ArrayList<Position> moves = s.legalMoves();
        if ( !moves.isEmpty() ){
            int x = (int) (Math.random() * moves.size()); // Get random index
            return moves.get(x);
        } else {
			return new Position(-1,-1); // No valid moves
        }
	}
}
