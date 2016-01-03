package org.optimizationBenchmarking.utils.chart.impl.abstr;

import java.awt.Graphics2D;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.chart.spec.IChartSelector;
import org.optimizationBenchmarking.utils.graphics.style.spec.IBasicStyles;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJob;

/** The selector the chart to create */
public class ChartSelector extends ToolJob implements IChartSelector {

  /** the graphic to use */
  private final Graphics2D m_graphic;

  /** the style set to use */
  private final IBasicStyles m_styles;

  /** the chart driver */
  private final ChartDriver m_driver;

  /**
   * create the chart selector
   *
   * @param builder
   *          the chart builder
   */
  public ChartSelector(final ChartBuilder builder) {
    this(builder.getGraphic(), builder.getStyles(),
        builder.getChartDriver(), builder.getLogger());
  }

  /**
   * create the chart selector
   *
   * @param graphic
   *          the graphic
   * @param styles
   *          the style set
   * @param driver
   *          the chart driver
   * @param logger
   *          the logger
   */
  protected ChartSelector(final Graphics2D graphic,
      final IBasicStyles styles, final ChartDriver driver,
      final Logger logger) {
    super(logger);
    ChartBuilder._checkGraphic(graphic);
    ChartBuilder._checkBasicStyles(styles);
    ChartBuilder._checkChartDriver(driver);
    this.m_graphic = graphic;
    this.m_styles = styles;
    this.m_driver = driver;
  }

  /**
   * get the logger
   *
   * @return the logger
   */
  final Logger _getLogger() {
    return this.getLogger();
  }

  /**
   * Get the graphic to draw the chart on
   *
   * @return the graphic to draw the chart on
   */
  protected final Graphics2D getGraphic() {
    return this.m_graphic;
  }

  /**
   * Get the style set to be used for the chart
   *
   * @return the style set to be used for the chart
   */
  protected final IBasicStyles getStyles() {
    return this.m_styles;
  }

  /**
   * Get the chart driver
   *
   * @return the chart driver
   */
  public final ChartDriver getChartDriver() {
    return this.m_driver;
  }

  /** {@inheritDoc} */
  @Override
  public final LineChart2D lineChart2D() {
    return new LineChart2D(this);
  }

  /** {@inheritDoc} */
  @Override
  public final PieChart pieChart() {
    return new PieChart(this);
  }
}
