package cryptoTrader.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import cryptoTrader.gui.MainUI;
import cryptoTrader.units.Broker;
import cryptoTrader.units.TResults;


import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;

public class DataVisualizationCreator {
	
	//creating required charts using the input of brokers
	public void createCharts(ArrayList<Broker> brokers) {
		
		ArrayList<TResults> results = new ArrayList<TResults>();
				
		for(int i = 0; i < brokers.size(); i++) {
			brokers.get(i).performTrades();
			results.add(brokers.get(i).getStrategy().getTResults());
		}
		
		tableOutput(results);
		barOutput(brokers);
	}
	
	private void tableOutput(ArrayList<TResults> TResults) {
		Object[] columnNames = {"Trader","Strategy","CryptoCoin","Action","Quantity","Price","Date"};
				
		
		Object data[][] = new Object[TResults.size()][7];
		
		for(int i = 0; i < TResults.size(); i++) {
			data[i] = TResults.get(i).convertToString();
		}
		

		JTable table = new JTable(data, columnNames);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),"Trader Actions",TitledBorder.CENTER,TitledBorder.TOP));
	
		scrollPane.setPreferredSize(new Dimension(850, 400));
		table.setFillsViewportHeight(true);
		
		MainUI.getInstance().updateStats(scrollPane);
	}
	
	private void barOutput(ArrayList<Broker> brokers) {
		
		DefaultCategoryDataset setdata = new DefaultCategoryDataset();
		int maxResults = 0;
		
		for(int i = 0; i < brokers.size(); i++) {
			if((brokers.get(i).getStrategy().getTResults().action).equals("Fail") == false) {
				if(brokers.get(i).getStrategy().getTResults().amount > maxResults) {
					maxResults = brokers.get(i).getStrategy().getTResults().amount;
				}
				setdata.setValue(brokers.get(i).getStrategy().getTResults().amount, brokers.get(i).getName(), brokers.get(i).getStrategy().getName());	
			}			
		}

		int maxXAxis = (int)Math.ceil(maxResults*1.40);
		if(maxXAxis == 0) maxXAxis = 1;

		CategoryPlot plot = new CategoryPlot();
		BarRenderer barRender = new BarRenderer();

		CategoryAxis xAxis = new CategoryAxis("Strategy Name");
		NumberAxis yAxis = new NumberAxis("Actions (Buying or Selling)");
		
		yAxis.setRange(new Range(0.0, maxXAxis));
		
		plot.setDataset(0, setdata);
		plot.setRenderer(0, barRender);
		plot.setDomainAxis(xAxis);
		plot.setRangeAxis(yAxis);

		JFreeChart bar = new JFreeChart("Actions Performed By Traders So Far", new Font("Arial", java.awt.Font.BOLD, 16), plot, true);

		ChartPanel chartPanel = new ChartPanel(bar);
		
		chartPanel.setPreferredSize(new Dimension(700, 400));
		chartPanel.setBackground(Color.white);
		chartPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
		
		MainUI.getInstance().updateStats(chartPanel);
	}
}
