package org.optimizationBenchmarking.utils.chart.impl.abstr;

import java.awt.Graphics2D;

import org.optimizationBenchmarking.utils.chart.spec.IChartBuilder;
import org.optimizationBenchmarking.utils.graphics.style.spec.IBasicStyles;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJobBuilder;

/**
 * A builder for creating the chart selector.
 */
public class ChartBuilder extends
    ToolJobBuilder<ChartSelector, ChartBuilder> implements IChartBuilder {

  /** the chart driver */
  private final ChartDriver m_driver;

  /** the graphic to use */
  private Graphics2D m_graphic;

  /** the basic style set to use */
  private IBasicStyles m_styles;

  /**
   * create the chart builder
   *
   * @param driver
   *          the chart driver
   */
  protected ChartBuilder(final ChartDriver driver) {
    super();
    ChartBuilder._checkChartDriver(driver);
    this.m_driver = driver;
  }

  /**
   * check whether a graphic parameter is valid
   *
   * @param graphic
   *          the graphic parameter
   */
  static final void _checkGraphic(final Graphics2D graphic) {
    if (graphic == null) {
      throw new IllegalArgumentException(//
          "The graphic for a chart cannot be null."); //$NON-NLS-1$
    }
  }

  /**
   * check whether a chart driver is valid
   *
   * @param driver
   *          the chart driver to check
   */
  static final void _checkChartDriver(final ChartDriver driver) {
    if (driver == null) {
      throw new IllegalArgumentException("Chart driver cannot be null.");//$NON-NLS-1$
    }
  }

  /**
   * check whether a style set for a chart is valid
   *
   * @param styles
   *          the basic style set
   */
  static final void _checkBasicStyles(final IBasicStyles styles) {
    if (styles == null) {
      throw new IllegalArgumentException(//
          "Basic styles for a chart cannot be null.");//$NON-NLS-1$
    }
    if (styles.getDefaultFont() == null) {
      throw new IllegalArgumentException(//
          "Basic styles for a chart cannot have null default font.");//$NON-NLS-1$
    }
    if (styles.getDefaultStroke() == null) {
      throw new IllegalArgumentException(//
          "Basic styles for a chart cannot have null default stroke.");//$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final ChartBuilder setGraphic(final Graphics2D graphic) {
    ChartBuilder._checkGraphic(graphic);
    this.m_graphic = graphic;
    return this;
  }

  /**
   * Get the graphic to draw the chart on
   *
   * @return the graphic to draw the chart on
   * @see #setGraphic(Graphics2D)
   */
  public final Graphics2D getGraphic() {
    return this.m_graphic;
  }

  /** {@inheritDoc} */
  @Override
  public final ChartBuilder setStyles(final IBasicStyles styles) {
    ChartBuilder._checkBasicStyles(styles);
    this.m_styles = styles;
    return this;
  }

  /**
   * Get the style set to be used for the chart
   *
   * @return the style set to be used for the chart
   */
  public final IBasicStyles getStyles() {
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
  public ChartSelector create() {
    return new ChartSelector(this);
  }
}
