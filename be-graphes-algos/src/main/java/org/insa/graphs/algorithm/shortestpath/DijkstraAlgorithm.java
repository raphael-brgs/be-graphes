package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;

import java.util.ArrayList;
import java.util.Collections;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.algorithm.utils.ElementNotFoundException;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
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
      
        // On initialise les labels de toute les nodes du graph
        Label lab = null; 
        
        for (Node node: graph.getNodes()) {
        	lab = new Label (node) ;
        }
        
        lab = lab.getLabel(data.getOrigin()) ;
        lab.setCost(0); // on met le cout de l'origine a zero ( les autre sont deja à l'infini ) 
        
        // Initialisation du tas
        BinaryHeap<Label> tas = new BinaryHeap<Label>() ;
        tas.insert(lab); // on insert l'origin dans le tas
        
        // Djikstra algo
        // on parcours tout les nodes et on les marque
        int nombre_marked=0 ;
        Node node_suiv = null ;
        Label label_suiv = null ;

        while (nombre_marked <= nbNodes) {
        	lab = tas.findMin() ; // on recupere le min dans le tas
        	lab.setMarked(true); // on marque le label
        	nombre_marked++;
        	// on parcours les succeseurs du node marqué
        	for (Arc arc: lab.getCurrent().getSuccessors()) {
        		node_suiv = arc.getDestination() ;
        		label_suiv = lab.getLabel(node_suiv) ;
        		if ( label_suiv.isMarked() == false ) { // si le node succeseurs visité n'est pas marqué
        			if (label_suiv.getCost() > lab.getCost()+ data.getCost(arc)) {
        				label_suiv.setCost(lab.getCost()+ data.getCost(arc)); // on met à jour le coût des labels
        				label_suiv.setFather(arc); // on met à jour le père
        				try { tas.remove(label_suiv); // on insert le label (remove avant au cas où il y était déjà) 
        				} catch (ElementNotFoundException e){
        					
        				}
        				tas.insert(label_suiv); 
        			}
        		}
        	}
        }
        // on crée la solution
        ShortestPathSolution solution = null;

        // la destination a un coût infini dans les label, la solution n'est pas faisable
        if ( lab.getLabel(data.getDestination()).getCost() == Double.POSITIVE_INFINITY) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else {
        	//On crée un chemin à l'envers avec le tableau des labels final
        	ArrayList<Arc> arcs = new ArrayList<>();
        	Arc arc = lab.getLabel(data.getDestination()).getFather() ;
            while (arc != null) {
                arcs.add(arc);
                arc = lab.getLabel(arc.getOrigin()).getFather() ;
            }
            
            //On reverse le chemin
            Collections.reverse(arcs);
            
            // Voici la solution 
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        }
        
        return solution;
    }

}
