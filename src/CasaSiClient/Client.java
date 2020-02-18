package CasaSiClient;

public class Client implements Comparable{
	private int timpVenire;
	private int timpCumparaturi;
	public Client(int timpv,int timpc){
		this.timpVenire = timpv;
		this.timpCumparaturi = timpc;
	}
	public int getTimpVenire() {
		return timpVenire;
	}
	public void setTimpVenire(int timpVenire) {
		this.timpVenire = timpVenire;
	}
	public int getTimpCumparaturi() {
		return timpCumparaturi;
	}
	public void setTimpCumparaturi(int timpCumparaturi) {
		this.timpCumparaturi = timpCumparaturi;
	}
	@Override
	public String toString() {
		return "☺ "+ timpCumparaturi;
	}
	@Override
	public int compareTo(Object arg0) {
		if (this.getTimpVenire()<((Client) arg0).getTimpVenire()) return -1;
		else if (this.getTimpVenire()>((Client) arg0).getTimpVenire()) return 1;
		return 0;
	}
}
