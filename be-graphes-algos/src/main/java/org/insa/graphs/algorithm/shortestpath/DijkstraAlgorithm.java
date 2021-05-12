package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;

import java.util.ArrayList;
import java.util.Collections;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.algorithm.utils.EmptyPriorityQueueException;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.algorithm.utils.Label;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
    	
    	boolean finished = false ; // permet de mettre fin à la boucle
    	
    	// On recupere le graph
        final ShortestPathData data = getInputData();
        Graph graph = data.getGraph();
        final int nbNodes = graph.size();
        
        // On initialise une solution 
        ShortestPathSolution solution = null;
        
        // On crée un tableau de Labels afin d'associer un label à chaque Node
        Label tabLabels[] = new Label[nbNodes];
        
        // Initialisation du tas de labels
        BinaryHeap<Label> tas = new BinaryHeap<Label>() ;
         
        // Cas si le sommet de depart == sommet d'arriver ?? 
      
        // On ajoute le sommet de depart 
        Label labDeb = new Label (data.getOrigin()) ;
        tabLabels[labDeb.getCurrent().getId()] = labDeb ;
        tas.insert(labDeb); // on insert l'origin dans le tas
        labDeb.setInTas(true); // on note ce label comme etant dans le tas
        labDeb.setCost(0); // on met le coût de l'origine a zero ( les autre sont deja à l'infini )
        
        notifyOriginProcessed(data.getOrigin()); // on notifie les observer que l'origine a été processed
        
        // Djikstra algo
        // on parcours tout les nodes et on les marque

        // Tant qu'il y a des Node non marqués (donc dans le tas) ou qu'on n'a pas atteint la destination
        while (!tas.isEmpty() && !finished) {
        	
        	Label actuel = null ; 
        	// On supprime le minimum du tas et on le recupere
        	try {
        		actuel = tas.deleteMin() ;
        		actuel.setInTas(false);
        	} catch (EmptyPriorityQueueException e) {
        		return new ShortestPathSolution(data, Status.INFEASIBLE);
        	}		
        	// On marque le node fraîchement sorti du tas et on avertit les observer
        	actuel.setMarked();
        	notifyNodeMarked(actuel.getCurrent());
        	
        	// Si la destination est atteinte on doit arreter la boucle
        	if (actuel.getCurrent() == data.getDestination()) {
        		finished = true ;
        		notifyDestinationReached(data.getDestination()) ;
        	}
        	
        	// On parcours les succeseurs du node fraichement marqué
        	for (Arc arc: actuel.getCurrent().getSuccessors()) {
        		
        		//On vérifie si on peut utiliser l'arc selon les filtres selectionés
        		if (!data.isAllowed(arc)) {
					continue;
				}
        		
        		//On recupere label et node successeur
        		Node nodeSuiv = arc.getDestination() ;
        		Label labelSuiv = tabLabels[nodeSuiv.getId()] ;
        		
        		//On verifie que le label est bien été créé sinon on le crée
        		if (labelSuiv == null) {
					labelSuiv = new Label(nodeSuiv);
					tabLabels[labelSuiv.getCurrent().getId()] = labelSuiv;
				}
        		
        		// On verifie si le node succeseurs visité n'est pas marqué
        		if (!labelSuiv.isMarked()) {
        			
        			// On compare l'ancien coût avec le nouveau coût
        			if (labelSuiv.getCost() > actuel.getCost()+ data.getCost(arc)) {
        				
        				// Si le nouveau coût est plus petit, on met à jour le coût du labels et le pere du label
        				labelSuiv.setCost(actuel.getCost()+ data.getCost(arc)); 
        				labelSuiv.setFather(arc); // on met à jour le père
        				
        				// Si le label est deja dans le tas on update sa position avec un remove-insert
        				if (labelSuiv.isInTas()) {
        					tas.remove(labelSuiv);
        				}
        				// Sinon on l'ajoute dans le tas
        				else {
        					labelSuiv.setInTas(true);
        					notifyNodeReached(nodeSuiv); 
        				}
        				tas.insert(labelSuiv);
        			}
        		}
        	}
        }

        // la destination a un coût infini dans les label, la solution n'est pas faisable
        if ( tabLabels[data.getDestination().getId()].getCost() == Double.POSITIVE_INFINITY) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else {
        	//On crée un chemin à l'envers avec le tableau des labels final
        	ArrayList<Arc> arcs = new ArrayList<>();
        	Arc arc = tabLabels[data.getDestination().getId()].getFather() ; // le getFather nous renvoie bien l'arc precedant la destination
        	
            while (arc != null) {
                arcs.add(arc);
                arc = tabLabels[arc.getOrigin().getId()].getFather() ;
            }
            
            //On reverse le chemin
            Collections.reverse(arcs);
            
            // Voici la solution 
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        }
        
        return solution;
    }

}
