package org.optimizationBenchmarking.utils.chart.impl.abstr;

import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.chart.spec.IAxis;
import org.optimizationBenchmarking.utils.chart.spec.ILine2D;
import org.optimizationBenchmarking.utils.chart.spec.ILineChart2D;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.graphics.style.spec.IBasicStyles;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** The builder for two-dimensional line charts. */
public class LineChart2D extends Chart implements ILineChart2D {

  /** the x-axis type has been set */
  private static final int FLAG_HAS_X_AXIS = (Chart.FLAG_CHART_BUILDER_MAX << 1);
  /** the y-axis type has been set */
  private static final int FLAG_HAS_Y_AXIS = (LineChart2D.FLAG_HAS_X_AXIS << 1);
  /** at least one line has been added */
  private static final int FLAG_HAS_LINE = (LineChart2D.FLAG_HAS_Y_AXIS << 1);

  /** the id counter */
  private volatile int m_idCounter;

  /** the lines */
  private ArrayList<CompiledLine2D> m_lines;

  /** the internal x-axis builder */
  private Axis m_xAxis;
  /** the internal y-axis builder */
  private Axis m_yAxis;

  /**
   * create the line chart
   *
   * @param selector
   *          the selector
   */
  protected LineChart2D(final ChartSelector selector) {
    this(selector.getGraphic(), selector.getStyles(), //
        selector._getLogger(), selector.getChartDriver());
  }

  /**
   * create the line chart
   *
   * @param graphic
   *          the graphic
   * @param styles
   *          the style set to use
   * @param logger
   *          the logger to use
   * @param driver
   *          the chart driver
   */
  protected LineChart2D(final Graphics2D graphic,
      final IBasicStyles styles, final Logger logger,
      final ChartDriver driver) {
    super(graphic, styles, logger, driver);

    this.m_lines = new ArrayList<>();

    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    switch (flagValue) {
      case FLAG_HAS_X_AXIS: {
        append.append("xAxisSet"); //$NON-NLS-1$
        break;
      }
      case FLAG_HAS_Y_AXIS: {
        append.append("yAxisSet"); //$NON-NLS-1$
        break;
      }
      case FLAG_HAS_LINE: {
        append.append("hasLine"); //$NON-NLS-1$
        break;
      }
      default: {
        super.fsmFlagsAppendName(flagValue, flagIndex, append);
      }
    }
  }

  /**
   * Add a new line
   *
   * @param line
   *          the line
   * @param originalData
   *          the original data to be aggregated over
   */
  synchronized final void _addLine(final CompiledLine2D line,
      final IMatrix originalData) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.fsmFlagsAssertAndUpdate(
        (LineChart2D.FLAG_HAS_X_AXIS | LineChart2D.FLAG_HAS_Y_AXIS),
        FSM.FLAG_NOTHING, LineChart2D.FLAG_HAS_LINE, FSM.FLAG_NOTHING);

    if (line != null) {
      this.m_xAxis._registerData(originalData);
      this.m_yAxis._registerData(originalData);
      this.m_lines.add(line);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void afterChildClosed(
      final HierarchicalFSM child) {
    super.afterChildClosed(child);

    if (child == this.m_xAxis) {
      this.fsmFlagsAssertAndUpdate(FSM.STATE_NOTHING,
          LineChart2D.FLAG_HAS_X_AXIS, LineChart2D.FLAG_HAS_X_AXIS,
          FSM.FLAG_NOTHING);
      return;
    }
    if (child == this.m_yAxis) {
      this.fsmFlagsAssertAndUpdate(FSM.STATE_NOTHING,
          LineChart2D.FLAG_HAS_Y_AXIS, LineChart2D.FLAG_HAS_Y_AXIS,
          FSM.FLAG_NOTHING);
      return;
    }
    if (child instanceof Line2D) {
      return;
    }
    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    Logger logger;
    Graphics2D graphic;
    CompiledLineChart2D chart;
    Font titleFont;

    this.fsmStateAssertAndSet(ChartElement.STATE_ALIVE,
        ChartElement.STATE_DEAD);
    this.fsmFlagsAssertTrue(LineChart2D.FLAG_HAS_X_AXIS
        | LineChart2D.FLAG_HAS_Y_AXIS | LineChart2D.FLAG_HAS_LINE);

    graphic = this.m_graphic;
    logger = this.getLogger();
    try {
      if ((logger != null) && (logger.isLoggable(Level.FINEST))) {
        logger.finest("Now compiling " + this._id()); //$NON-NLS-1$
      }

      if (this.m_title != null) {
        titleFont = this.m_titleFont;
        if (titleFont == null) {
          titleFont = this.m_driver
              .getDefaultChartTitleFont(this.m_styles);
        } else {
          titleFont = this.m_driver.scaleTitleFont(this.m_titleFont);
        }
      } else {
        titleFont = null;
      }

      chart = new CompiledLineChart2D(this.m_title, titleFont,
          this.m_legendMode, this.m_xAxis._getAxis(),
          this.m_yAxis._getAxis(), new ArrayListView<>(this.m_lines
              .toArray(new CompiledLine2D[this.m_lines.size()])));
      if ((logger != null) && (logger.isLoggable(Level.FINEST))) {
        logger.finest("Now rendering the compiled " + this._id()); //$NON-NLS-1$
      }
      this.m_xAxis = null;
      this.m_yAxis = null;
      this.m_lines = null;
      this.m_driver.renderLineChart2D(chart, graphic, logger);
    } catch (final Throwable error) {
      ErrorUtils.logError(logger,
          ("Unrecoverable error during rendering of compiled 2D line chart #" //$NON-NLS-1$
              + this._id()),
          error, true, RethrowMode.AS_RUNTIME_EXCEPTION);
    } finally {
      this.m_lines = null;
      this.m_xAxis = null;
      this.m_yAxis = null;
      chart = null;
      graphic = null;
    }

    super.onClose();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final IAxis xAxis() {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.fsmFlagsAssertFalse(
        LineChart2D.FLAG_HAS_LINE | LineChart2D.FLAG_HAS_X_AXIS);
    if (this.m_xAxis != null) {
      throw new IllegalStateException(//
          "X-axis is already under construction."); //$NON-NLS-1$
    }
    return (this.m_xAxis = new Axis(this, 0));
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final IAxis yAxis() {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.fsmFlagsAssertFalse(
        LineChart2D.FLAG_HAS_LINE | LineChart2D.FLAG_HAS_Y_AXIS);
    if (this.m_yAxis != null) {
      throw new IllegalStateException(//
          "Y-axis is already under construction."); //$NON-NLS-1$
    }
    return (this.m_yAxis = new Axis(this, 1));
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final ILine2D line() {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.fsmFlagsAssertTrue(
        LineChart2D.FLAG_HAS_X_AXIS | LineChart2D.FLAG_HAS_Y_AXIS);
    return new Line2D(this, (++this.m_idCounter));
  }

}
