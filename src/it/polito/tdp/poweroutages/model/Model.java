package it.polito.tdp.poweroutages.model;

import java.time.Period;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.poweroutages.db.PowerOutageDAO;

public class Model {

	PowerOutageDAO podao;
	Set<PowerOutage> best ;
	Set<PowerOutage> pOutages;
	
	int maxPersone = 0;
	
	public Model() {
		podao = new PowerOutageDAO();
		pOutages = new HashSet<PowerOutage>();
	}
	
	public Set<PowerOutage> cercaSoluzione(Nerc selectedNerc, int anniMax, int oreMax){
		Set<PowerOutage> parziale = new HashSet<PowerOutage>();
		pOutages= podao.getOutages(selectedNerc);
		best = new HashSet<PowerOutage>();
		
		cerca(anniMax, oreMax, parziale, 0);
		
		return best;
		
	}
	
	private void cerca(int anniMax, int oreMax, Set<PowerOutage> parziale, int livello) {
		
		//caso terminale
		if(livello==pOutages.size()) {
			System.out.println(livello);
			for(PowerOutage p : parziale) {
				System.out.println(p.toString());
			}
			int persone = calcolaPersone(parziale);
			if (persone>maxPersone) {
				best.clear();
				maxPersone = persone;
				for(PowerOutage po : parziale) {
					best.add(po);
				}
			}
			return;
		}
			
			for(PowerOutage prova : pOutages) {
				if(aggiuntaValida(prova, parziale, anniMax, oreMax)) {
					parziale.add(prova);
					cerca(anniMax, oreMax, parziale, livello+1);
					parziale.remove(prova);
				}
			}
		}
	
	private boolean aggiuntaValida(PowerOutage prova, Set<PowerOutage> parziale, int anniMax, int oreMax) {
		int yearProva = prova.getDate_event_began().getYear();
		int contaOre = 0;
			
			for(PowerOutage p : parziale) {
			int yearP = p.getDate_event_began().getYear();
			
			if(Math.abs(yearProva - yearP)>anniMax) {
				return false;
			}
				contaOre+= p.getHours();
		}
		long hours = ChronoUnit.HOURS.between(prova.getDate_event_began(), prova.getDate_event_finished());
		if((contaOre+hours)>oreMax) {
			return false;
		}
		
		return true;
	}

	public int calcolaPersone(Set<PowerOutage> parziale) {
		int persone=0;
		for(PowerOutage po : parziale) {
			persone+=po.getCustomers_affected();
		}
		return persone;
	}

	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	public void reset() {
		maxPersone = 0;
	}

}
