package CasaSiClient;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import Simulare.Simulare;
import View.MagazinView;

public class Casa implements Runnable {
	private BlockingQueue<Client> coada;
	private AtomicInteger timpAsteptare=new AtomicInteger(0);
	private AtomicInteger timpAsteptare_total=new AtomicInteger(0);
	private AtomicInteger timpServiciu_total=new AtomicInteger(0);
	private AtomicInteger timpGol=new AtomicInteger(0);
	private MagazinView view;
	public Casa(MagazinView view) {
		this.view = view;
		coada = new ArrayBlockingQueue<Client>(100);
		timpAsteptare = new AtomicInteger(0);
	}
	public void addClient(Client c) {
		coada.add(c);
		timpAsteptare.addAndGet(c.getTimpCumparaturi());
	}
	public Client getClient() throws InterruptedException {
		return coada.take();
	}
	@Override
	public void run() {
		while(true) {
			if (!coada.isEmpty()) {
				Client client=coada.peek();
				if (timpAsteptare.intValue()-1>=0) timpAsteptare.addAndGet(-1);
				if (client!=null && client.getTimpCumparaturi()-1>=0) client.setTimpCumparaturi(client.getTimpCumparaturi()-1);
				if (client!=null && client.getTimpCumparaturi()==0) {
					coada.remove();
					view.setM_text2("Un client a iesit din coada\n");
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public BlockingQueue<Client> getCoada() {
		return coada;
	}
	public void setCoada(BlockingQueue<Client> casa) {
		this.coada = casa;
	}
	public AtomicInteger getTimpAsteptare() {
		return timpAsteptare;
	}
	public void setTimpAsteptare(AtomicInteger timpAsteptare) {
		this.timpAsteptare = timpAsteptare;
	}
	public AtomicInteger getTimpServiciu_total() {
		return timpServiciu_total;
	}
	public void setTimpServiciu_total(AtomicInteger timpServiciu_total) {
		this.timpServiciu_total = timpServiciu_total;
	}
	public AtomicInteger getTimpGol() {
		return timpGol;
	}
	public void setTimpGol(AtomicInteger timpGol) {
		this.timpGol = timpGol;
	}
	public void setTimpAsteptare_total(AtomicInteger timpAsteptare_total) {
		this.timpAsteptare_total = timpAsteptare_total;
	}
	public AtomicInteger getTimpAsteptare_total() {
		return timpAsteptare_total;
	}
	@Override
	public String toString() {
		return "Casa: "+timpAsteptare+coada;
	}
}
