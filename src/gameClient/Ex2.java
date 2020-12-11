package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import org.json.JSONException;
import org.json.JSONObject;
import api.game_service;
import api.directed_weighted_graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Ex2 implements Runnable {
	private static MyFrame _win;
	private static Arena _ar;
	static int index = 0;
	static List<CL_Agent> agents = new LinkedList<>();
	private static String agentsAfterMove;
	private static int scenario_num;
	private static directed_weighted_graph graph;
	private static game_service game;
	private static long moveTime;
	private static int sleep;
	private static int id;


	public static void main(String[] args) {
		id = Integer.parseInt(args[0]);
		scenario_num = Integer.parseInt(args[1]);

		Thread player = new Thread(new SimplePlayer("data\\pokemon.mp3"));
		Thread client = new Thread(new Ex2());
		client.start();
		//player.start();
	}

	@Override
	public void run() {
		//scenario_num = 11;
		game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games

		game.login(id);

		String g = game.getGraph();

		String fileGraph = newSave(g);
		directed_weighted_graph gg = new DWGraph_DS(); //= game.getJava_Graph_Not_to_be_used();
		dw_graph_algorithms aa = new DWGraph_Algo();
		aa.init(gg);
		aa.load(fileGraph);
		graph = aa.getGraph();

		try {
			init(game);
		} catch (IOException e) {
			e.printStackTrace();
		}
		game.startGame();

		int ind = 0;
		sleep = 100;

		while (game.isRunning()) {
			sleep = 100;
			_ar.setInfo(game.toString());
			_ar.setTimeToEnd(game.timeToEnd());

			moveAgants(game, aa.getGraph());
			try {
				if (ind % 1 == 0) {
					_win.repaint();
				}
				Thread.sleep(sleep);
				ind++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String res = game.toString();

		System.out.println(res);
		System.exit(0);
	}

	/**
	 * Moves each of the agents along the edge,
	 * in case the agent is on a node the next destination (next edge) is chosen (randomly).
	 *
	 * @param game
	 * @param gg
	 * @param
	 */
	private static void moveAgants(game_service game, directed_weighted_graph gg) {
		agentsAfterMove = game.move();
		moveTime = System.currentTimeMillis();
		List<CL_Agent> agents = Arena.getAgents(agentsAfterMove, gg);
		_ar.setAgents(agents);

		String pokemonString = game.getPokemons();
		List<CL_Pokemon> pokemonsList = Arena.json2Pokemons(pokemonString);
		_ar.setPokemons(pokemonsList);

		for (int i = 0; i < agents.size(); i++) {
			CL_Agent ag = agents.get(i);
			int id = ag.getID();
			int dest = ag.getNextNode();
			int src = ag.getSrcNode();
			double v = ag.getValue();
			if (dest == -1) {
				dest = nextNode(gg, ag);
				game.chooseNextEdge(ag.getID(), dest);
			}

		}
	}


	private static int nextNode(directed_weighted_graph g, CL_Agent ag) {
		int ans = -1;

		List<CL_Pokemon> pok = _ar.getPokemons(); // List of all current pokemons
		CL_Pokemon chosenPok;
		dw_graph_algorithms aa = new DWGraph_Algo();
		aa.init(g);

		for (CL_Pokemon setPok : pok) { //set the weight of each pokemon the the shortestDist to src(agent)
			Arena.updateEdge(setPok, g);
			setPok.setTempWeight(aa, ag.getSrcNode());

		}
		//choose the nearest pokemon to this specific agent
		PriorityQueue<CL_Pokemon> sortedPokByWeight = new PriorityQueue<CL_Pokemon>(11, new CL_Pokemon.pathComparator());
		sortedPokByWeight.addAll(pok);
		chosenPok = sortedPokByWeight.poll();

		if(scenario_num == 20 && chosenPok.get_edge().getWeight() < 1.06 && ag.getSpeed() == 5) {
			sleep = 20;

		}


		ag.setCurrEdge(chosenPok.get_edge());

			if (ag.getSrcNode() == chosenPok.get_edge().getSrc()) { // need to add - && isOnEdge(src,pok,type....){ game.move()}
				return chosenPok.get_edge().getDest();// src arrived to pok src now sent the agent to dest
			}


			List<node_data> nn = aa.shortestPath(ag.getSrcNode(), chosenPok.get_edge().getSrc());

			if (nn.size() > 1) {
				ans = nn.get(1).getKey();
			}

			return ans;
		}



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

			JSONObject line;
			try {
				line = new JSONObject(info);
				JSONObject ttt = line.getJSONObject("GameServer");
				int rs = ttt.getInt("agents");

				ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
				PriorityQueue<CL_Pokemon> sortedPokByValue = new PriorityQueue<CL_Pokemon>(11, new CL_Pokemon.valueComparator());
				sortedPokByValue.addAll(cl_fs);

				for (int a = 0; a < cl_fs.size(); a++) {
					Arena.updateEdge(cl_fs.get(a), graph);
				}
				for (int a = 0; a < rs; a++) {
					CL_Pokemon c = sortedPokByValue.poll();
					int nn = c.get_edge().getSrc();
					game.addAgent(nn);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

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

	}

