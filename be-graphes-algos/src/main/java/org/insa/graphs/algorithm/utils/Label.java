package org.insa.graphs.algorithm.utils;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label implements Comparable<Label> {
	
	// Attributes of labels
	
	private Node current ;
	
	private boolean marked ;
	
	private double cost ;
	
	private Arc father ; // arc menant au père 
	
	private boolean inTas;  
	
	// Constructor of label
	
	public Label (Node actuel) {
		this.current = actuel ; 
		this.marked = false ; 
		this.cost = Double.POSITIVE_INFINITY ;
		this.father = null ;
		this.inTas = false ;
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

	public void setMarked() {
		this.marked = true;
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
	
	
	public boolean isInTas() {
		return inTas;
	}


	public void setInTas(boolean inTas) {
		this.inTas = inTas;
	}
	
	public void clear() {
		this.clear();
	}


	@Override
	public int compareTo(Label other) {
		int result = 0 ;
		if (this.getCost() < other.getCost()) {result = -1 ;}
		if (this.getCost() == other.getCost()) {result = 0 ;}
		if (this.getCost() > other.getCost()) { result = 1 ;}
		return result;
	}
	@Override
	public String toString() {
		return " Node id : " + this.getCurrent().getId() + " Label cost : " + this.getCost() ;
	}
}
