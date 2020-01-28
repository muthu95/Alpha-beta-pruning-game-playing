import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameState {
    private int size;            // The number of stones
    private boolean[] stones;    // Game state: true for available stones, false for taken ones
    private int lastMove;        // The last move

    /**
     * Class constructor specifying the number of stones.
     */
    public GameState(int size) {

        this.size = size;

        //  For convenience, we use 1-based index, and set 0 to be unavailable
        this.stones = new boolean[this.size + 1];
        this.stones[0] = false;

        // Set default state of stones to available
        for (int i = 1; i <= this.size; ++i) {
            this.stones[i] = true;
        }

        // Set the last move be -1
        this.lastMove = -1;
    }

    /**
     * Copy constructor
     */
    public GameState(GameState other) {
        this.size = other.size;
        this.stones = Arrays.copyOf(other.stones, other.stones.length);
        this.lastMove = other.lastMove;
    }


    /**
     * This method is used to compute a list of legal moves
     *
     * @return This is the list of state's moves
     */
    public List<Integer> getMoves() {
        List<Integer> children = new ArrayList<Integer>();
        int i;
        if(lastMove == -1) {
        	int limit = (size%2 == 0) ? (size/2)-1 : (size/2);
        	for(i=1; i<=limit; i++)
        		if(i%2 == 1)
        			children.add(i);
        } else {
	        for(i=1; i<=size; i++) {
	        	if(stones[i] && (lastMove%i == 0 || i%lastMove == 0))
	        		children.add(i);
	        }
        }
        //System.out.println("Get Moves for " + lastMove + ": " + children);
        return children;
    }


    /**
     * This method is used to generate a list of successors
     * using the getMoves() method
     *
     * @return This is the list of state's successors
     */
    public List<GameState> getSuccessors() {
        return this.getMoves().stream().map(move -> {
            var state = new GameState(this);
            state.removeStone(move);
            return state;
        }).collect(Collectors.toList());
    }


    /**
     * This method is used to evaluate a game state based on
     * the given heuristic function
     *
     * @return double This is the static score of given state
     */
    public double evaluate(boolean isMaxPlayer, List<GameState> neighborStates) {
        double score = 0.0;
        if(neighborStates.size() == 0)
        	score = -1.0;
        else if(getStone(1))
        	score = 0.0;
        else if(lastMove == 1) {
        	if(getMoves().size()%2 == 1)
        		score = 0.5;
        	else
        		score = -0.5;
        } else if(Helper.isPrime(lastMove)) {
        	int c = 0;
        	for(GameState neighbor : neighborStates) {
        		if(neighbor.getLastMove()%lastMove == 0)
        			c++;
        	}
        	if(c%2 == 1)
        		score = 0.7;
        	else
        		score = -0.7;
        } else {
        	int largestPrime = Helper.getLargestPrimeFactor(lastMove);
        	int c = 0;
        	for(GameState neighbor : neighborStates) {
        		if(neighbor.getLastMove()%largestPrime == 0)
        			c++;
        	}
        	if(c%2 == 1)
        		score = 0.6;
        	else
        		score = -0.6;
        }
        if(score == 0)
        	return score;
        return (isMaxPlayer) ? score : -score;
    }

    /**
     * This method is used to take a stone out
     *
     * @param idx Index of the taken stone
     */
    public void removeStone(int idx) {
        this.stones[idx] = false;
        this.lastMove = idx;
    }

    /**
     * These are get/set methods for a stone
     *
     * @param idx Index of the taken stone
     */
    public void setStone(int idx) {
        this.stones[idx] = true;
    }

    public boolean getStone(int idx) {
        return this.stones[idx];
    }

    /**
     * These are get/set methods for lastMove variable
     *
     * @param move Index of the taken stone
     */
    public void setLastMove(int move) {
        this.lastMove = move;
    }

    public int getLastMove() {
        return this.lastMove;
    }

    /**
     * This is get method for game size
     *
     * @return int the number of stones
     */
    public int getSize() {
        return this.size;
    }
    
    public boolean isMaxPlayerTurn() {
    	int i, taken = 0;
        for(i=1; i<=size; i++) {
        	if(stones[i] == false)
        		taken++;
        }
        return (taken%2 == 0);   
    }

}	
