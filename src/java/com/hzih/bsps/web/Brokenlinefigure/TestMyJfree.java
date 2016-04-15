package com.hzih.bsps.web.Brokenlinefigure;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-1-30
 * Time: 下午3:12
 * To change this template use File | Settings | File Templates.
 */
public class TestMyJfree {

    ChartPanel frame1;
    public TestMyJfree(){
        XYDataset xydataset = createDataset();
        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart("业务流量统计图", "时间", "流量", xydataset, true, true, true);
        XYPlot xyplot = (XYPlot) jfreechart.getPlot();
        DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();
        dateaxis.setDateFormatOverride(new SimpleDateFormat("hh:mm"));
        frame1=new ChartPanel(jfreechart,true);
        dateaxis.setLabelFont(new Font("黑体",Font.BOLD,14));         //水平底部标题
        dateaxis.setTickLabelFont(new Font("宋体",Font.BOLD,12));  //垂直标题
        ValueAxis rangeAxis=xyplot.getRangeAxis();//获取柱状
        rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,15));
        jfreechart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
        jfreechart.getTitle().setFont(new Font("宋体",Font.BOLD,20));//设置标题字体

    }
    private static XYDataset createDataset() {  //这个数据集有点多，但都不难理解
        TimeSeries timeseries = new TimeSeries("实时流量",
                Minute.class);
        for(int i=0;i<=60;i++){
            timeseries.add(new Minute(i,new Hour()), Math.random()*300);
        }
//        timeseries.add(new Minute(), 300);
//        timeseries.add(new Minute(0,new Hour()), 0.D);
//        timeseries.add(new Minute(5,new Hour()), 200.D);
//        timeseries.add(new Minute(10,new Hour()), 110.D);
//        timeseries.add(new Minute(15,new Hour()), 120.30000000000001D);
//        timeseries.add(new Minute(20,new Hour()), 144.80000000000001D);
//        timeseries.add(new Minute(25,new Hour()), 135.59999999999999D);
//        timeseries.add(new Minute(30,new Hour()), 163.80000000000001D);
//        timeseries.add(new Minute(35,new Hour()), 148.30000000000001D);
//        timeseries.add(new Minute(40,new Hour()), 153.90000000000001D);
//        timeseries.add(new Minute(45,new Hour()), 142.69999999999999D);
//        timeseries.add(new Minute(50,new Hour()), 123.2D);
//        timeseries.add(new Minute(55,new Hour()), 131.80000000000001D);
//        timeseries.add(new Minute(60,new Hour()), 139.59999999999999D);
        TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
        timeseriescollection.addSeries(timeseries);
//        timeseriescollection.addSeries(timeseries1);
        return timeseriescollection;
    }
    public ChartPanel getChartPanel(){
        return frame1;

    }

    public static void main(String args[]){
        JFrame frame=new JFrame("Java数据统计图");
        frame.setLayout(new GridLayout(2,2,10,10));
//        frame.add(new BarChart().getChartPanel());           //添加柱形图
//        frame.add(new BarChart1().getChartPanel());          //添加柱形图的另一种效果
//        frame.add(new PieChart().getChartPanel());           //添加饼状图
//        frame.add(new TimeSeriesChart().getChartPanel());    //添加折线图
        frame.add(new TestMyJfree().getChartPanel());
        frame.setBounds(50, 50, 800, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}
