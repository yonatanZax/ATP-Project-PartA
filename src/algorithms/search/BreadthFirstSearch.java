package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import java.util.*;
import java.util.concurrent.LinkedTransferQueue;

/**
 * Represents the BreadthFirstSearch algorithm.
 * Inherits ASearchingAlgorithm.
 */
public class BreadthFirstSearch extends ASearchingAlgorithm {


	/**
	 * Constructor that will initialize what it need to solve the searchable problem.
	 */
	public BreadthFirstSearch() {
		super();
		stateQueue = new LinkedTransferQueue<AState>();
	}



	@Override
	public Solution solve(ISearchable searchable) {
		if (searchable == null)
			return null;
		clear();
		AState result = BFS(searchable);

		Solution solution = formSolution(result);;
		return solution;
	}


	/**
	 * The actual BFS algorithm.
	 * @param searchable
	 * @return
	 */
	private AState BFS(ISearchable searchable) {
		AState result = null;
		HashSet<Integer> visited = new HashSet<Integer>();
		AState goalState = searchable.getGoalState();
		AState startState = searchable.getStartState();

		stateQueue.add(startState);
		visited.add(startState.hashCode());

		while (!stateQueue.isEmpty()) {


			AState currentState = popOpenList();
			//visited.add((Integer)currentState.hashCode());

			if (currentState.equals(goalState))
				return currentState;

			ArrayList<AState> neighboursList = searchable.getAllPossibleStates(currentState);
			for (AState neighbour : neighboursList) {
				if (!visited.contains(neighbour.hashCode())){
					stateQueue.add(neighbour);

					visited.add(neighbour.hashCode());
				}
			}
		}
		return result;
	}



	public String getName(){
		return "Breadth First Search";
	}



	public static void main(String[] args) {
		char[][] map = {{'S','1','0','1','1'},
				        {'0','1','0','0','1'},
				        {'0','1','0','1','1'},
				        {'0','0','0','0','1'},
				        {'1','1','1','E','1'},
				        {'0','0','0','1','1'}};
		try {
			Maze maze = new Maze(map, new Position(0, 0, null), new Position(4, 3, null));
		}catch (Exception e){}
		MyMazeGenerator mg = new MyMazeGenerator();
		Maze maze2 = mg.generate(1000,1000);
		ISearchable searchableMaze = new SearchableMaze(maze2);

	//	DepthFirstSearch dfs = new DepthFirstSearch();
	//	Solution solution2 = dfs.solve(searchableMaze);
	//	System.out.println(solution2);
	//	System.out.println(dfs.getNumberOfNodesEvaluated() + '\n');

		BreadthFirstSearch bfs = new BreadthFirstSearch();
		//maze2.print();
		Solution solution = bfs.solve(searchableMaze);
		//maze.print();
		//System.out.println(solution);
        System.out.println(bfs.getNumberOfNodesEvaluated() + "\n");

        BestFirstSearch BestFS = new BestFirstSearch();
        Solution solution1 = BestFS.solve(searchableMaze);
        //System.out.println(solution1);
        System.out.println(BestFS.getNumberOfNodesEvaluated());
	}

}

