package cryptoTrader.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;

import javax.swing.BorderFactory;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;

import cryptoTrader.gui.MainUI;

public class BarOutput implements ICreator {

	@Override
	public void createOutput() {
		//Init dataset to hold all data
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		//Populate the dataset with the real data from strategies and brokers
		HashMap<String, HashMap<String, Integer>> bars = MainUI.getInstance().getBars();
		for(String strategy : bars.keySet()) {
			HashMap<String, Integer> counts = bars.get(strategy);
			for(String name : counts.keySet()) {
				int count = counts.get(name);
				dataset.setValue(count, name, strategy);
			}
		}
		
		//Render and plot
		CategoryPlot plot = new CategoryPlot();
		BarRenderer barrenderer1 = new BarRenderer();

		plot.setDataset(0, dataset);
		plot.setRenderer(0, barrenderer1);
		CategoryAxis domainAxis = new CategoryAxis("Strategy");
		plot.setDomainAxis(domainAxis);
		LogAxis rangeAxis = new LogAxis("Actions(Buys or Sells)");
		rangeAxis.setRange(new Range(1.0, 20.0));
		plot.setRangeAxis(rangeAxis);

		JFreeChart barChart = new JFreeChart("Actions Performed By Traders So Far", new Font("Serif", java.awt.Font.BOLD, 18), plot,
				true);

		ChartPanel chartPanel = new ChartPanel(barChart);
		chartPanel.setPreferredSize(new Dimension(600, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		chartPanel.setBackground(Color.white);
		MainUI.getInstance().updateStats(chartPanel);
	}
}
