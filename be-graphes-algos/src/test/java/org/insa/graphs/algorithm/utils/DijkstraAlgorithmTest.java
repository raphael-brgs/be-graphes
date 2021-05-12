package org.insa.graphs.algorithm.utils;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.RoadInformation;
import org.insa.graphs.model.RoadInformation.RoadType;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.BinaryPathReader;
import org.insa.graphs.model.io.GraphReader;
import org.insa.graphs.model.io.PathReader;
import org.junit.BeforeClass;
import org.junit.Test;

public class DijkstraAlgorithmTest {
	
	// Small graph use for tests
    private static Graph graph;

    // List of nodes
    private static Node[] nodes;

    // List of arcs in the graph, a2b is the arc from node A (0) to B (1).
    @SuppressWarnings("unused")
    private static Arc a2b, a2c, a2e, b2c, c2d_1, c2d_2, c2d_3, c2a, d2a, d2e, e2d, e2f;

    // Some paths...
    private static Path emptyPath, singleNodePath, shortPath, longPath, loopPath, longLoopPath,
            invalidPath;

	
	@BeforeClass
    public static void initAll() throws IOException {

        // 10 and 20 meters per seconds
        RoadInformation speed10 = new RoadInformation(RoadType.MOTORWAY, null, true, 36, ""),
                speed20 = new RoadInformation(RoadType.MOTORWAY, null, true, 72, "");

        // Create nodes
        nodes = new Node[6];
        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = new Node(i, null);
        }

        // Add arcs...
        a2b = Node.linkNodes(nodes[0], nodes[1], 10, speed10, null);
        a2c = Node.linkNodes(nodes[0], nodes[2], 15, speed10, null);
        a2e = Node.linkNodes(nodes[0], nodes[4], 15, speed20, null);
        b2c = Node.linkNodes(nodes[1], nodes[2], 10, speed10, null);
        c2d_1 = Node.linkNodes(nodes[2], nodes[3], 20, speed10, null);
        c2d_2 = Node.linkNodes(nodes[2], nodes[3], 10, speed10, null);
        c2d_3 = Node.linkNodes(nodes[2], nodes[3], 15, speed20, null);
        d2a = Node.linkNodes(nodes[3], nodes[0], 15, speed10, null);
        d2e = Node.linkNodes(nodes[3], nodes[4], 22.8f, speed20, null);
        e2d = Node.linkNodes(nodes[4], nodes[3], 10, speed10, null);
        e2f = Node.linkNodes(nodes[4], nodes[5], 10, speed10, null);

        graph = new Graph("ID", "", Arrays.asList(nodes), null);
    }
	
	@Test
	public void SolutionValid() {
		ShortestPathData data = new ShortestPathData(graph,nodes[0],nodes[4],ArcInspectorFactory.getAllFilters().get(0)) ;
		ShortestPathAlgorithm Dijkstra = new DijkstraAlgorithm(data) ;
		ShortestPathSolution solutionD = Dijkstra.run() ;
		assertTrue(solutionD.getPath().isValid()) ; // On regarde si la solution est valide
	}

	@Test
	public void CheminSimple() {
		ShortestPathData data = new ShortestPathData(graph,nodes[0],nodes[4],ArcInspectorFactory.getAllFilters().get(0)) ;
		ShortestPathAlgorithm Dijkstra = new DijkstraAlgorithm(data) ;
		ShortestPathAlgorithm Bellman = new BellmanFordAlgorithm(data) ;
		ShortestPathSolution solutionD = Dijkstra.run() ;
		ShortestPathSolution solutionB = Bellman.run() ;
		assertEquals(solutionD.getPath().getLength(),solutionB.getPath().getLength(),1e-6) ; // On compare les solutions en longueur
	}
	
	@Test
	public void CheminInexistant() {
		ShortestPathData data = new ShortestPathData(graph,nodes[5],nodes[0],ArcInspectorFactory.getAllFilters().get(0)) ;
		ShortestPathAlgorithm Dijkstra = new DijkstraAlgorithm(data) ;
		ShortestPathSolution solutionD = Dijkstra.run() ;
		assertEquals(solutionD.getStatus(),Status.INFEASIBLE) ; // On doit avoir une solution INFEASIBLE
	}
	
	@Test
	public void CheminLongueurNulle() {
		ShortestPathData data = new ShortestPathData(graph,nodes[0],nodes[0],ArcInspectorFactory.getAllFilters().get(0)) ;
		ShortestPathAlgorithm Dijkstra = new DijkstraAlgorithm(data) ;
		ShortestPathSolution solutionD = Dijkstra.run() ;
		assertEquals(solutionD.getPath().size(),1) ; // On doit avoir une chemin avec un node
	}
	
	@Test
	public void CheminCarteLength() throws IOException {
		String mapName = "C:\\Users\\rapha\\Documents\\INSA\\3ème Année MiC - IR\\2nd semestre\\BE Graphes\\be-graphes\\maps_tf\\haute-garonne.mapgr";
        String pathName = "C:\\Users\\rapha\\Documents\\INSA\\3ème Année MiC - IR\\2nd semestre\\BE Graphes\\be-graphes\\path\\path_fr31_insa_aeroport_length.path";
        
        // Create a graph and path readers.
        final GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
        final PathReader pathReader = new BinaryPathReader(new DataInputStream(new BufferedInputStream(new FileInputStream(pathName))));
        //Read the graph and path.
        final Graph graph = reader.read();
        final Path path = pathReader.readPath(graph);
        
		ShortestPathData data = new ShortestPathData(graph,path.getOrigin(),path.getDestination(),ArcInspectorFactory.getAllFilters().get(1)) ; // On prend l'inspecteur length + only car allowed
		ShortestPathAlgorithm Dijkstra = new DijkstraAlgorithm(data) ;
		ShortestPathSolution solutionD = Dijkstra.run() ;
		assertEquals(solutionD.getPath().getLength(),path.getLength(),1e-6) ; // On doit avoir obtenir un chemin egal en longueur au chemin optimal
	}
	
	@Test
	public void CheminCarteTime() throws IOException {
		String mapName = "C:\\Users\\rapha\\Documents\\INSA\\3ème Année MiC - IR\\2nd semestre\\BE Graphes\\be-graphes\\maps_tf\\haute-garonne.mapgr";
        String pathName = "C:\\Users\\rapha\\Documents\\INSA\\3ème Année MiC - IR\\2nd semestre\\BE Graphes\\be-graphes\\path\\path_fr31_insa_aeroport_time.path";
        
        // Create a graph and path readers.
        final GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
        final PathReader pathReader = new BinaryPathReader(new DataInputStream(new BufferedInputStream(new FileInputStream(pathName))));
        //Read the graph and path.
        final Graph graph = reader.read();
        final Path path = pathReader.readPath(graph);
        
		ShortestPathData data = new ShortestPathData(graph,path.getOrigin(),path.getDestination(),ArcInspectorFactory.getAllFilters().get(3)) ; // On prend l'inspecteur time + only car allowed
		ShortestPathAlgorithm Dijkstra = new DijkstraAlgorithm(data) ;
		ShortestPathSolution solutionD = Dijkstra.run() ;
		assertEquals(solutionD.getPath().getMinimumTravelTime(),path.getMinimumTravelTime(),1e-6) ; // On doit avoir obtenir un chemin egal en temps au chemin optimal
	}
}
