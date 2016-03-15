package test.junit.org.optimizationBenchmarking.utils.chart;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

import org.optimizationBenchmarking.utils.chart.impl.export.ExportChartDriver;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

import shared.junit.org.optimizationBenchmarking.utils.chart.ChartDriverTest;

/** Test whether the text chart driver can be used */
public class ExportChartDriverTest extends ChartDriverTest {

  /** create */
  public ExportChartDriverTest() {
    super(ExportChartDriver.getInstance());
  }

  /** {@inheritDoc} */
  @Override
  protected final Graphics2D createGraphic() {
    return new __ExportGraphics2D(super.createGraphic());
  }

  /** the internal graphics context for testing */
  private static final class __ExportGraphics2D extends Graphics2D
      implements ITextOutput {

    /** the wrapped graphics 2D */
    private final Graphics2D m_wrapped;
    /** the text output */
    private final MemoryTextOutput m_to;

    /**
     * create
     *
     * @param wrapped
     *          the wrapped graphics
     */
    __ExportGraphics2D(final Graphics2D wrapped) {
      super();
      this.m_wrapped = wrapped;
      this.m_to = new MemoryTextOutput();
    }

    /** {@inheritDoc} */
    @Override
    public final ITextOutput append(final CharSequence csq) {
      this.m_to.append(csq);
      return this;
    }

    /** {@inheritDoc} */
    @Override
    public final ITextOutput append(final CharSequence csq,
        final int start, final int end) {
      this.m_to.append(csq, start, end);
      return this;
    }

    /** {@inheritDoc} */
    @Override
    public final ITextOutput append(final char c) {
      this.m_to.append(c);
      return this;
    }

    /** {@inheritDoc} */
    @Override
    public final void append(final String s) {
      this.m_to.append(s);
    }

    /** {@inheritDoc} */
    @Override
    public final void append(final String s, final int start,
        final int end) {
      this.m_to.append(s, start, end);
    }

    /** {@inheritDoc} */
    @Override
    public final void append(final char[] chars) {
      this.m_to.append(chars);
    }

    /** {@inheritDoc} */
    @Override
    public final void append(final char[] chars, final int start,
        final int end) {
      this.m_to.append(chars, start, end);
    }

    /** {@inheritDoc} */
    @Override
    public final void append(final byte v) {
      this.m_to.append(v);
    }

    /** {@inheritDoc} */
    @Override
    public final void append(final short v) {
      this.m_to.append(v);
    }

    /** {@inheritDoc} */
    @Override
    public final void append(final int v) {
      this.m_to.append(v);
    }

    /** {@inheritDoc} */
    @Override
    public final void append(final long v) {
      this.m_to.append(v);
    }

    /** {@inheritDoc} */
    @Override
    public final void append(final float v) {
      this.m_to.append(v);
    }

    /** {@inheritDoc} */
    @Override
    public final void append(final double v) {
      this.m_to.append(v);
    }

    /** {@inheritDoc} */
    @Override
    public final void append(final boolean v) {
      this.m_to.append(v);
    }

    /** {@inheritDoc} */
    @Override
    public final void append(final Object o) {
      this.m_to.append(o);
    }

    /** {@inheritDoc} */
    @Override
    public final void appendLineBreak() {
      this.m_to.appendLineBreak();
    }

    /** {@inheritDoc} */
    @Override
    public final void appendNonBreakingSpace() {
      this.m_to.appendNonBreakingSpace();
    }

    /** {@inheritDoc} */
    @Override
    public final void flush() {
      this.m_to.flush();
    }

    /** {@inheritDoc} */
    @Override
    public final void draw(final Shape s) {
      // ignore
    }

    /** {@inheritDoc} */
    @Override
    public final boolean drawImage(final Image img,
        final AffineTransform xform, final ImageObserver obs) {
      return this.m_wrapped.drawImage(img, xform, obs);
    }

    /** {@inheritDoc} */
    @Override
    public final void drawImage(final BufferedImage img,
        final BufferedImageOp op, final int x, final int y) {
      this.m_wrapped.drawImage(img, op, x, y);
    }

    /** {@inheritDoc} */
    @Override
    public final void drawRenderedImage(final RenderedImage img,
        final AffineTransform xform) {
      this.m_wrapped.drawRenderedImage(img, xform);

    }

    /** {@inheritDoc} */
    @Override
    public final void drawRenderableImage(final RenderableImage img,
        final AffineTransform xform) {
      this.m_wrapped.drawRenderableImage(img, xform);
    }

    /** {@inheritDoc} */
    @Override
    public final void drawString(final String str, final int x,
        final int y) {
      this.m_wrapped.drawString(str, x, y);
    }

    /** {@inheritDoc} */
    @Override
    public final void drawString(final String str, final float x,
        final float y) {
      this.m_wrapped.drawString(str, x, y);
    }

    /** {@inheritDoc} */
    @Override
    public final void drawString(
        final AttributedCharacterIterator iterator, final int x,
        final int y) {
      this.m_wrapped.drawString(iterator, x, y);
    }

    /** {@inheritDoc} */
    @Override
    public final void drawString(
        final AttributedCharacterIterator iterator, final float x,
        final float y) {
      this.m_wrapped.drawString(iterator, x, y);
    }

    /** {@inheritDoc} */
    @Override
    public final void drawGlyphVector(final GlyphVector g, final float x,
        final float y) {
      this.m_wrapped.drawGlyphVector(g, x, y);
    }

    /** {@inheritDoc} */
    @Override
    public final void fill(final Shape s) {
      this.m_wrapped.fill(s);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean hit(final Rectangle rect, final Shape s,
        final boolean onStroke) {
      return this.m_wrapped.hit(rect, s, onStroke);
    }

    /** {@inheritDoc} */
    @Override
    public final GraphicsConfiguration getDeviceConfiguration() {
      return this.m_wrapped.getDeviceConfiguration();
    }

    /** {@inheritDoc} */
    @Override
    public final void setComposite(final Composite comp) {
      this.m_wrapped.setComposite(comp);
    }

    /** {@inheritDoc} */
    @Override
    public final void setPaint(final Paint paint) {
      this.m_wrapped.setPaint(paint);
    }

    /** {@inheritDoc} */
    @Override
    public final void setStroke(final Stroke s) {
      this.m_wrapped.setStroke(s);
    }

    /** {@inheritDoc} */
    @Override
    public final void setRenderingHint(final Key hintKey,
        final Object hintValue) {
      this.m_wrapped.setRenderingHint(hintKey, hintValue);
    }

    /** {@inheritDoc} */
    @Override
    public final Object getRenderingHint(final Key hintKey) {
      return this.m_wrapped.getRenderingHint(hintKey);
    }

    /** {@inheritDoc} */
    @Override
    public final void setRenderingHints(final Map<?, ?> hints) {
      this.m_wrapped.setRenderingHints(hints);
    }

    /** {@inheritDoc} */
    @Override
    public final void addRenderingHints(final Map<?, ?> hints) {
      this.m_wrapped.addRenderingHints(hints);
    }

    /** {@inheritDoc} */
    @Override
    public final RenderingHints getRenderingHints() {
      return this.m_wrapped.getRenderingHints();
    }

    /** {@inheritDoc} */
    @Override
    public final void translate(final int x, final int y) {
      this.m_wrapped.translate(x, y);
    }

    /** {@inheritDoc} */
    @Override
    public final void translate(final double tx, final double ty) {
      this.m_wrapped.translate(tx, ty);
    }

    /** {@inheritDoc} */
    @Override
    public final void rotate(final double theta) {
      this.m_wrapped.rotate(theta);
    }

    /** {@inheritDoc} */
    @Override
    public final void rotate(final double theta, final double x,
        final double y) {
      this.m_wrapped.rotate(theta, x, y);
    }

    /** {@inheritDoc} */
    @Override
    public final void scale(final double sx, final double sy) {
      this.m_wrapped.scale(sx, sy);
    }

    /** {@inheritDoc} */
    @Override
    public final void shear(final double shx, final double shy) {
      this.m_wrapped.shear(shx, shy);
    }

    /** {@inheritDoc} */
    @Override
    public final void transform(final AffineTransform Tx) {
      this.m_wrapped.transform(Tx);
    }

    /** {@inheritDoc} */
    @Override
    public final void setTransform(final AffineTransform Tx) {
      this.m_wrapped.setTransform(Tx);
    }

    /** {@inheritDoc} */
    @Override
    public final AffineTransform getTransform() {
      return this.m_wrapped.getTransform();
    }

    /** {@inheritDoc} */
    @Override
    public final Paint getPaint() {
      return this.m_wrapped.getPaint();
    }

    /** {@inheritDoc} */
    @Override
    public final Composite getComposite() {
      return this.m_wrapped.getComposite();
    }

    /** {@inheritDoc} */
    @Override
    public final void setBackground(final Color color) {
      this.m_wrapped.setBackground(color);
    }

    /** {@inheritDoc} */
    @Override
    public final Color getBackground() {
      return this.m_wrapped.getBackground();
    }

    /** {@inheritDoc} */
    @Override
    public final Stroke getStroke() {
      return this.m_wrapped.getStroke();
    }

    /** {@inheritDoc} */
    @Override
    public final void clip(final Shape s) {
      this.m_wrapped.clip(s);
    }

    /** {@inheritDoc} */
    @Override
    public final FontRenderContext getFontRenderContext() {
      return this.m_wrapped.getFontRenderContext();
    }

    /** {@inheritDoc} */
    @Override
    public final Graphics create() {
      return new __ExportGraphics2D(
          ((Graphics2D) (this.m_wrapped.create())));
    }

    /** {@inheritDoc} */
    @Override
    public final Color getColor() {
      return this.m_wrapped.getColor();
    }

    /** {@inheritDoc} */
    @Override
    public final void setColor(final Color c) {
      this.m_wrapped.setColor(c);
    }

    /** {@inheritDoc} */
    @Override
    public final void setPaintMode() {
      this.m_wrapped.setPaintMode();
    }

    /** {@inheritDoc} */
    @Override
    public final void setXORMode(final Color c1) {
      this.m_wrapped.setXORMode(c1);
    }

    /** {@inheritDoc} */
    @Override
    public final Font getFont() {
      return this.m_wrapped.getFont();
    }

    /** {@inheritDoc} */
    @Override
    public final void setFont(final Font font) {
      this.m_wrapped.setFont(font);
    }

    /** {@inheritDoc} */
    @Override
    public final FontMetrics getFontMetrics(final Font f) {
      return this.m_wrapped.getFontMetrics();
    }

    /** {@inheritDoc} */
    @Override
    public final Rectangle getClipBounds() {
      return this.m_wrapped.getClipBounds();
    }

    /** {@inheritDoc} */
    @Override
    public final void clipRect(final int x, final int y, final int width,
        final int height) {
      this.m_wrapped.clipRect(x, y, width, height);
    }

    /** {@inheritDoc} */
    @Override
    public final void setClip(final int x, final int y, final int width,
        final int height) {
      this.m_wrapped.setClip(x, y, width, height);
    }

    /** {@inheritDoc} */
    @Override
    public final Shape getClip() {
      return this.m_wrapped.getClip();
    }

    /** {@inheritDoc} */
    @Override
    public final void setClip(final Shape clip) {
      this.m_wrapped.setClip(clip);
    }

    /** {@inheritDoc} */
    @Override
    public final void copyArea(final int x, final int y, final int width,
        final int height, final int dx, final int dy) {
      this.m_wrapped.copyArea(x, y, width, height, dx, dy);
    }

    /** {@inheritDoc} */
    @Override
    public final void drawLine(final int x1, final int y1, final int x2,
        final int y2) {
      this.m_wrapped.drawLine(x1, y1, x2, y2);
    }

    /** {@inheritDoc} */
    @Override
    public final void fillRect(final int x, final int y, final int width,
        final int height) {
      this.m_wrapped.fillRect(x, y, width, height);
    }

    /** {@inheritDoc} */
    @Override
    public final void clearRect(final int x, final int y, final int width,
        final int height) {
      this.m_wrapped.clearRect(x, y, width, height);
    }

    /** {@inheritDoc} */
    @Override
    public final void drawRoundRect(final int x, final int y,
        final int width, final int height, final int arcWidth,
        final int arcHeight) {
      this.m_wrapped.drawRoundRect(x, y, width, height, arcWidth,
          arcHeight);
    }

    /** {@inheritDoc} */
    @Override
    public final void fillRoundRect(final int x, final int y,
        final int width, final int height, final int arcWidth,
        final int arcHeight) {
      this.m_wrapped.fillRoundRect(x, y, width, height, arcWidth,
          arcHeight);
    }

    /** {@inheritDoc} */
    @Override
    public final void drawOval(final int x, final int y, final int width,
        final int height) {
      this.m_wrapped.drawOval(x, y, width, height);
    }

    /** {@inheritDoc} */
    @Override
    public final void fillOval(final int x, final int y, final int width,
        final int height) {
      this.m_wrapped.fillOval(x, y, width, height);
    }

    /** {@inheritDoc} */
    @Override
    public final void drawArc(final int x, final int y, final int width,
        final int height, final int startAngle, final int arcAngle) {
      this.m_wrapped.drawArc(x, y, width, height, startAngle, arcAngle);
    }

    /** {@inheritDoc} */
    @Override
    public final void fillArc(final int x, final int y, final int width,
        final int height, final int startAngle, final int arcAngle) {
      this.m_wrapped.fillArc(x, y, width, height, startAngle, arcAngle);
    }

    /** {@inheritDoc} */
    @Override
    public final void drawPolyline(final int[] xPoints,
        final int[] yPoints, final int nPoints) {
      this.m_wrapped.drawPolyline(xPoints, yPoints, nPoints);
    }

    /** {@inheritDoc} */
    @Override
    public final void drawPolygon(final int[] xPoints, final int[] yPoints,
        final int nPoints) {
      this.m_wrapped.drawPolygon(xPoints, yPoints, nPoints);
    }

    /** {@inheritDoc} */
    @Override
    public final void fillPolygon(final int[] xPoints, final int[] yPoints,
        final int nPoints) {
      this.m_wrapped.fillPolygon(xPoints, yPoints, nPoints);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean drawImage(final Image img, final int x,
        final int y, final ImageObserver observer) {
      return this.m_wrapped.drawImage(img, x, y, observer);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean drawImage(final Image img, final int x,
        final int y, final int width, final int height,
        final ImageObserver observer) {
      return this.m_wrapped.drawImage(img, x, y, width, height, observer);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean drawImage(final Image img, final int x,
        final int y, final Color bgcolor, final ImageObserver observer) {
      return this.m_wrapped.drawImage(img, x, y, bgcolor, observer);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean drawImage(final Image img, final int x,
        final int y, final int width, final int height,
        final Color bgcolor, final ImageObserver observer) {
      return this.m_wrapped.drawImage(img, x, y, width, height, bgcolor,
          observer);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean drawImage(final Image img, final int dx1,
        final int dy1, final int dx2, final int dy2, final int sx1,
        final int sy1, final int sx2, final int sy2,
        final ImageObserver observer) {
      return this.m_wrapped.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1,
          sx2, sy2, observer);
    }

    /** {@inheritDoc} */
    @Override
    public final boolean drawImage(final Image img, final int dx1,
        final int dy1, final int dx2, final int dy2, final int sx1,
        final int sy1, final int sx2, final int sy2, final Color bgcolor,
        final ImageObserver observer) {
      return this.m_wrapped.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1,
          sx2, sy2, bgcolor, observer);
    }

    /** {@inheritDoc} */
    @Override
    public final void dispose() {
      this.m_wrapped.dispose();
      this.m_to.clear();
    }
  }
}
