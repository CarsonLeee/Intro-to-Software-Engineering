package cryptoTrader.units;

import java.util.ArrayList;

public interface IStrategy {
	
	//Performs the trades needed according to the strategy defined and the broker passed
	public void performTrade(Broker broker);
	
	public String getAction();
	
	//Returns the name of the Strategy
	public String getName();

	public ArrayList<String> getCoinList();

	public TResults getTResults();
}
