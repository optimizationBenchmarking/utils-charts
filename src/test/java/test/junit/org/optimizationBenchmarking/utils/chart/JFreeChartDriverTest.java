package test.junit.org.optimizationBenchmarking.utils.chart;

import org.optimizationBenchmarking.utils.chart.impl.jfree.JFreeChartDriver;

import shared.junit.org.optimizationBenchmarking.utils.chart.ChartDriverTest;

/** Test whether JFreeChart can be used */
public class JFreeChartDriverTest extends ChartDriverTest {

  /** create */
  public JFreeChartDriverTest() {
    super(JFreeChartDriver.getInstance());
  }
}
