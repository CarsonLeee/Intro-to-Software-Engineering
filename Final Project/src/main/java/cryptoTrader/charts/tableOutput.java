package cryptoTrader.charts;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import cryptoTrader.gui.MainUI;

public class TableOutput implements ICreator {

	@Override
	public void createOutput() {
		//Get instance of main GUI
		MainUI gui = MainUI.getInstance();
		//We start declaring the column names
		Object[] columnNames = {"Trader","Strategy","CryptoCoin","Action","Quantity","Price","Date"};
		
		//Now, we loop through all trades to add them to the data object array
		Object[][] data = new Object[gui.getTrades().size()][7];
		for(int i = 0; i < gui.getTrades().size(); i++) {
			//We copy the trades to avoid losing information
			String[] tradeStr = gui.getTrades().get(i);
			Object[] trade = {tradeStr[0], tradeStr[1], tradeStr[2], tradeStr[3], tradeStr[4],
					tradeStr[5], tradeStr[6]};
			data[i] = trade;
			
		}

		JTable table = new JTable(data, columnNames);
		//table.setPreferredSize(new Dimension(600, 300));

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
				"Trader Actions",
				TitledBorder.CENTER,
				TitledBorder.TOP));


		scrollPane.setPreferredSize(new Dimension(800, 300));
		table.setFillsViewportHeight(true);;

		MainUI.getInstance().updateStats(scrollPane);

	}

}
