package org.optimizationBenchmarking.utils.chart.impl.abstr;

import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.chart.spec.IAxis;
import org.optimizationBenchmarking.utils.graphics.style.spec.IBasicStyles;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.IAggregate;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * The base class for all axes
 */
final class Axis extends TitledElement implements IAxis {

  /** the tick font has been set */
  private static final int FLAG_HAS_TICK_FONT = (TitledElement.FLAG_TITLED_ELEMENT_BUILDER_MAX << 1);
  /** the stroke has been set */
  private static final int FLAG_HAS_STROKE = (Axis.FLAG_HAS_TICK_FONT << 1);
  /** the color has been set */
  private static final int FLAG_HAS_COLOR = (Axis.FLAG_HAS_STROKE << 1);
  /** the grid line stroke has been set */
  private static final int FLAG_HAS_GRID_LINE_STROKE = (Axis.FLAG_HAS_COLOR << 1);
  /** the grid line color has been set */
  private static final int FLAG_HAS_GRID_LINE_COLOR = (Axis.FLAG_HAS_GRID_LINE_STROKE << 1);
  /** the minimum has been set */
  private static final int FLAG_HAS_MIN = (Axis.FLAG_HAS_GRID_LINE_COLOR << 1);
  /** the maximum has been set */
  private static final int FLAG_HAS_MAX = (Axis.FLAG_HAS_MIN << 1);

  /** the minimum aggregate */
  private Number m_minimum;

  /** the maximum aggregate */
  private Number m_maximum;

  /** the axis stroke */
  private Stroke m_axisStroke;

  /** the axis color */
  private Color m_axisColor;

  /** the grid line stroke */
  private Stroke m_gridLineStroke;

  /** the grid line color */
  private Color m_gridLineColor;

  /** the aggregate */
  private IAggregate m_aggregate;

  /** the tick font */
  private Font m_tickFont;

  /** the column this axis is responsible for */
  private final int m_col;

  /**
   * create the chart item
   *
   * @param owner
   *          the owner
   * @param col
   *          the column this axis is responsible for
   */
  protected Axis(final Chart owner, final int col) {
    super(owner);
    if (owner == null) {
      throw new IllegalArgumentException(//
          "Chart owning an axis cannot be null."); //$NON-NLS-1$
    }
    this.m_col = col;

    final Logger logger;

    logger = this.getLogger();
    if ((logger != null) && (logger.isLoggable(Level.FINEST))) {
      logger.finest("Beginning to build " + //$NON-NLS-1$
          this.getClass().getSimpleName() + //
          " for column " + col + //$NON-NLS-1$
          " for " + owner._id()); //$NON-NLS-1$
    }

    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final Logger getLogger() {
    return this.getOwner().getLogger();
  }

  /** {@inheritDoc} */
  @Override
  protected final Chart getOwner() {
    return ((Chart) (super.getOwner()));
  }

  /** {@inheritDoc} */
  @Override
  protected final void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    switch (flagValue) {
      case FLAG_HAS_TICK_FONT: {
        append.append("tickFontSet"); //$NON-NLS-1$
        break;
      }
      case FLAG_HAS_STROKE: {
        append.append("axisStrokeSet"); //$NON-NLS-1$
        break;
      }
      case FLAG_HAS_COLOR: {
        append.append("axisColorSet"); //$NON-NLS-1$
        break;
      }
      case FLAG_HAS_GRID_LINE_COLOR: {
        append.append("gridLineColorSet"); //$NON-NLS-1$
        break;
      }
      case FLAG_HAS_GRID_LINE_STROKE: {
        append.append("gridLineStrokeSet"); //$NON-NLS-1$
        break;
      }
      case FLAG_HAS_MIN: {
        append.append("minimumSet"); //$NON-NLS-1$
        break;
      }
      case FLAG_HAS_MAX: {
        append.append("maximumSet"); //$NON-NLS-1$
        break;
      }
      default: {
        super.fsmFlagsAppendName(flagValue, flagIndex, append);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void setAxisColor(final Color axisColor) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING, Axis.FLAG_HAS_COLOR,
        Axis.FLAG_HAS_COLOR, FSM.FLAG_NOTHING);
    if (axisColor == null) {
      throw new IllegalArgumentException(//
          "CompiledAxis color cannot be set to null. If you don't want to specify a title font, don't set it."); //$NON-NLS-1$
    }
    this.m_axisColor = axisColor;
  }

  /** {@inheritDoc} */
  @Override
  public final synchronized void setTickFont(final Font tickFont) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING, Axis.FLAG_HAS_TICK_FONT,
        Axis.FLAG_HAS_TICK_FONT, FSM.FLAG_NOTHING);
    if (tickFont == null) {
      throw new IllegalArgumentException(//
          "Tick font cannot be set to null. If you don't want to specify a title font, don't set it."); //$NON-NLS-1$
    }
    this.m_tickFont = tickFont;
  }

  /** {@inheritDoc} */
  @Override
  public final synchronized void setAxisStroke(final Stroke axisStroke) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING, Axis.FLAG_HAS_STROKE,
        Axis.FLAG_HAS_STROKE, FSM.FLAG_NOTHING);
    if (axisStroke == null) {
      throw new IllegalArgumentException(//
          "CompiledAxis stroke cannot be set to null. If you don't want to specify a title font, don't set it."); //$NON-NLS-1$
    }
    this.m_axisStroke = axisStroke;
  }

  /** {@inheritDoc} */
  @Override
  public final synchronized void setGridLineStroke(
      final Stroke gridLineStroke) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        Axis.FLAG_HAS_GRID_LINE_STROKE, Axis.FLAG_HAS_GRID_LINE_STROKE,
        FSM.FLAG_NOTHING);
    if (gridLineStroke == null) {
      throw new IllegalArgumentException(//
          "Grid line stroke cannot be set to null. If you don't want to specify a title font, don't set it."); //$NON-NLS-1$
    }
    this.m_gridLineStroke = gridLineStroke;
  }

  /** {@inheritDoc} */
  @Override
  public final synchronized void setGridLineColor(
      final Color gridLineColor) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        Axis.FLAG_HAS_GRID_LINE_COLOR, Axis.FLAG_HAS_GRID_LINE_COLOR,
        FSM.FLAG_NOTHING);
    if (gridLineColor == null) {
      throw new IllegalArgumentException(//
          "Grid line color cannot be set to null. If you don't want to specify a title font, don't set it."); //$NON-NLS-1$
    }
    this.m_gridLineColor = gridLineColor;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void setMinimum(final Number minimum) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    if (minimum == null) {
      throw new IllegalArgumentException(
          "Minimum aggregate cannot be null.");//$NON-NLS-1$
    }
    if (minimum instanceof IAggregate) {
      if (minimum == this.m_maximum) {
        throw new IllegalArgumentException(//
            "Cannot set minimum aggregate to same value as maximum aggregate, i.e., " //$NON-NLS-1$
                + minimum);
      }
    } else {
      Axis._assertMin(minimum.doubleValue());
    }

    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING, Axis.FLAG_HAS_MIN,
        Axis.FLAG_HAS_MIN, FSM.FLAG_NOTHING);
    this.m_minimum = minimum;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void setMaximum(final Number maximum) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    if (maximum == null) {
      throw new IllegalArgumentException(
          "Maximum aggregate cannot be null.");//$NON-NLS-1$
    }
    if (maximum instanceof IAggregate) {
      if (maximum == this.m_minimum) {
        throw new IllegalArgumentException(//
            "Cannot set maximum aggregate to same value as minimum aggregate, i.e., " //$NON-NLS-1$
                + maximum);
      }
    } else {
      Axis._assertMax(maximum.doubleValue());
    }
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING, Axis.FLAG_HAS_MAX,
        Axis.FLAG_HAS_MAX, FSM.FLAG_NOTHING);
    this.m_maximum = maximum;
  }

  /**
   * check a minimum value
   *
   * @param min
   *          the minimum value
   */
  static final void _assertMin(final double min) {
    if ((min != min) || (min >= Double.POSITIVE_INFINITY)) {
      throw new IllegalArgumentException(
          min + " is not a valid minimum value.");//$NON-NLS-1$
    }
  }

  /**
   * check a maximum value
   *
   * @param max
   *          the maximum value
   */
  static final void _assertMax(final double max) {
    if ((max != max) || (max <= Double.NEGATIVE_INFINITY)) {
      throw new IllegalArgumentException(
          max + " is not a valid maximum value.");//$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void setMinimum(final double minimum) {
    Axis._assertMin(minimum);
    this.setMinimum(NumericalTypes.valueOf(minimum));
  }

  /** {@inheritDoc} */
  @Override
  public final void setMaximum(final double maximum) {
    Axis._assertMax(maximum);
    this.setMaximum(NumericalTypes.valueOf(maximum));
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    this.fsmFlagsAssertTrue(Axis.FLAG_HAS_MAX | Axis.FLAG_HAS_MIN);

    final Logger logger;

    if (this.m_maximum instanceof IAggregate) {
      if (this.m_minimum instanceof IAggregate) {
        this.m_aggregate = new __TwoAggregates(//
            ((IAggregate) (this.m_minimum)),
            ((IAggregate) (this.m_maximum)));
      } else {
        this.m_aggregate = ((IAggregate) (this.m_maximum));
      }
    } else {
      if (this.m_minimum instanceof IAggregate) {
        this.m_aggregate = ((IAggregate) (this.m_minimum));
      }
    }

    logger = this.getLogger();
    if ((logger != null) && (logger.isLoggable(Level.FINEST))) {
      logger.finest("Finished building " + //$NON-NLS-1$
          this.getClass().getSimpleName() + //
          " for column " + this.m_col + //$NON-NLS-1$
          " for " + this.getOwner()._id()); //$NON-NLS-1$
    }

    super.onClose();
  }

  /**
   * Register some data
   *
   * @param data
   *          the data set
   */
  final void _registerData(final IMatrix data) {
    if (this.m_aggregate != null) {
      data.aggregateColumn(this.m_col, this.m_aggregate);
    }
  }

  /**
   * get the axis
   *
   * @return the axis
   */
  @SuppressWarnings("resource")
  final CompiledAxis _getAxis() {
    final ChartDriver driver;
    final Chart chart;
    final IBasicStyles styles;
    Font titleFont, tickFont;
    Stroke axisStroke, gridLineStroke;
    Color axisColor, gridLineColor;

    chart = this.getOwner();
    styles = chart.m_styles;
    driver = chart.m_driver;

    if (this.m_title != null) {
      titleFont = this.m_titleFont;
      if (titleFont == null) {
        titleFont = driver.getDefaultAxisTitleFont(styles);
      } else {
        titleFont = driver.scaleAxisTitleFont(titleFont);
      }
    } else {
      titleFont = null;
    }

    axisColor = this.m_axisColor;
    if (axisColor == null) {
      axisColor = driver.getDefaultAxisColor(styles);
    }

    axisStroke = this.m_axisStroke;
    if (axisStroke == null) {
      axisStroke = driver.getDefaultAxisStroke(styles);
    }

    tickFont = this.m_tickFont;
    if (tickFont == null) {
      tickFont = driver.getDefaultAxisTickFont(styles);
    } else {
      tickFont = driver.scaleAxisTickFont(tickFont);
    }

    gridLineStroke = this.m_gridLineStroke;
    if (gridLineStroke == null) {
      gridLineStroke = driver.getDefaultGridLineStroke(styles);
    }

    gridLineColor = this.m_gridLineColor;
    if (gridLineColor == null) {
      gridLineColor = driver.getDefaultGridLineColor(styles);
    }

    return new CompiledAxis(this.m_title, titleFont, tickFont, axisStroke,
        axisColor, gridLineStroke, gridLineColor,
        this.m_minimum.doubleValue(), this.m_maximum.doubleValue());
  }

  /** the aggregate portmanteau */
  private static final class __TwoAggregates implements IAggregate {
    /** the first aggregate */
    private final IAggregate m_aggregateOne;
    /** the second aggregate */
    private final IAggregate m_aggregateTwo;

    /**
     * create the aggregate portmanteau
     *
     * @param aggregateOne
     *          the first aggregate
     * @param aggregateTwo
     *          the second aggregate
     */
    __TwoAggregates(final IAggregate aggregateOne,
        final IAggregate aggregateTwo) {
      super();
      this.m_aggregateOne = aggregateOne;
      this.m_aggregateTwo = aggregateTwo;
    }

    /** {@inheritDoc} */
    @Override
    public final void append(final byte v) {
      this.m_aggregateOne.append(v);
      this.m_aggregateTwo.append(v);
    }

    /** {@inheritDoc} */
    @Override
    public final void append(final short v) {
      this.m_aggregateOne.append(v);
      this.m_aggregateTwo.append(v);
    }

    /** {@inheritDoc} */
    @Override
    public final void append(final int v) {
      this.m_aggregateOne.append(v);
      this.m_aggregateTwo.append(v);
    }

    /** {@inheritDoc} */
    @Override
    public final void append(final long v) {
      this.m_aggregateOne.append(v);
      this.m_aggregateTwo.append(v);
    }

    /** {@inheritDoc} */
    @Override
    public final void append(final float v) {
      this.m_aggregateOne.append(v);
      this.m_aggregateTwo.append(v);
    }

    /** {@inheritDoc} */
    @Override
    public final void append(final double v) {
      this.m_aggregateOne.append(v);
      this.m_aggregateTwo.append(v);
    }
  }
}
