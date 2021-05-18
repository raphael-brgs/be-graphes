package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.utils.Label;
import org.insa.graphs.algorithm.utils.LabelStar;
import org.insa.graphs.model.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    private double getMaximumSpeed () {
    	ShortestPathData data = this.getInputData() ;
    	double source1 = data.getMaximumSpeed() ;
    	double source2 = data.getGraph().getGraphInformation().getMaximumSpeed() ;
        return (double)Math.max(source1, source2) ;	
    }
    
    protected Label newLabel (Node node) { //  On redefinit newLabel afin de creer des LabelStar à la place des Labels
    	ShortestPathData data = this.getInputData() ;
    	return new LabelStar (node, data.getDestination(), data.getMode(), this.getMaximumSpeed() ) ;
    }

}
