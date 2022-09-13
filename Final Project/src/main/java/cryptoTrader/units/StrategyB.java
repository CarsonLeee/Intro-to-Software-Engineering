package cryptoTrader.units;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import cryptoTrader.gui.MainUI;
import cryptoTrader.utils.DataFetcher;

public class StrategyB implements IStrategy {

	private ArrayList<String> coinList;
	private String action;
	private TResults trade;
	
	public void performTrade(Broker broker) {
		//Strategy B: If the BTC stock price is less than 57,000$ and ADA price is
		//less than 2$, then buy 15 units of ETH
		String btc = MainUI.getInstance().getAbbvsMap().get("BTC");
		String ada = MainUI.getInstance().getAbbvsMap().get("ADA");
		String eth = MainUI.getInstance().getAbbvsMap().get("ETH");
		
		coinList = new ArrayList<String>();
		coinList.add(btc);
		coinList.add(eth);
		coinList.add(ada);
		
		action = "Buy";
		
		DataFetcher fetcher = new DataFetcher();
		
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Calendar cal = Calendar.getInstance();
		String dateStr = dateFormat.format(cal.getTime());
		
		//First, make sure the broker has the BTC, ADA and ETH listed
		if(broker.getCoinNames().contains(btc) && broker.getCoinNames().contains(ada) && broker.getCoinNames().contains(eth)) {
			//If so, check the prices needed for BTC and ADA using the current date

			
			double priceBTC = fetcher.getPriceForCoin(btc.toLowerCase(), dateStr);
			double priceADA = fetcher.getPriceForCoin(ada.toLowerCase(), dateStr);
			if(priceBTC < 57000.0 && priceADA < 2.0) {
				//Perform trade! We then create a String array with all the values needed
				
				this.trade = new TResults(broker, "ETH", dateStr, 15, fetcher.getPriceForCoin(eth.toLowerCase(), dateStr), action);
			} else {
				this.trade = new TResults(broker, "ETH", dateStr, 15, fetcher.getPriceForCoin(eth.toLowerCase(), dateStr), "Fail");
			}
		} else {
			this.trade = new TResults(broker, "ETH", dateStr, 15, fetcher.getPriceForCoin(eth.toLowerCase(), dateStr), action);
		}
	}

	//Returns the name of current Strategy
	public String getName() {
		return "Strategy-B";
	}

	public String getAction() {
		return action;
	}
	
	public TResults getTResults() {
		return trade;
	}
	public ArrayList<String> getCoinList() {
		return coinList;
	}

}
