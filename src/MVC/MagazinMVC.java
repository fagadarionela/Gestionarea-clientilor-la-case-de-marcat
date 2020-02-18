package MVC;

import java.io.IOException;

import Controller.MagazinController;
import Model.MagazinModel;
import View.MagazinView;

public class MagazinMVC {
	public static void main(String[] args) throws IOException {
        MagazinModel    model      = new MagazinModel();
        MagazinView     view     = new MagazinView(model);
        MagazinController controller = new MagazinController(model, view);
        view.setVisible(true);
}
}
