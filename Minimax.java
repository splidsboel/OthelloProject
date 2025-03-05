import java.util.ArrayList;

public class Minimax implements IOthelloAI {
    ArrayList<Position> moves;

    public Minimax(GameState s){
        this.moves = s.legalMoves();

    }

    @Override
    public Position decideMove(GameState s) { // aka Minimax-search
        int player = s.getPlayerInTurn();


        //HHALLOOO
        Position position = new Position(player, player);
        return position;
    }

    public GameState maxValue(GameState s){
        if (moves.isEmpty()){
            return s;
        }

        int v = -2147483648; // lowest int in java


        for (Position position : moves) {
            
        }
        //ING
        return s;
    }

    public GameState minValue(GameState s){
        if (moves.isEmpty()){
            return s;
        }
        //SÅ KU VI VÆRE HER
        return s;
    }
}
