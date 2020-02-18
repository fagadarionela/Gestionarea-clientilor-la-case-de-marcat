package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import Model.MagazinModel;
import Simulare.Simulare;
import View.MagazinView;

public class MagazinController {
		private MagazinModel model;
	    private MagazinView  view;
	    int intervalSosireClienti;
		int intervalTimpNecesar ;
		int intervalSimulare ;
		public int sosireClientiMin;
		public int sosireClientiMax;
		public int serviciuMin;
		public int nrCase;
		public int serviciuMax;
		public int  nrClienti;
	    public MagazinController(MagazinModel model,MagazinView view) throws IOException {
	        this.model = model;
	    	this.view  = view;
	        view.addOkListener(new OkListener());
	    }
	    class OkListener implements ActionListener{
	    	public void actionPerformed(ActionEvent e) {
	    		try {
	    			sosireClientiMin = Integer.parseInt(view.getM_sosire_min());
	    			sosireClientiMax = Integer.parseInt(view.getM_sosire_max());
	    			serviciuMin = Integer.parseInt(view.getM_timp_serviciu_min());	
	    			serviciuMax = Integer.parseInt(view.getM_timp_serviciu_max());
	    			nrCase = Integer.parseInt(view.getM_nrCase());	
	    			nrClienti = Integer.parseInt(view.getM_nrClienti());
	    			intervalSimulare = Integer.parseInt(view.getM_simulare());	    	
	    			Simulare s = new Simulare(intervalSimulare,sosireClientiMin,sosireClientiMax,serviciuMin,serviciuMax,nrCase,nrClienti,view);
	    			Thread t = new Thread(s);
	    			t.start();
	    		}
	    		catch(NumberFormatException nfex) {
	    			view.showError("Bad Input");						
	    		} catch (IOException e1) {
					e1.printStackTrace();
				}
	    		catch (Exception exc) {
	    			view.showError(exc.getMessage());
	    		}
	    	}
	    }  
}
