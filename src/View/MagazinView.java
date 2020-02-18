package View;
import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import Model.MagazinModel;

public class MagazinView extends JFrame{
	private JTextField m_sosire_min = new JTextField(5);
	private JTextField m_sosire_max = new JTextField(5);
    private JTextField m_timp_serviciu_min = new JTextField(5);
    private JTextField m_timp_serviciu_max = new JTextField(5);
    private JTextField m_nrCase    = new JTextField(5);
    private JTextField m_nrClienti    = new JTextField(5);
    private JTextField m_simulare     = new JTextField(5);
    private JButton    m_ok = new JButton("OK");
    private MagazinModel m_model;
    private JTextArea m_text = new JTextArea(20, 50);
    private JTextArea m_text2 = new JTextArea(20,50);
    private JPanel content;
    public MagazinView(MagazinModel model) {
        m_model = model; 
        content = new JPanel();					
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));			
        content.add(new Label("min sosire client:"));
        content.add(m_sosire_min);
        content.add(new Label("max sosire client:"));
        content.add(m_sosire_max);
        content.add(new Label("timp necesar min:"));
        content.add(m_timp_serviciu_min);
        content.add(new Label("timp necesar max:"));
        content.add(m_timp_serviciu_max);
        content.add(new Label("nr case:"));
        content.add(m_nrCase);
        content.add(new Label("nr clienti:"));
        content.add(m_nrClienti);
        content.add(new Label("interval de simulare:"));
        content.add(m_simulare);
        content.add(m_ok);
        content.add(m_text);
        content.add(m_text2);
        JScrollPane scrollPane = new JScrollPane(m_text);
        content.add(scrollPane,BorderLayout.CENTER);
        JScrollPane scrollPane2 = new JScrollPane(m_text2);
        content.add(scrollPane2,BorderLayout.CENTER);
        m_text.setEditable(false);
        m_text2.setEditable(false);
        this.setContentPane(content);
        this.pack();
        this.setTitle("Magazin");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void showError(String errMessage) {
        JOptionPane.showMessageDialog(this, errMessage);
    }
    public void addOkListener(ActionListener ok) {
        m_ok.addActionListener(ok);
    }
	public String getM_nrClienti() {
		return m_nrClienti.getText();
	}
	public String getM_sosire_min() {
		return m_sosire_min.getText();
	}
	public String getM_sosire_max() {
		return m_sosire_max.getText();
	}
	public String getM_timp_serviciu_min() {
		return m_timp_serviciu_min.getText();
	}
	public String getM_timp_serviciu_max() {
		return m_timp_serviciu_max.getText();
	}
	public String getM_nrCase() {
		return m_nrCase.getText();
	}
	public MagazinModel getM_model() {
		return m_model;
	}
	public void setM_model(MagazinModel m_model) {
		this.m_model = m_model;
	}
	public String getM_simulare() {
		return m_simulare.getText();
	}
	public void setM_simulare(String m_simulare) {
		this.m_simulare.setText(m_simulare);
	}
	public String getM_text() {
		return m_text.getText();
	}
	public void setM_text(String m_text) {
		this.m_text.append(m_text);
	}
	public void stergeTextArea() {
		m_text.removeAll();
	}
	public String getM_text2() {
		return m_text2.getText();
	}
	public void setM_text2(String m_text) {
		this.m_text2.append(m_text);
	}
	public void stergeTextArea2() {
		m_text2.removeAll();
	}
}
