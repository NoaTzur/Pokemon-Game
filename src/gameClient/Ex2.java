package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * this class is merging all classes to one working game, we used the example that supplied to us and did an improvements.
 * there is two threads in the main function - one is running the game and the second is playing a song.
 * this class has number of function which in the end, we hope, is a smart algorithm - we want to catch them all !!
 */
public class Ex2 implements Runnable {
	private static MyFrame _win;
	private static Arena _ar;
	private static Ex2 ex2;
	private static String agentsAfterMove;
	private static int scenario_num = -1;
	private static directed_weighted_graph graph;
	private static dw_graph_algorithms graphAlgo;
	private static game_service game;
	private static int sleep;
	private static int id;
	private static EdgesWithPok chosenPoks = new EdgesWithPok();


	public static void main(String[] args) {

		Thread player = new Thread(new SimplePlayer("data\\pokemon.mp3"));
		ex2 = new Ex2();
		Thread client = new Thread(ex2);

//		if(args.length !=0) {
//			id = Integer.parseInt(args[0]);
//			scenario_num = Integer.parseInt(args[1]);
//		}
//		else{
//			myLogin loginScreen = new myLogin();
//			loginScreen.register(ex2);
//			myLogin.action();
//			while(scenario_num == -1) {
//				System.out.println("");
//			}
//		}

		client.start();
		//player.start();
	}

	/**
	 * this function is the main thread. it initialized the game with the scenario number that the user has choose,
	 * initialized the Arena and the GUI with the game inforamation from the server.
	 * while the game is still running, calls the function MoveAgents that update the location of the agents
	 * and the pokemons in the screen .
	 */
	@Override
	public void run() {
		scenario_num = 17;
		game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games

		//game.login(id);
		System.out.println(id);

		String jasonG = game.getGraph();
		String fileGraph = newSave(jasonG);
		directed_weighted_graph gg = new DWGraph_DS(); //= game.getJava_Graph_Not_to_be_used();
		graphAlgo = new DWGraph_Algo();
		graphAlgo.init(gg);
		graphAlgo.load(fileGraph);
		graph = graphAlgo.getGraph();

		try {
			init(game);
		} catch (IOException e) {
			e.printStackTrace();
		}
		game.startGame();

		int repaintTime = 0;
		sleep = 100;

		while (game.isRunning()) {
			sleep = 100;

			_ar.setInfo(game.toString());
			_ar.setTimeToEnd(game.timeToEnd());

			moveAgants(game, graphAlgo.getGraph());
			try {
				if (repaintTime % 1 == 0) {
					_win.repaint();
				}
				Thread.sleep(sleep);
				repaintTime++;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String res = game.toString();

		System.out.println(res);
		System.exit(0);
	}

	/**
	 * Moves each of the agents along the edge (by the game.move() function, that return a jason string with the new info)
	 * for each agent, checks if there are an destination that it need to go to, in case the destination is -1,
	 * we need to find knew node to be its destination.
	 * nextNode() function returns the next dest for each agent, and chooseNextEdge updating the new location in the game server.
	 * @param game - the info from the server
	 * @param graph - game graph the user choose
	 *
	 */
	private static void moveAgants(game_service game, directed_weighted_graph graph) {
		agentsAfterMove = game.move();
		List<CL_Agent> agents = Arena.getAgents(agentsAfterMove, graph);
		_ar.setAgents(agents);

		String pokemonString = game.getPokemons();
		List<CL_Pokemon> pokemonsList = Arena.json2Pokemons(pokemonString);
		_ar.setPokemons(pokemonsList);

		for (int i = 0; i < agents.size(); i++) {
			CL_Agent ag = agents.get(i);
			int dest = ag.getNextNode();
			if(dest == -1) {
				dest = nextNode(graph, ag);
				game.chooseNextEdge(ag.getID(), dest);
			}
		}
	}

	/**
	 * this function is the main "clever" function of the game process.
	 * in this function, we choose the next destination for each agent:
	 * with the help of the "EdgesWithPok" class that we`ve created, we checks if the specific agent has a destination pokemon.
	 * if not - we choose for him pokemon from the pokemon list. this selection is executed this way :
	 * add all the pokemon in the list to a priority queue. the queue sorting the pokemon by the shortest path between the agent and the
	 * pokemon. when we poll a pokemon from the queue, we know that this pokemon is the closest to this agent.
	 * but - to be sure we does not sent agents to the same pokemon, after we poll pokemon from the queue we check if this pokemon
	 * does not have a "parent" agent. if there is, we poll another pokemon from the queue.
	 * if out agent has a destination pokemon, we continue to move the agent to this pokemon.
	 * the "moving" of the agent to the chosen pokemon is done by the "toThePok" function.
	 *
	 * @param g - the game graph
	 * @param ag -the agent
	 * @return int - a new destination id node
	 */
	private static int nextNode(directed_weighted_graph g, CL_Agent ag) {
		int ans = -1;

		dw_graph_algorithms ga = new DWGraph_Algo();
		ga.init(g);

		List<CL_Pokemon> pok = _ar.getPokemons(); // List of all current pokemons

		for (CL_Pokemon setPok : pok) { //set the weight of each pokemon the the shortestDist to src(agent)
			Arena.updateEdge(setPok, g);
			setPok.setTempWeight(ga, ag.getSrcNode());
		}

		CL_Pokemon chosenPok;
		if (chosenPoks.getPok(ag.getID()) != null) {
			chosenPok = chosenPoks.getPok(ag.getID());
		}
		else { //agent doesnt have a pokemon destination so pick new one
			PriorityQueue<CL_Pokemon> sortedPokByWeight = new PriorityQueue<CL_Pokemon>(20, new CL_Pokemon.pathComparator());
			sortedPokByWeight.addAll(pok);
			chosenPok = sortedPokByWeight.poll();
			while (chosenPoks.containsPok(chosenPok) && !sortedPokByWeight.isEmpty()) { //there is an agent that "connect" to that pokemon
				chosenPok = sortedPokByWeight.poll();
			}
			chosenPoks.addPok(ag.getID(), chosenPok);
		}
		ans = toThePok(ag, ga);

		if(scenario_num > 20 || scenario_num == 3){
			if(chosenPok.get_edge().getWeight() < 1.1 && ag.getSpeed() == 5) {
				sleep = 20;
			}
		}
		else if(chosenPok.get_edge().getWeight() < 0.7 && ag.getSpeed() == 5){
			sleep = 20;
		}

		return ans;
	}

	/**
	 * this function returning the next node the agent need to move to.
	 * its doing this with the help of the EdgesWithPok class.
	 * this function is checking 2 things - first, if the agent is arrived to the src of the pokemon edge - if true- returns the
	 * pokemon edge destination. (agent has caught his destination pokemon, he is no longet has a destination, in the next iteration
	 * we will choose for him new pokmeon)
	 * ans second thing, if the agent does not arrived to the pokemon edge src, it returns the next node in the shortest path
	 * between the agent ans the pokemon edge src.
	 * @param ag = the agent
	 * @param ga = graph algo - the graph of the game.
	 * @return new destination key node for the agent.
	 */
	public static int toThePok(CL_Agent ag, dw_graph_algorithms ga) {
		int ans = -1;

		if(chosenPoks.getPok(ag.getID()).get_edge().getSrc() == ag.getSrcNode()) {
			ans = chosenPoks.getPok(ag.getID()).get_edge().getDest();
			chosenPoks.addPok(ag.getID(), null); // agent caught the pokemon, no pokemon "connected" to him right know
		} else {
			List<node_data> path = ga.shortestPath(ag.getSrcNode(), chosenPoks.getPok(ag.getID()).get_edge().getSrc());
			if (path.size() > 1) {
				ans = path.get(1).getKey();
			}
		}
		return ans;
	}

	/**
	 * this function initialized the Arena and the GUI with the information from the server, place the agent and the pokemons
	 * in the start location.
	 * if the graph is connected - the function is locates them near pokemons with the higher value.
	 * if the graph is un-connected, with the help of the DFS class we wrote, it is supposed to locate
	 * each agent in different component in the graph.
	 * @param game
	 *
	 */
	private void init (game_service game) throws IOException {
		String poksJason = game.getPokemons();
		_ar = new Arena();
		_ar.setGraph(graph);
		_ar.setPokemons(Arena.json2Pokemons(poksJason));
		_win = new MyFrame("test Ex2");
		_win.setSize(1000, 700);
		_win.update(_ar);

		_win.show();
		String info = game.toString();
		int rs = 0;

		if(graphAlgo.isConnected()) {
			JSONObject line;
			try {
				line = new JSONObject(info);
				JSONObject gameInfo = line.getJSONObject("GameServer");
				rs = gameInfo.getInt("agents");

				ArrayList<CL_Pokemon> cl_pk = Arena.json2Pokemons(game.getPokemons());

				//priorityQueue which poll the pokemon that has the grater value
				PriorityQueue<CL_Pokemon> sortedPokByValue = new PriorityQueue<CL_Pokemon>(11, new CL_Pokemon.valueComparator());
				sortedPokByValue.addAll(cl_pk);

				for (int p = 0; p < cl_pk.size(); p++) {
					Arena.updateEdge(cl_pk.get(p), graph);
				}
				for (int a = 0; a < rs; a++) {
					CL_Pokemon c = sortedPokByValue.poll();
					int node = c.get_edge().getSrc();
					game.addAgent(node);
				}


			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		else{
			JSONObject line;
			try {
				line = new JSONObject(info);
				JSONObject gameInfo = line.getJSONObject("GameServer");
				rs = gameInfo.getInt("agents");

				ArrayList<CL_Pokemon> cl_pk = Arena.json2Pokemons(game.getPokemons());
				for (int p = 0; p < cl_pk.size(); p++) {
					Arena.updateEdge(cl_pk.get(p), graph);
				}
				//dfs algorithms returns a list of integer each one representing component in the graph
				DFS graphComponents = new DFS();
				List<Integer> componentsNodes = graphComponents.DFSalgo(graph);

				for (int a = 0; a < rs; a++) {
					int node = componentsNodes.get(a);
					game.addAgent(node);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		for(int i=0; i<rs; i++){
			chosenPoks.addPok(i, null); // init the chosen structure
		}
	}

	/**
	 * this function take an jason string and puts it into a file located in the workSpace folder.
	 * @param g = the string
	 * @return the path to the file.
	 */
	public static String newSave (String g){
		String path = "data\\gameGraph.txt";
		try {
			PrintWriter pw = new PrintWriter(new File(path));
			pw.write(g);
			pw.close();
			return path;


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * sets the scenario we are getting from the user
	 * @param num from 0 to 23
	 */
	public static void setScenario(int num){
		scenario_num = num;
	}

	/**
	 * sets the id of the user
	 * @param num an id
	 */
	public static void setId(int num){
		id = num;
	}


}

