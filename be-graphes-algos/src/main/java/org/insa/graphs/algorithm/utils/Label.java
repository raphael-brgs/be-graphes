package org.insa.graphs.algorithm.utils;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

import java.util.ArrayList;

public class Label {
	
	// Attributes of labels
	
	private Node current ;
	
	private boolean marked ;
	
	private double cost ;
	
	private Arc father ; // arc menant au père 
	
	private static ArrayList<Label> tabLabel ;  
	
	// Constructor of label
	
	public Label (Node actuel) {
		this.current = actuel ; 
		this.marked = false ; 
		this.cost = Double.POSITIVE_INFINITY ;
		this.father = null ;
		Label.tabLabel.add(this.current.getId(), this); ;
	}
	
	
	// Methode of Label (getter and setter) 

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
	}

	public Node getCurrent() {
		return current;
	}

	public void setCurrent(Node current) {
		this.current = current;
	}

	public Arc getFather() {
		return father;
	}

	public void setFather(Arc father) {
		this.father = father;
	}
	
	
	public Label getLabel(Node actuel) {
		return Label.tabLabel.get(actuel.getId()) ;
	}
}
