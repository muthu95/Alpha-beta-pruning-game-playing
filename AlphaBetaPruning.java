import java.util.List;
public class AlphaBetaPruning {


	int visitedNodes;
	int evaluatedNodes;
	int maxDepthReached;
	int nextMove;
	int deepestLevel;
	double score;
	
    public AlphaBetaPruning() {
    	visitedNodes = 0;
    	evaluatedNodes = 0;
    	maxDepthReached = 0;
    	nextMove = 0;
    }

    /**
     * This function will print out the information to the terminal,
     * as specified in the homework description.
     */
    public void printStats() {
    	System.out.println("Move: " + nextMove);
    	System.out.println("Value: " + score);
    	System.out.println("Number of Nodes Visited: " + visitedNodes);
    	System.out.println("Number of Nodes Evaluated: " + evaluatedNodes);
    	System.out.println("Max Depth Reached: " + maxDepthReached);
    	System.out.printf("Avg Effective Branching Factor: %.1f\n", (double)(visitedNodes-1)/(visitedNodes-evaluatedNodes));
    }

    /**
     * This function will start the alpha-beta search
     * @param state This is the current game state
     * @param depth This is the specified search depth
     */
    public void run(GameState state, int depth) {
        boolean maxPlayer = state.isMaxPlayerTurn();
        deepestLevel = depth;
    	score = alphabeta(state, depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, maxPlayer);
    	maxDepthReached = depth - deepestLevel;
    }

    /**
     * This method is used to implement alpha-beta pruning for both 2 players
     * @param state This is the current game state
     * @param depth Current depth of search
     * @param alpha Current Alpha value
     * @param beta Current Beta value
     * @param maxPlayer True if player is Max Player; Otherwise, false
     * @return int This is the number indicating score of the best next move
     */
    private double alphabeta(GameState state, int depth, double alpha, double beta, boolean maxPlayer) {
        //System.out.println(state.getLastMove());
    	double v = 0.0;
        visitedNodes++;
        int successorStep = 0;
        deepestLevel = Math.min(deepestLevel, depth);
        List<GameState> neighborStates = state.getSuccessors();
        if (depth == 0 || neighborStates.size() == 0) {
        	evaluatedNodes++;
        	v = state.evaluate(maxPlayer, neighborStates);
        } else {
    		double neighborValue;
        	if(maxPlayer) {
	        	v = Double.NEGATIVE_INFINITY;
				for (GameState neighborState : neighborStates) {
					neighborValue = alphabeta(neighborState, depth-1, alpha, beta, !maxPlayer);
					if(neighborValue > v) {
						v = neighborValue;
						successorStep = neighborState.getLastMove();
					} else if(neighborValue == v)
						successorStep = Math.min(successorStep, neighborState.getLastMove());
					if(v >= beta)
						break;
					alpha = Math.max(alpha, v);
				}
        	} else {
        		v = Double.POSITIVE_INFINITY;
				for (GameState neighborState : neighborStates) {
					neighborValue = alphabeta(neighborState, depth-1, alpha, beta, !maxPlayer);
					if(neighborValue < v) {
						v = neighborValue;
						successorStep = neighborState.getLastMove();
					} else if(neighborValue == v)
						successorStep = Math.min(successorStep, neighborState.getLastMove());
					if(v <= alpha)
						break;
					beta = Math.min(beta, v);
				}
        	}
        }
        nextMove = successorStep;
    	return v;
    }
}
