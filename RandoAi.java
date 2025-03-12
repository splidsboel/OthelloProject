import java.util.ArrayList;

public class RandoAi implements IOthelloAI {

    @Override
    public Position decideMove(GameState s) {
        ArrayList<Position> moves = s.legalMoves();
        int x = (int) Math.random();
        if ( !moves.isEmpty() )
			return moves.get(moves.size()-1);
		else
			return new Position(-1,-1);
	}
    
}
