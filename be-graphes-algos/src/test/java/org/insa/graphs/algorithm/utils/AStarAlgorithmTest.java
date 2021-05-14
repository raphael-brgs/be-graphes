package org.insa.graphs.algorithm.utils;

import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;

public class AStarAlgorithmTest extends DijkstraAlgorithmTest {
	
	protected ShortestPathAlgorithm newAlgo (ShortestPathData data) {
    	return new AStarAlgorithm(data) ;
    }

}
