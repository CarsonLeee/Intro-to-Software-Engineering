package cryptoTrader.units;

import java.util.ArrayList;

public class Broker {
	
	//Fields of a Broker
	private String name;
	private ArrayList<String> coinNames;
	private IStrategy strategy;
	
	//Constructor of the Broker
	public Broker(String name) {
		this.name = name;
		coinNames = new ArrayList<String>();
		strategy = null; //no strategy so far
	}
	
	//Adds a Coin to the given Broker
	public void addCoin(String coin) {
		this.coinNames.add(coin);
	}
	
	//Returns the list of coins
	public ArrayList<String> getCoinNames() {
		return coinNames;
	}
	
	//Set the strategy for the broker
	public void setStrategy(IStrategy strategy) {
		this.strategy = strategy;
	}
	
	//Getter for name
	public String getName() {
		return name;
	}
	
	//Getter for Strategy
	public IStrategy getStrategy() {
		return strategy;
	}
	
	
	//Performs the trades according to the strategy
	public void performTrades() {
		this.strategy.performTrade(this);
	}

}
