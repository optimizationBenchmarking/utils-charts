package org.optimizationBenchmarking.utils.chart.impl.abstr;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.HashMap;
import java.util.WeakHashMap;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.chart.spec.IChartDriver;
import org.optimizationBenchmarking.utils.graphics.GraphicUtils;
import org.optimizationBenchmarking.utils.graphics.style.spec.IBasicStyles;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.tools.impl.abstr.Tool;

/** the chart driver base class */
public abstract class ChartDriver extends Tool implements IChartDriver {

  /** the font scale for the chart title: {@value} */
  protected static final float FONT_SCALE_CHART_TITLE = 1f;
  /** the font scale for the axis title: {@value} */
  protected static final float FONT_SCALE_AXIS_TITLE = 0.9f;
  /** the font scale for the data title: {@value} */
  protected static final float FONT_SCALE_DATA_TITLE = ChartDriver.FONT_SCALE_AXIS_TITLE;
  /** the font scale for the axis ticks: {@value} */
  protected static final float FONT_SCALE_AXIS_TICKS = (0.1f
      * (int) (10 * (ChartDriver.FONT_SCALE_AXIS_TITLE
          * ChartDriver.FONT_SCALE_AXIS_TITLE)));

  /** the hash map of scaled fonts */
  private final HashMap<__FontKey, Font> m_scaled;

  /** the defaults */
  private final WeakHashMap<IBasicStyles, Object[]> m_defaults;

  /**
   * the chart driver
   */
  protected ChartDriver() {
    super();
    this.m_scaled = new HashMap<>();
    this.m_defaults = new WeakHashMap<>();
  }

  /** {@inheritDoc} */
  @Override
  public ChartBuilder use() {
    this.checkCanUse();
    return new ChartBuilder(this);
  }

  /**
   * render a compiled 2D line chart
   *
   * @param chart
   *          the chart to be rendered.
   * @param graphic
   *          the graphic output interface
   * @param logger
   *          a logger for logging info, or {@code null} if none is needed
   */
  protected abstract void renderLineChart2D(
      final CompiledLineChart2D chart, final Graphics2D graphic,
      final Logger logger);

  /**
   * render a compiled pie chart
   *
   * @param chart
   *          the chart to be rendered.
   * @param graphic
   *          the graphic output interface
   * @param logger
   *          a logger for logging info, or {@code null} if none is needed
   */
  protected abstract void renderPieChart(final CompiledPieChart chart,
      final Graphics2D graphic, final Logger logger);

  /**
   * Scale a font with the base font scale
   *
   * @param font
   *          the font
   * @param scale
   *          the scale
   * @return the scaled font
   */
  public synchronized final Font scaleFont(final Font font,
      final float scale) {
    final float oldSize, newSize;
    final int size;
    final __FontKey goalKey, goalKey2;
    Font found, derived;
    int goalSize;

    if (scale == 1f) {
      return font;
    }

    oldSize = font.getSize2D();
    if (scale < 1f) {
      goalSize = (int) (Math.floor(oldSize * scale));
    } else {
      goalSize = (int) (Math.ceil(oldSize * scale));
    }
    goalSize = Math.max(5, Math.min(100, goalSize));
    if (goalSize == oldSize) {
      return font;
    }

    goalKey = new __FontKey(font, goalSize);
    found = this.m_scaled.get(goalKey);
    if (found != null) {
      return found;
    }

    derived = font.deriveFont((float) goalSize);
    if (derived == null) {
      return font;
    }

    newSize = derived.getSize2D();
    if (newSize == oldSize) {
      derived = font;
    } else {
      if (Math.abs(newSize - goalSize) > Math.abs(oldSize - goalSize)) {
        derived = font;
      }
    }

    size = derived.getSize();
    if (size != goalSize) {
      goalKey2 = new __FontKey(font, size);
      found = this.m_scaled.get(goalKey2);
      if (found != null) {
        this.m_scaled.put(goalKey, found);
        return found;
      }
    }

    this.m_scaled.put(goalKey, derived);

    return derived;
  }

  /**
   * Get the defaults for a given style set
   *
   * @param styles
   *          the style set
   * @return the defaults for a given style set
   */
  private synchronized final Object[] __getDefaults(
      final IBasicStyles styles) {
    Object[] result;

    result = this.m_defaults.get(styles);
    if (result == null) {
      result = new Object[_EDefaults.DEFAULT_COUNT];
      this.m_defaults.put(styles, result);
      this.m_scaled.clear();
    }

    return result;
  }

  /**
   * Scale the title font of the chart
   *
   * @param font
   *          the title font
   * @return the scaled font
   */
  public synchronized Font scaleTitleFont(final Font font) {
    return this.scaleFont(font, ChartDriver.FONT_SCALE_CHART_TITLE);
  }

  /**
   * Create the default font to be used for the chart title
   *
   * @param styles
   *          the available style set
   * @return the default font to be used for the chart title
   */
  protected synchronized Font createDefaultChartTitleFont(
      final IBasicStyles styles) {
    return this.scaleTitleFont(styles.getDefaultFont().getFont());
  }

  /**
   * Get the default font to be used for the chart title
   *
   * @param styles
   *          the available style set
   * @return the default font to be used for the chart title
   */
  public synchronized final Font getDefaultChartTitleFont(
      final IBasicStyles styles) {
    final int key;
    final Object[] defaults;
    Font result;

    defaults = this.__getDefaults(styles);
    key = _EDefaults.CHART_TITLE_FONT.ordinal();
    result = ((Font) (defaults[key]));
    if (result == null) {
      defaults[key] = result = this.createDefaultChartTitleFont(styles);
    }
    return result;
  }

  /**
   * Scale the data title font of a data element
   *
   * @param font
   *          the data title font
   * @return the scaled font
   */
  public synchronized Font scaleDataTitleFont(final Font font) {
    return this.scaleFont(font, ChartDriver.FONT_SCALE_DATA_TITLE);
  }

  /**
   * Create the default font to be used for a data element titles
   *
   * @param styles
   *          the available style set
   * @return the default font to be used for a data element titles
   */
  protected synchronized Font createDefaultDataTitleFont(
      final IBasicStyles styles) {
    return this.scaleDataTitleFont(styles.getDefaultFont().getFont());
  }

  /**
   * Get the default font to be used for a data element title
   *
   * @param styles
   *          the available style set
   * @return the default font to be used for a data element title
   */
  public synchronized final Font getDefaultDataTitleFont(
      final IBasicStyles styles) {
    final int key;
    final Object[] defaults;
    Font result;

    defaults = this.__getDefaults(styles);
    key = _EDefaults.DATA_TITLE_FONT.ordinal();
    result = ((Font) (defaults[key]));
    if (result == null) {
      defaults[key] = result = this.createDefaultDataTitleFont(styles);
    }
    return result;
  }

  /**
   * Create the default data stroke
   *
   * @param styles
   *          the styles
   * @return the default data stroke
   */
  protected synchronized Stroke createDefaultDataStroke(
      final IBasicStyles styles) {
    return styles.getDefaultStroke().getStroke();
  }

  /**
   * Get the default data stroke
   *
   * @param styles
   *          the available style set
   * @return the default stroke to be used for a data
   */
  public synchronized final Stroke getDefaultDataStroke(
      final IBasicStyles styles) {
    final int key;
    final Object[] defaults;
    Stroke result;

    defaults = this.__getDefaults(styles);
    key = _EDefaults.DATA_STROKE.ordinal();
    result = ((Stroke) (defaults[key]));
    if (result == null) {
      defaults[key] = result = this.createDefaultDataStroke(styles);
    }
    return result;
  }

  /**
   * Scale the axis title font of the chart
   *
   * @param font
   *          the axis title font
   * @return the scaled font
   */
  public synchronized Font scaleAxisTitleFont(final Font font) {
    return this.scaleFont(font, ChartDriver.FONT_SCALE_AXIS_TITLE);
  }

  /**
   * Create the default font to be used for axis titles
   *
   * @param styles
   *          the available style set
   * @return the default font to be used for axis titles
   */
  protected synchronized Font createDefaultAxisTitleFont(
      final IBasicStyles styles) {
    return this.scaleFont(styles.getDefaultFont().getFont(),
        ChartDriver.FONT_SCALE_AXIS_TITLE);
  }

  /**
   * Get the default font to be used for an axis title
   *
   * @param styles
   *          the available style set
   * @return the default font to be used for an axis title
   */
  public synchronized final Font getDefaultAxisTitleFont(
      final IBasicStyles styles) {
    final int key;
    final Object[] defaults;
    Font result;

    defaults = this.__getDefaults(styles);
    key = _EDefaults.AXIS_TITLE_FONT.ordinal();
    result = ((Font) (defaults[key]));
    if (result == null) {
      defaults[key] = result = this.createDefaultAxisTitleFont(styles);
    }
    return result;
  }

  /**
   * Scale the axis tick font of the chart
   *
   * @param font
   *          the axis title font
   * @return the scaled font
   */
  public synchronized Font scaleAxisTickFont(final Font font) {
    return this.scaleFont(font, ChartDriver.FONT_SCALE_AXIS_TICKS);
  }

  /**
   * Create the default font to be used for axis ticks
   *
   * @param styles
   *          the available style set
   * @return the default font to be used for axis ticks
   */
  protected synchronized Font createDefaultAxisTickFont(
      final IBasicStyles styles) {
    return this.scaleFont(styles.getDefaultFont().getFont(),
        ChartDriver.FONT_SCALE_AXIS_TICKS);
  }

  /**
   * Get the default font to be used for an axis ticks
   *
   * @param styles
   *          the available style set
   * @return the default font to be used for an axis ticks
   */
  public synchronized final Font getDefaultAxisTickFont(
      final IBasicStyles styles) {
    final int key;
    final Object[] defaults;
    Font result;

    defaults = this.__getDefaults(styles);
    key = _EDefaults.AXIS_TICK_FONT.ordinal();
    result = ((Font) (defaults[key]));
    if (result == null) {
      defaults[key] = result = this.createDefaultAxisTickFont(styles);
    }
    return result;
  }

  /**
   * Get the stroke to be used for axes
   *
   * @param styles
   *          the styles
   * @return the axes stroke
   */
  protected synchronized Stroke createDefaultAxisStroke(
      final IBasicStyles styles) {
    final Stroke defaultStroke, thickStroke;
    final float defaultWidth, thickWidth, goalWidth;
    final int endCap, lineJoin;
    BasicStroke basicStroke;

    defaultStroke = styles.getDefaultStroke().getStroke();
    thickStroke = styles.getThickStroke().getStroke();

    findThickWidth: {
      if (defaultStroke instanceof BasicStroke) {
        basicStroke = ((BasicStroke) defaultStroke);
        endCap = basicStroke.getEndCap();
        lineJoin = basicStroke.getLineJoin();
        defaultWidth = basicStroke.getLineWidth();
        if (thickStroke instanceof BasicStroke) {
          thickWidth = ((BasicStroke) thickStroke).getLineWidth();
          break findThickWidth;
        }
      } else {
        defaultWidth = GraphicUtils.getDefaultStrokeWidth();
        if (thickStroke instanceof BasicStroke) {
          basicStroke = ((BasicStroke) thickStroke);
          endCap = basicStroke.getEndCap();
          lineJoin = basicStroke.getLineJoin();
          thickWidth = basicStroke.getLineWidth();
          break findThickWidth;
        }
        endCap = GraphicUtils.getDefaultStrokeEndCap();
        lineJoin = GraphicUtils.getDefaultStrokeJoin();
      }
      thickWidth = GraphicUtils.getDefaultThickStrokeWidth();
    }

    goalWidth = Math.max(defaultWidth, //
        Math.min(thickWidth, //
            ((float) (0.01d * Math.round(100d * //
                ((0.65d * defaultWidth) + //
                    (0.35d * thickWidth)))))));

    return new BasicStroke(goalWidth, endCap, lineJoin, //
        GraphicUtils.getDefaultStrokeMiterLimit(goalWidth));
  }

  /**
   * Get the default axis stroke
   *
   * @param styles
   *          the available style set
   * @return the default stroke to be used for an axis
   */
  public synchronized final Stroke getDefaultAxisStroke(
      final IBasicStyles styles) {
    final int key;
    final Object[] defaults;
    Stroke result;

    defaults = this.__getDefaults(styles);
    key = _EDefaults.AXIS_STROKE.ordinal();
    result = ((Stroke) (defaults[key]));
    if (result == null) {
      defaults[key] = result = this.createDefaultAxisStroke(styles);
    }
    return result;
  }

  /**
   * Create the color to be used for axes
   *
   * @param styles
   *          the styles
   * @return the axis color
   */
  protected synchronized Color createDefaultAxisColor(
      final IBasicStyles styles) {
    return styles.getBlack().getColor();
  }

  /**
   * Get the default axis color
   *
   * @param styles
   *          the available style set
   * @return the default color to be used for an axis
   */
  public synchronized final Color getDefaultAxisColor(
      final IBasicStyles styles) {
    final int key;
    final Object[] defaults;
    Color result;

    defaults = this.__getDefaults(styles);
    key = _EDefaults.AXIS_COLOR.ordinal();
    result = ((Color) (defaults[key]));
    if (result == null) {
      defaults[key] = result = this.createDefaultAxisColor(styles);
    }
    return result;
  }

  /**
   * Get the stroke to be used for grid lines
   *
   * @param styles
   *          the styles
   * @return the grid line stroke
   */
  protected synchronized Stroke createDefaultGridLineStroke(
      final IBasicStyles styles) {
    final Stroke thinStroke;
    final BasicStroke basicStroke;
    final float width, miterLimit;
    final int endCap, lineJoin;

    thinStroke = styles.getThinStroke().getStroke();
    if (thinStroke instanceof BasicStroke) {
      basicStroke = ((BasicStroke) thinStroke);
      width = basicStroke.getLineWidth();
      miterLimit = basicStroke.getMiterLimit();
      endCap = basicStroke.getEndCap();
      lineJoin = basicStroke.getLineJoin();
    } else {
      width = (2f / 3f);
      endCap = BasicStroke.CAP_ROUND;
      lineJoin = BasicStroke.JOIN_ROUND;
      miterLimit = 10f;
    }

    return new BasicStroke(width, endCap, lineJoin, miterLimit,
        new float[] { 0.5f, 2.5f }, 0f);
  }

  /**
   * Get the default grid line stroke
   *
   * @param styles
   *          the available style set
   * @return the default stroke to be used for a grid line
   */
  public synchronized final Stroke getDefaultGridLineStroke(
      final IBasicStyles styles) {
    final int key;
    final Object[] defaults;
    Stroke result;

    defaults = this.__getDefaults(styles);
    key = _EDefaults.GRID_STROKE.ordinal();
    result = ((Stroke) (defaults[key]));
    if (result == null) {
      defaults[key] = result = this.createDefaultGridLineStroke(styles);
    }
    return result;
  }

  /**
   * Get the color to be used for grid lines
   *
   * @param styles
   *          the styles
   * @return the grid line color
   */
  protected synchronized Color createDefaultGridLineColor(
      final IBasicStyles styles) {
    return this.getDefaultAxisColor(styles);
  }

  /**
   * Get the default grid line color
   *
   * @param styles
   *          the available style set
   * @return the default color to be used for an grid lines
   */
  public synchronized final Color getDefaultGridLineColor(
      final IBasicStyles styles) {
    final int key;
    final Object[] defaults;
    Color result;

    defaults = this.__getDefaults(styles);
    key = _EDefaults.GRID_COLOR.ordinal();
    result = ((Color) (defaults[key]));
    if (result == null) {
      defaults[key] = result = this.createDefaultGridLineColor(styles);
    }
    return result;
  }

  /** the font key */
  private static final class __FontKey {

    /** the font name */
    final String m_fontName;

    /** the hash code */
    final int m_hash;

    /**
     * Create a font key
     *
     * @param font
     *          the font
     * @param size
     *          the size
     */
    __FontKey(final Font font, final int size) {
      super();

      int style;

      this.m_fontName = TextUtils.toLowerCase(font.getFontName());

      style = 0;
      if (!(font.isBold())) {
        style |= 1;
      }
      if (!(font.isItalic())) {
        style |= 2;
      }
      if (!(GraphicUtils.isFontUnderlined(font))) {
        style |= 4;
      }
      style |= (font.getSize() << 3);

      this.m_hash = HashUtils.combineHashes(
          HashUtils.hashCode(//
              (style ^ (style << 12) ^ (style << 24))),
          HashUtils.hashCode(this.m_fontName));
    }

    /** {@inheritDoc} */
    @Override
    public final int hashCode() {
      return this.m_hash;
    }

    /** {@inheritDoc} */
    @Override
    public final boolean equals(final Object o) {
      final __FontKey fk;
      if (o == this) {
        return true;
      }
      if (o instanceof __FontKey) {
        fk = ((__FontKey) o);
        return ((fk.m_hash == this.m_hash) && //
            (this.m_fontName.equals(fk.m_fontName)));
      }

      return false;
    }
  }
}
