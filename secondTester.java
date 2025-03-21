public class secondTester implements IOthelloAI {
    
    @Override
    public Position decideMove(GameState s) {
        Minimax minimax2 = new Minimax(s, 2);
        return minimax2.decideMove(s);
    }
}