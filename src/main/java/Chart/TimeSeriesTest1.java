package Chart;

import org.jfree.chart.*;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.text.SimpleDateFormat;

public class TimeSeriesTest1 extends ApplicationFrame implements
        ChartMouseListener, MouseListener, MouseMotionListener {
    private static final long serialVersionUID = 1L;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private boolean domainCrosshairState;
    private XYItemEntity xyItemEntity;
    private boolean isDraged;

    public TimeSeriesTest1(String str) {
        super(str);
        XYDataset localXYDataset = createDataset();
        chart = createChart(localXYDataset);
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 500));
        // chartPanel.setMouseZoomable(true, false);
        chartPanel.setMouseZoomable(false);
        chartPanel.addChartMouseListener(this);
        chartPanel.addMouseListener((MouseListener) this);
        chartPanel.addMouseMotionListener(this);

        domainCrosshairState = false;
        isDraged = false;
        setContentPane(chartPanel);
    }

    private static XYDataset createDataset() {
        Day localDay = new Day();
        TimeSeries localTimeSeries1 = new TimeSeries("随机数据", Hour.class);
        localTimeSeries1.add(new Hour(0, localDay), 520.2D);
        localTimeSeries1.add(new Hour(2, localDay), 575.1D);
        localTimeSeries1.add(new Hour(4, localDay), 564.4D);
        localTimeSeries1.add(new Hour(6, localDay), 540.2D);
        localTimeSeries1.add(new Hour(8, localDay), 530.2D);
        localTimeSeries1.add(new Hour(10, localDay), 555.2D);
        localTimeSeries1.add(new Hour(12, localDay), 580.4D);
        localTimeSeries1.add(new Hour(14, localDay), 583.2D);
        localTimeSeries1.add(new Hour(16, localDay), 595.2D);
        localTimeSeries1.add(new Hour(18, localDay), 564.4D);
        localTimeSeries1.add(new Hour(20, localDay), 523.2D);
        localTimeSeries1.add(new Hour(22, localDay), 515.2D);
        localTimeSeries1.add(new Hour(24, localDay), 530.4D);

        TimeSeries localTimeSeries2 = new TimeSeries("随机数据", Hour.class);
        localTimeSeries2.add(new Hour(0, localDay), 620.2D);
        localTimeSeries2.add(new Hour(2, localDay), 654.1D);
        localTimeSeries2.add(new Hour(4, localDay), 664.4D);
        localTimeSeries2.add(new Hour(6, localDay), 670.2D);
        localTimeSeries2.add(new Hour(8, localDay), 690.2D);
        localTimeSeries2.add(new Hour(10, localDay), 695.2D);
        localTimeSeries2.add(new Hour(12, localDay), 680.4D);
        localTimeSeries2.add(new Hour(14, localDay), 683.2D);
        localTimeSeries2.add(new Hour(16, localDay), 695.2D);
        localTimeSeries2.add(new Hour(18, localDay), 714.4D);
        localTimeSeries2.add(new Hour(20, localDay), 723.2D);
        localTimeSeries2.add(new Hour(22, localDay), 715.2D);
        localTimeSeries2.add(new Hour(24, localDay), 680.4D);

        TimeSeriesCollection localTimeSeriesCollection = new TimeSeriesCollection();
        localTimeSeriesCollection.addSeries(localTimeSeries1);
        localTimeSeriesCollection.addSeries(localTimeSeries2);

        return localTimeSeriesCollection;

    }

    private static JFreeChart createChart(XYDataset xyDataset) {
        JFreeChart localChart = ChartFactory.createTimeSeriesChart("时间序列调整",
                "时间", "数值", xyDataset, true, true, false);
        TextTitle title = localChart.getTitle();
        title.setFont(new Font("宋体", Font.BOLD, 24));
        LegendTitle legend = localChart.getLegend();
        legend.setItemFont(new Font("宋体", Font.ITALIC, 14));

        XYPlot plot = (XYPlot) localChart.getPlot();
        plot.setBackgroundPaint(null);

        // X 轴
        DateAxis xAxis = (DateAxis) plot.getDomainAxis();
        xAxis.setLabelFont(new Font("宋体", Font.PLAIN, 18));
        xAxis.setLabelPaint(Color.BLUE);
        SimpleDateFormat fmt = new SimpleDateFormat("k:mm");
        xAxis.setTickUnit(new DateTickUnit(DateTickUnit.HOUR, 2, fmt));

        // Y 轴
        ValueAxis yAxis = plot.getRangeAxis();
        yAxis.setLabelFont(new Font("宋体", Font.PLAIN, 18));
        yAxis.setLabelPaint(Color.BLUE);

        return localChart;
    }

    public static JPanel createDemoPanel() {
        JFreeChart chart = createChart(createDataset());
        return new ChartPanel(chart);
    }

    public void chartMouseClicked(ChartMouseEvent e) {
        if (e.getTrigger().getClickCount() == 2) {
            domainCrosshairState = !domainCrosshairState;
            // plot.setDomainCrosshairVisible(domainCrosshairState);
            chartPanel.setHorizontalAxisTrace(domainCrosshairState); // 双击竖线
        }

        if (e.getTrigger().getClickCount() == 3) {
            System.out.print(chartPanel.getChart());
        }

        xyItemEntity = (XYItemEntity) e.getEntity();
        if (xyItemEntity == null)
            return;
        int seriesIndex = xyItemEntity.getSeriesIndex();

        XYPlot xyPlot = (XYPlot) chart.getPlot();
        XYLineAndShapeRenderer xyLineAndShapeRenderer = (XYLineAndShapeRenderer) xyPlot
                .getRenderer();
        for (int index = 0; index < xyItemEntity.getDataset().getSeriesCount(); index++) {
            xyLineAndShapeRenderer.setSeriesShapesVisible(index, false);
        }
        xyLineAndShapeRenderer.setSeriesShapesVisible(seriesIndex, true); // 数据点可见

    }

    public void mousePressed(MouseEvent e) {
        // System.out.println("mouse key is pressed!");
    }

    public void mouseReleased(MouseEvent e) {
        // System.out.println("mouse key is released!");
        if (xyItemEntity == null)
            return;
        // for(int i=0;i< xyItemEntity.getDataset().getSeriesCount();i++){
        TimeSeriesCollection tsc = (TimeSeriesCollection) xyItemEntity
                .getDataset();
        TimeSeries ts = tsc.getSeries(xyItemEntity.getSeriesIndex());
        for (int i = 0; i < ts.getItemCount(); i++) {
            System.out.println(ts.getDataItem(i).getPeriod().toString()
                    + "------" + ts.getValue(i).intValue());
        }
        // }
        xyItemEntity = null;
        isDraged = false;
    }

    public void mouseEntered(MouseEvent mouseEvent) {
        // System.out.println( "mouse key is pressed!");
    }

    public void mouseExited(MouseEvent mouseEvent) {
        // System.out.println( "mouse key is pressed!");

    }

    public void mouseClicked(MouseEvent mouseEvent) {
        // System.out.println( "mouse key is pressed!");
    }

    public void chartMouseMoved(ChartMouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {
        if (xyItemEntity == null)
            return;
        int seriesIndex = xyItemEntity.getSeriesIndex();
        int itemIndex = xyItemEntity.getItem();
        int xPos = e.getX();
        int yPos = e.getY();
        Point2D point2D = chartPanel.translateScreenToJava2D(new Point(xPos,
                yPos));
        XYPlot xyPlot = (XYPlot) chart.getPlot();
        ChartRenderingInfo chartRenderingInfo = chartPanel
                .getChartRenderingInfo();
        Rectangle2D rectangle2D = chartRenderingInfo.getPlotInfo()
                .getDataArea();
        ValueAxis valueAxis = xyPlot.getRangeAxis();
        RectangleEdge rectangleEdge = xyPlot.getRangeAxisEdge();
        double value = valueAxis.java2DToValue(point2D.getY(), rectangle2D,
                rectangleEdge);

        TimeSeriesCollection tsc = (TimeSeriesCollection) xyItemEntity
                .getDataset();
        TimeSeries ts = tsc.getSeries(seriesIndex);
        ts.update(itemIndex, value);
        isDraged = true;
    }

    public void mouseMoved(MouseEvent e) {

    }

    public static void main(String[] args) {
        TimeSeriesTest1 dragValueApp = new TimeSeriesTest1("时间序列拖动示例");
        dragValueApp.pack();
        RefineryUtilities.centerFrameOnScreen(dragValueApp);
        dragValueApp.setSize(500, 500);
        dragValueApp.setVisible(true);
    }

    // public static void main(String[] args) {
    // JFrame frame = new JFrame("Applet is in the frame");
    // MyJApplet myJApplet = new MyJApplet();
    // // main方法里创建一个框架来放置applet，applet单独运行时，
    // // 要完成操作必须手动调用init和start方法
    // frame.add(myJApplet, BorderLayout.CENTER);
    // myJApplet.init();
    //
    // frame.setLocationRelativeTo(null);
    // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // frame.setSize(300, 300);
    // frame.setVisible(true);
    // }

    // public static void main(String[] paramArrayOfString){
    // TimeSeriesTest1 dragValueApp = new TimeSeriesTest1("时间序列拖动示例");
    // dragValueApp.pack();
    // RefineryUtilities.centerFrameOnScreen(dragValueApp);
    // dragValueApp.setVisible(true);
    // }

    // public static void init(){
    // TimeSeriesTest1 dragValueApp = new TimeSeriesTest1("时间序列拖动示例");
    // dragValueApp.pack();
    // RefineryUtilities.centerFrameOnScreen(dragValueApp);
    // dragValueApp.setVisible(true);
    // Container contentPane=getContentPane();
    // JLabel label=new JLabel("Hello World!");
    // getContentPane().add(label);
    // }
}
