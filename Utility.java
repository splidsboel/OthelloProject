/**
 * The Utility class calculates position-based values for the Othello board.
 * It assigns utility scores to different board positions based on strategic importance.
 */
public class Utility {
    private int[][] utilityValue;

    /**
     * Constructs a Utility instance and initializes the utility value table.
     *
     * @param s The current game state from which the board dimensions are derived.
     */
    public Utility(GameState s){
        this.utilityValue = populate(s);
    }

    /**
     * Returns the utility value for a given position on the board.
     *
     * @param p The position on the board.
     * @return The utility score associated with the position.
     */
    public int moveUtility(Position p){
        return utilityValue[p.col][p.row];
    }

    /**
     * Generates a utility value matrix for the board based on strategic positioning.
     *
     * @param s The current game state.
     * @return A 2D array where each position is assigned a utility score.
     */
    public int[][] populate(GameState s){
        int n = s.getBoard().length;
        int[][] utilities = new int[n][n];

        // Define utility values
        int cornerValue = 10;
        int edgeValue = 5;
        int nearCornerPenalty = -5;
        int secondOuterRingPenalty = -3;
        int centerValue = 1;

        // Assign values based on position importance
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // Corners (highest value)
                if ((i == 0 && j == 0) || (i == 0 && j == n - 1) ||
                    (i == n - 1 && j == 0) || (i == n - 1 && j == n - 1)) {
                    utilities[i][j] = cornerValue;
                }
                // Adjacent to corners (dangerous positions)
                else if ((i == 0 && j == 1) || (i == 1 && j == 0) ||
                         (i == 0 && j == n - 2) || (i == 1 && j == n - 1) ||
                         (i == n - 1 && j == 1) || (i == n - 2 && j == 0) ||
                         (i == n - 1 && j == n - 2) || (i == n - 2 && j == n - 1)) {
                    utilities[i][j] = nearCornerPenalty;
                }
                // Second-most outer ring (only if board is at least 6x6)
                else if (n >= 6 &&
                         (i == 1 || j == 1 || i == n - 2 || j == n - 2) &&
                         !(i == 1 && j == 1) && !(i == 1 && j == n - 2) &&
                         !(i == n - 2 && j == 1) && !(i == n - 2 && j == n - 2)) {
                    utilities[i][j] = secondOuterRingPenalty;
                }
                // Edge positions (moderately valuable)
                else if (i == 0 || j == 0 || i == n - 1 || j == n - 1) {
                    utilities[i][j] = edgeValue;
                }
                // Center (least valuable, neutral)
                else {
                    utilities[i][j] = centerValue;
                }
            }
        }
        return utilities;
    }
}