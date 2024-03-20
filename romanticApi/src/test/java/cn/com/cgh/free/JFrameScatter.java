package cn.com.cgh.free;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JFrameScatter {
    public static void main(String[] args) {
        // 添加数据
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries("");
        ArrayList<Integer> o1 = new ArrayList<>();
        ArrayList<Integer> o2 = new ArrayList<>();
        ArrayList<Integer> o3 = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i < 30){
                o1.add(i);
            }else if   (i< 40){
                o2.add(i);
            }else{
                o3.add(i);
            }
            series1.add(i, Math.random()*100);
        }
        dataset.addSeries(series1);

        // 创建JFreeChart对象
        JFreeChart chart = ChartFactory.createScatterPlot(
                null,
                 "重要特征                                    次要特征                                    一般特征","", dataset);
        chart.setBackgroundPaint(new Color(255, 255, 255));
        XYPlot plot = chart.getXYPlot();
        plot.setOutlinePaint(null);
//        plot.setRangeGridlinesVisible(false);
        plot.setBackgroundPaint(new Color(255, 255, 255));
        plot.getDomainAxis().setTickLabelFont(new Font("宋体",Font.PLAIN,12));
        plot.mapDatasetToRangeAxes(0, o1);
        plot.mapDatasetToRangeAxes(1, o2);
        plot.mapDatasetToRangeAxes(2, o3);
        // x 轴
        plot.getDomainAxis().setPositiveArrowVisible(true);
//        plot.getDomainAxis().setTickLabelsVisible(false);
//        plot.getDomainAxis().setTickMarksVisible(false);
        // y 轴
        // 坐标轴标尺值是否显示
//        plot.getRangeAxis().setTickLabelsVisible(false);
        // 显示正向箭头
        plot.getRangeAxis().setPositiveArrowVisible(true);
        XYItemRenderer renderer = plot.getRenderer();
        renderer.setSeriesShape(0, new Ellipse2D.Double(-2, -2, 2, 2));
//        plot.getRangeAxis().setStandardTickUnits(new NumberTickUnitSource());
        //自动设置数据轴数据范围时数据范围的最小跨度
        // 利用awt进行显示
        ChartFrame chartFrame = new ChartFrame("自有渠道", chart);
        chartFrame.pack();
        chartFrame.setVisible(true);
        try {
            ChartUtils.saveChartAsJPEG(new File("/Users/cgh/Desktop/chart_Scatter.png"), chart, 800, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
