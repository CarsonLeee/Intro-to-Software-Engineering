package cryptoTrader.units;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import cryptoTrader.gui.MainUI;
import cryptoTrader.utils.DataFetcher;

public class StrategyA implements IStrategy {

	private ArrayList<String> coinList;
	private String action;
	private TResults trade;

	public void performTrade(Broker broker) {
		//Strategy A: If the ETH stock price is greater than 2500.00$ and LTC price is
		//greater than 120$, then sell 500 units of ADA
		String eth = MainUI.getInstance().getAbbvsMap().get("ETH");
		String ltc = MainUI.getInstance().getAbbvsMap().get("LTC");
		String ada = MainUI.getInstance().getAbbvsMap().get("ADA");

		coinList = new ArrayList<String>();
		coinList.add(eth);
		coinList.add(ltc);
		coinList.add(ada);

		action = "Sell";

		DataFetcher fetcher = new DataFetcher();

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Calendar cal = Calendar.getInstance();
		String dateStr = dateFormat.format(cal.getTime());
		
		//First, make sure the broker has the ETH, LTC and ADA listed
		if(broker.getCoinNames().contains(eth) && broker.getCoinNames().contains(ltc) && broker.getCoinNames().contains(ada)) {
			//If so, check the prices needed for XRP and LTC using the current date

			double priceETH = fetcher.getPriceForCoin(eth.toLowerCase(), dateStr);
			double priceLTC = fetcher.getPriceForCoin(ltc.toLowerCase(), dateStr);
			if(priceETH > 2500 && priceLTC > 120) {
				//Perform trade! We then create a String array with all the values needed
				this.trade = new TResults(broker, "ADA", dateStr, 500, fetcher.getPriceForCoin(ada.toLowerCase(), dateStr), action);
			} else {
				this.trade = new TResults(broker, "ADA", dateStr, 500, fetcher.getPriceForCoin(ada.toLowerCase(), dateStr), "Fail");
			}
		} else {
			this.trade = new TResults(broker, "ADA", dateStr, 500, fetcher.getPriceForCoin(ada.toLowerCase(), dateStr), action);
		}
	}

	//Returns the name of current Strategy
	public String getName() {
		return "Strategy-A";
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
