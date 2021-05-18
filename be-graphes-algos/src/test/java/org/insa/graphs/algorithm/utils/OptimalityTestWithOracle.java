package org.insa.graphs.algorithm.utils;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.*;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.GraphStatistics;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Point;
import org.insa.graphs.model.RoadInformation;
import org.insa.graphs.model.RoadInformation.RoadType;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.BeforeClass;
import org.junit.Test;

public class OptimalityTestWithOracle { // We assume that Bellman-Ford algorithm always return an optimal solution
	
	// Objects related to INSA's R4 to Metro Fac de Pharma path
	private static String mapINSA = "C:\\Users\\rapha\\Documents\\INSA\\3ème Année MiC - IR\\2nd semestre\\BE Graphes\\be-graphes\\maps_tf\\insa.mapgr" ;
	private static GraphReader INSAreader = null ;
	private static Graph graphINSA = null;
	private static Node R4 = null;
	private static Node FacDePharma = null;
	
	// Objects related to Haute Garonne's INSA to Airport path
	private static String mapHG = "C:\\Users\\rapha\\Documents\\INSA\\3ème Année MiC - IR\\2nd semestre\\BE Graphes\\be-graphes\\maps_tf\\haute-garonne.mapgr";
	private static GraphReader HGreader = null ;
	private static Graph graphHG = null;
	private static Node INSA = null;
	private static Node Airport = null;
	
	private static ShortestPathData INSAtoAirport_Time_Data = null;
	private static ShortestPathSolution INSAtoAirport_Time_Oracle = null ; 
	
	private static ShortestPathData INSAtoAirport_Length_Data = null;
	private static ShortestPathSolution INSAtoAirport_Length_Oracle = null ; 
	
	private static ShortestPathAlgorithm Bellman = null ;
	
	@BeforeClass
    public static void initAll() throws IOException {
		
		// Fist path initilizations :
		INSAreader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapINSA))));
		graphINSA = INSAreader.read();
		R4 = graphINSA.get(819) ;
		FacDePharma = graphINSA.get(778) ;
		
		// Second path initilizations :
		HGreader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapHG))));
		graphHG = HGreader.read();
		INSA = graphHG.get(10991) ;
		Airport = graphHG.get(89149) ;
		
		INSAtoAirport_Time_Data = new ShortestPathData(graphHG,INSA,Airport,ArcInspectorFactory.getAllFilters().get(3)) ;
		Bellman = new BellmanFordAlgorithm(INSAtoAirport_Time_Data) ;
		INSAtoAirport_Time_Oracle = Bellman.run() ; 
		
		INSAtoAirport_Length_Data = new ShortestPathData(graphHG,INSA,Airport,ArcInspectorFactory.getAllFilters().get(1)) ;
		Bellman = new BellmanFordAlgorithm(INSAtoAirport_Length_Data) ;
		INSAtoAirport_Length_Oracle = Bellman.run() ;  
		
		
    }
	

	@Test
	public void R4toMetroFacDePharma_Time_Pedestrian() {
		fail("Not yet implemented");
	}
	
	@Test
	public void R4toMetroFacDePharma_Length_Pedestrian() {
		fail("Not yet implemented");
	}
	
	@Test
	public void Dijkstra_INSAtoAirport_Length_CarOnly() {
		ShortestPathAlgorithm Dijkstra = new DijkstraAlgorithm(INSAtoAirport_Length_Data) ;
		ShortestPathSolution solution = Dijkstra.run() ;
		assertEquals(solution.getPath().getLength(),INSAtoAirport_Length_Oracle.getPath().getLength(),1e-6) ;
	}
	
	@Test
	public void Astar_INSAtoAirport_Length_CarOnly() {
		ShortestPathAlgorithm Astar = new AStarAlgorithm(INSAtoAirport_Length_Data) ;
		ShortestPathSolution solution = Astar.run() ;
		assertEquals(solution.getPath().getLength(),INSAtoAirport_Length_Oracle.getPath().getLength(),1e-6) ;
	}
	
	
	@Test
	public void Dijkstra_INSAtoAirport_Time_CarOnly() {
		ShortestPathAlgorithm Dijkstra = new DijkstraAlgorithm(INSAtoAirport_Time_Data) ;
		ShortestPathSolution solution = Dijkstra.run() ;
		assertEquals(solution.getPath().getMinimumTravelTime(),INSAtoAirport_Time_Oracle.getPath().getMinimumTravelTime(),1e-6) ;
	}
	
	@Test
	public void Astar_INSAtoAirport_Time_CarOnly() {
		ShortestPathAlgorithm Astar = new AStarAlgorithm(INSAtoAirport_Time_Data) ;
		ShortestPathSolution solution = Astar.run() ;
		assertEquals(solution.getPath().getMinimumTravelTime(),INSAtoAirport_Time_Oracle.getPath().getMinimumTravelTime(),1e-6) ;
	}


}
