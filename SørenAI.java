public class SørenAI implements IOthelloAI {
    
    @Override
    public Position decideMove(GameState s) {
        Minimax minimax = new Minimax(s, 7);
        return minimax.decideMove(s);
    }
}