import java.util.ArrayList;

public class RandoAI implements IOthelloAI {

    @Override
    public Position decideMove(GameState s) {
        ArrayList<Position> moves = s.legalMoves();
        if ( !moves.isEmpty() ){
            int x = (int) (Math.random() * moves.size()); // Get random index
            return moves.get(x); // Return a random move
        } else {
			return new Position(-1,-1);
        }
	}
}
