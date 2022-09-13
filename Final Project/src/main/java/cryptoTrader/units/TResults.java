/**
 * 
 */
package cryptoTrader.units;

import java.util.ArrayList;

public class TResults {
	public final Broker broker;
	public final String coin;
	public final String date;
	public final int amount;
	public final double cost;
	public String action;
	private ArrayList<String> coinList;
	
	public TResults(Broker broker, String coin, String date, int amount, double cost, String action) {
		this.broker = broker;
		this.coin = coin;
		this.date = date;
		this.amount = amount;
		this.cost = cost;
		this.action = action;
	}
	
	public Object[] convertToString() {
		coinList = broker.getCoinNames();
		ArrayList<String> compare = broker.getStrategy().getCoinList();
		boolean f = true;
		
		for(int i = 0; i < compare.size(); i++) {
			if(!coinList.contains(compare.get(i)))
				f = false;
		}
		
		if(action.equals("Fail")) {
			f = false;
		}
		
		
		if(f == false) {
			this.action = "Fail";
			Object[] results = {broker.getName(), broker.getStrategy().getName(), coin, "FAIL", "NULL", "NULL", date};
			return results;
		} else {
			Object[] results = {broker.getName(), broker.getStrategy().getName(), coin, action, String.valueOf(amount), String.valueOf(cost), date};
			return results;
		}
		
	}
	
}
