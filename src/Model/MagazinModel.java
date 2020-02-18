package Model;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import CasaSiClient.Casa;
import CasaSiClient.Client;


public class MagazinModel {
	private ArrayList<Casa> caseMarcat;
	private int nrCase;
	private int intervalSimulare ;

	public MagazinModel(){
		super();
	}	
	public MagazinModel(int nrCase,int intervalSimulare ){
		this.nrCase = nrCase;
		caseMarcat = new ArrayList<Casa>();
		this.intervalSimulare = intervalSimulare;
	}
	public synchronized void leagaClient(Client c) throws InterruptedException {
		int min = Integer.MAX_VALUE;
		for(Casa casaM: caseMarcat) {
			if (casaM.getTimpAsteptare().intValue()<min) min = casaM.getTimpAsteptare().intValue();
		}
		for(Casa casaM: caseMarcat) {
			if (casaM.getTimpAsteptare().intValue() == min) {
				casaM.addClient(c);
				break;
			}
		}
	}
	public synchronized void updateTimpi() {
		for(Casa casaM: caseMarcat) {
			if (casaM.getTimpAsteptare().intValue() == 0) {
				casaM.setTimpGol(new AtomicInteger(casaM.getTimpGol().intValue()+1));
			}
		}
		for(Casa casaM: caseMarcat) {
			if (casaM.getTimpAsteptare().intValue() != 0) casaM.setTimpServiciu_total(new AtomicInteger(casaM.getTimpServiciu_total().intValue()+1));
		}
		for(Casa casaM:caseMarcat) {
			for(Client c: casaM.getCoada())
			{
				if (c != casaM.getCoada().peek()) {
					casaM.setTimpAsteptare_total(new AtomicInteger(casaM.getTimpAsteptare_total().intValue()+1));
				}
			}
		}
	}
	public int getIntervalSimulare() {
		return intervalSimulare;
	}
	public void setIntervalSimulare(int intervalSimulare) {
		this.intervalSimulare = intervalSimulare;
	}
	public ArrayList<Casa> getCaseMarcat() {
		return caseMarcat;
	}
	public void setCaseMarcat(ArrayList<Casa> caseMarcat) {
		this.caseMarcat = caseMarcat;
	}
	public void addCaseMarcat(Casa casaMarcat) {
		this.caseMarcat.add(casaMarcat);
	}
	public int getNrCase() {
		return nrCase;
	}
	public void setNrCase(int nrCase) {
		this.nrCase = nrCase;
	}
	@Override
	public String toString() {
		return "MagazinModel [caseMarcat=" + caseMarcat + ", nrCase=" + nrCase + ", intervalSimulare="
				+ intervalSimulare +  "]";
	}
}
