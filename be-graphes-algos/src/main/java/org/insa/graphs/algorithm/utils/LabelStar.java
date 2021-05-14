package org.insa.graphs.algorithm.utils;

import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.model.Node;

public class LabelStar extends Label {
	
	private double costEstimation ;

	public LabelStar(Node actuel,Node destination, Mode mode, Double maxSpeed) {
		super(actuel);
		if (mode==Mode.TIME) {
			this.costEstimation = actuel.getPoint().distanceTo(destination.getPoint()) / maxSpeed ; // On divise par maxSpeed afin d'avoir une estimation sur le temps ( D/V = T ) , maxspeed nous permet d'avoir une borne inférieur
		} else if (mode==Mode.LENGTH) {
			this.costEstimation = actuel.getPoint().distanceTo(destination.getPoint()) ;
		}
	}
	
	public double getCostEstimation() {
		return this.costEstimation ;
	}
	
	public double getTotalCost() {
		return this.getCost() + this.getCostEstimation();
	}

}
