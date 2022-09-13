package cryptoTrader.units;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import cryptoTrader.gui.MainUI;
import cryptoTrader.utils.DataFetcher;

public class StrategyC implements IStrategy {

	private ArrayList<String> coinList;
	private String action;
	private TResults trade;

	public void performTrade(Broker broker) {
		//Strategy C: If the ETH stock price is greater than $3000 and ADA price is
		//greater than $1, then buy 2 units of BTC
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
			//If so, check the prices needed for ADA and ETH using the current date
			
			double priceETH = fetcher.getPriceForCoin(eth.toLowerCase(), dateStr);
			double priceADA = fetcher.getPriceForCoin(ada.toLowerCase(), dateStr);
			if(priceETH > 3000.0 && priceADA > 1.0) {
				//Perform trade! We then create a String array with all the values needed

				this.trade = new TResults(broker, "BTC", dateStr, 2, fetcher.getPriceForCoin(btc.toLowerCase(), dateStr), action);
			} else {
				this.trade = new TResults(broker, "BTC", dateStr, 2, fetcher.getPriceForCoin(btc.toLowerCase(), dateStr), "Fail");
			}
		} else {
			this.trade = new TResults(broker, "BTC", dateStr, 2, fetcher.getPriceForCoin(btc.toLowerCase(), dateStr), action);
		}
	}

	//Returns the name of current Strategy
	public String getName() {
		return "Strategy-C";
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
