package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.algorithm.utils.Label;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
    	
    	// On recupere le graph
        final ShortestPathData data = getInputData();
        Graph graph = data.getGraph();

        final int nbNodes = graph.size();
        
        // On initialise les labels de tout les nodes du graph
         
        
        for (Node node: graph.getNodes()) {
        	Label new_lab ; 
        	new_lab = new Label (node) ;
        }
        
        // TODO:
            
        
        ShortestPathSolution solution = null;
        return solution;
    }

}
