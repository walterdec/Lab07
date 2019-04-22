package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.db.PowerOutageDAO;

public class Model {

	PowerOutageDAO podao;
	List<PowerOutage> best ;
	List<PowerOutage> pOutages;
	
	int maxPersone = 0;
	
	public Model() {
		podao = new PowerOutageDAO();
		pOutages = new ArrayList<PowerOutage>();
	}
	
	public List<PowerOutage> cercaSoluzione(Nerc selectedNerc, int anniMax, int oreMax){
		List<PowerOutage> parziale = new ArrayList<PowerOutage>();
		pOutages= podao.getOutages(selectedNerc);
		best = new ArrayList<PowerOutage>();
		
		cerca(anniMax, oreMax, parziale);
		
		return best;
		
	}
	
	private void cerca(int anniMax, int oreMax, List<PowerOutage> parziale) {
			
			for(PowerOutage prova : pOutages) {
				if(aggiuntaValida(prova, parziale, anniMax, oreMax)) {
					parziale.add(prova);
					cerca(anniMax, oreMax, parziale);
					parziale.remove(parziale.size()-1);
					}
				}
			
				//terminazione
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
	
	private boolean aggiuntaValida(PowerOutage prova, List<PowerOutage> parziale, int anniMax, int oreMax) {
		int yearProva = prova.getDate_event_began().getYear();
		int contatoreOre = 0;
			
			for(PowerOutage p : parziale) {
				if(p.getId()==prova.getId())
					return false;
				
				int yearP = p.getDate_event_began().getYear();
			
				if(Math.abs(yearProva - yearP)>anniMax) {
					return false;
				}
				contatoreOre+= p.getHours();
			}
			
		long hours = prova.getHours();
		
		if((contatoreOre+hours)>oreMax) {
			return false;
		}
		return true;
	}

	public int calcolaPersone(List<PowerOutage> parziale) {
		int persone=0;
		for(PowerOutage po : parziale) {
			persone+=po.getCustomers_affected();
		}
		return persone;
	}
	
	public long contaOre(List<PowerOutage> parziale) {
		long ore = 0;
		for(PowerOutage po : parziale) {
			ore+=po.getHours();
		}
		return ore;
	}

	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	public void reset() {
		maxPersone = 0;
	}

}
