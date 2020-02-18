package Simulare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

import CasaSiClient.Casa;
import CasaSiClient.Client;
import Model.MagazinModel;
import View.MagazinView;

public class Simulare implements Runnable{
	public int timpSimulare=0;
	public int timpProcesareMax = 0;
	public int timpProcesareMin = 0;
	public int nrCase = 0;
	public int nrClienti = 0;
	public int timpSosireMin = 0;
	public int timpSosireMax = 0;
	
	private MagazinModel model;
	private MagazinView view;
	private ArrayList<Client> clientiGenerati;
	private int timp_varf = 0;
	
	public Simulare(int timpSimulare,int timpSosireMin,int timpSosireMax,int timpProcesareMin,int timpProcesareMax,int nrCase,int nrClienti,MagazinView view) throws Exception {
		this.timpSimulare = timpSimulare;
		this.timpSosireMin = timpSosireMin;
		this.timpSosireMax = timpSosireMax;
		this.timpProcesareMax = timpProcesareMax;
		this.timpProcesareMin = timpProcesareMin;
		this.nrCase = nrCase;
		this.nrClienti = nrClienti;
		this.view = view;
		view.stergeTextArea();
		view.stergeTextArea2();
		model = new MagazinModel(nrCase,timpSimulare);
		for(int i=0;i<nrCase;i++) {
			Casa c = new Casa(view);
			Thread t = new Thread(c);
			t.start();
			model.addCaseMarcat(c);
		}
		clientiGenerati = new ArrayList<Client>();
		validare();
		generareClienti();
	}
	public void validare() throws Exception{
		if (timpSimulare<=0 || timpSosireMin<0 || timpSosireMax<0 || timpProcesareMin<0 || timpProcesareMax<0 || nrCase<=0 || nrClienti<=0) throw new Exception("Eroare: nr negative");
		if (timpSosireMax< timpSosireMin || timpProcesareMax < timpProcesareMin) throw new Exception("Eroare: timpi max < timpi min");
	}
	public void generareClienti() throws Exception {
		int i=0;
		if (nrClienti > timpSosireMax - timpSosireMin+1) throw new Exception("Eroare: prea multi clienti");
		else if (nrClienti == timpSosireMax - timpSosireMin+1) 
		{
			while(i<nrClienti) {
				double r = Math.random();
				int timp_c = (int)(r*(timpProcesareMax-timpProcesareMin)+timpProcesareMin);
				int timp_v = i+timpSosireMin;
				Client c = new Client(timp_v,timp_c+1);
				this.clientiGenerati.add(c);
				i++;
			}
		}else {
			while(i<nrClienti) {
				double r = Math.random();
				int timp_c = (int)(r*(timpProcesareMax-timpProcesareMin)+timpProcesareMin);
				int timp_v = (int)((timpSosireMax-timpSosireMin)*r+timpSosireMin);
				boolean gasit = false;
				for(Client client: clientiGenerati)
					if ( client.getTimpVenire() == timp_v) gasit = true;
				if (gasit == false) {
					Client c = new Client(timp_v,timp_c+1);
					this.clientiGenerati.add(c);
					i++;
				}
			}
		}
			Collections.sort(clientiGenerati);
			System.out.println(clientiGenerati);
	}
	@Override
	public void run() {
		int currentTime = 1;
		this.timpSimulare = model.getIntervalSimulare();
		view.stergeTextArea2();
		int dim_max = -1;
		while(currentTime<=timpSimulare) {
			String deAfisat2 = new String("");
			System.out.println("timp: "+currentTime);
				if (!clientiGenerati.isEmpty() &&clientiGenerati.get(0).getTimpVenire() == currentTime) {
					try {
						model.leagaClient(clientiGenerati.get(0));
						deAfisat2+="S-a adaugat un client in coada\n";
						view.setM_text2(deAfisat2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("Client "+clientiGenerati.get(0).getTimpVenire()+" "+clientiGenerati.get(0).getTimpCumparaturi());
					clientiGenerati.remove(0);
				}
				model.updateTimpi();
				int dim = 0;
				for(Casa casaM:  model.getCaseMarcat()) {
					dim += casaM.getCoada().size();
				}
				if (dim> dim_max) {
					timp_varf = currentTime;
					dim_max = dim;
				}
				afisare();
				currentTime++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		view.setM_text2("\n");
		afisare_final();
	}
	public void afisare() {
		String deAfisat = new String("");
		for(Casa coada: model.getCaseMarcat()) {
			System.out.println(coada);
			deAfisat+=(" "+coada+"\n");
		}
		deAfisat+="\n";
		view.stergeTextArea();
		view.setM_text(deAfisat+"\n");
	}
	public void afisare_final() {
		view.stergeTextArea2();
		System.out.println("TERMINARE");
		String afis=new String("TERMINARE\n");
		//int oraVarf = 0;
		for(Casa coada: model.getCaseMarcat()) {
			System.out.println("CASA:");
			System.out.println("A fost goala "+coada.getTimpGol()+" secunde => "+(double)coada.getTimpGol().intValue()/timpSimulare);
			System.out.println("S-a asteptat "+coada.getTimpAsteptare_total()+" secunde => "+(double)coada.getTimpAsteptare_total().intValue()/timpSimulare);
			System.out.println("S-a lucrat "+coada.getTimpServiciu_total()+" secunde => "+(double)coada.getTimpServiciu_total().intValue()/timpSimulare);
			afis+="CASA:"+"\n";
			afis+=("A fost goala "+coada.getTimpGol()+" secunde => "+(double)coada.getTimpGol().intValue()/timpSimulare+"\n");
			afis+=("S-a asteptat "+coada.getTimpAsteptare_total()+" secunde => "+(double)coada.getTimpAsteptare_total().intValue()/timpSimulare+"\n");
			afis+=("S-a lucrat "+coada.getTimpServiciu_total()+" secunde => "+(double)coada.getTimpServiciu_total().intValue()/timpSimulare+"\n");
		}
		System.out.println("Ora de varf: "+timp_varf+"\n");
		afis+=("\n Ora de varf: "+timp_varf+"\n");
		view.setM_text2(afis);
	}
	public int getTimpSimulare() {
		return timpSimulare;
	}
}
