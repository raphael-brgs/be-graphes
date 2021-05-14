package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.utils.Label;
import org.insa.graphs.algorithm.utils.LabelStar;
import org.insa.graphs.model.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    protected Label newLabel (Node node) { //  On redefinit newLabel afin de creer des LabelStar
    	ShortestPathData data = this.getInputData() ;
    	return new LabelStar (node, data.getDestination(), data.getMode(), (double)Math.max(data.getMaximumSpeed(),data.getGraph().getGraphInformation().getMaximumSpeed() ) ) ;
    }

}
