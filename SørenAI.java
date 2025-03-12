public class SørenAI implements IOthelloAI {
    
    @Override
    public Position decideMove(GameState s) {
        Minimax minimax = new Minimax(s);
        return minimax.decideMove(s);
    }
}
