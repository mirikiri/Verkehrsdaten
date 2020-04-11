/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_Window;

import Controller_CellFactory.CDFDataListViewCell;
import Controller_CellFactory.HistogramDataListViewCell;
import Controller_CellFactory.HurstListViewCell;
import Controller_CellFactory.PaketListViewCell;
import CustomGraphs.BurstScatterChart;
import model.CDF;
import model.Hurst;
import model.Messung;
import model.Paket.Protocol;
import model.PaketLength;
import model.PaketLengthHistogram;
import model.PaketsPerTimeInterval;
import model.Zeitintervall;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.util.Callback;
import model.BurstTabMittelwert;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class MessungWindowVC implements Initializable {

    private Messung currentMessung;
    private Integer burst_activeFactor;
    private Integer burst_activeAnzahlMittelwerte;
    private Zeitintervall burst_activeInterval;
    private List<BurstTabMittelwert> mittelwerte;
    private final XYChart.Series<Number, Number> burst_activeSeries = new XYChart.Series();
    private final XYChart.Series<Number, Number> burst_series = new XYChart.Series();
    private final BurstScatterChart<Number, Number> burstChart = createBurstChart();

    @FXML
    private ListView<PaketsPerTimeInterval> z0_pakets;
    @FXML
    private LineChart<Number, Number> z0_chart;
    @FXML
    private ListView<Hurst> z0_hurst;
    @FXML
    private ListView<PaketsPerTimeInterval> z1_pakets;
    @FXML
    private LineChart<Number, Number> z1_chart;
    @FXML
    private ListView<Hurst> z1_hurst;
    @FXML
    private ListView<PaketsPerTimeInterval> z2_pakets;
    @FXML
    private LineChart<Number, Number> z2_chart;
    @FXML
    private ListView<Hurst> z2_hurst;
    @FXML
    private ListView<PaketsPerTimeInterval> z3_pakets;
    @FXML
    private LineChart<Number, Number> z3_chart;
    @FXML
    private ListView<Hurst> z3_hurst;
    @FXML
    private ListView<PaketsPerTimeInterval> z4_pakets;
    @FXML
    private LineChart<Number, Number> z4_chart;
    @FXML
    private ListView<Hurst> z4_hurst;
    @FXML
    private ListView<PaketLength> paket_length_data_udp;
    @FXML
    private LineChart<Number, Number> paket_length_chart_udp;
    @FXML
    private ListView<PaketLength> paket_length_data_tcp;
    @FXML
    private LineChart<Number, Number> paket_length_chart_tcp;
    @FXML
    private ListView<PaketLength> paket_length_data_other;
    @FXML
    private LineChart<Number, Number> paket_length_chart_other;
    @FXML
    private ListView<PaketLength> paket_length_data_total;
    @FXML
    private LineChart<Number, Number> paket_length_chart_total;
    @FXML
    private ListView<PaketLength> udp_data;
    @FXML
    private LineChart<Number, Number> udp_chart;
    @FXML
    private ListView<PaketLength> tcp_data;
    @FXML
    private LineChart<Number, Number> tcp_chart;
    @FXML
    private ListView<PaketLength> other_data;
    @FXML
    private LineChart<Number, Number> other_chart;
    @FXML
    private ListView<PaketLength> total_data;
    @FXML
    private LineChart<Number, Number> total_chart;
    @FXML
    private Label stat_filename;
    @FXML
    private Label stat_duration;
    @FXML
    private Label stat_totalPakets;
    @FXML
    private Label stat_UDPtotal;
    @FXML
    private Label stat_UDPprozent;
    @FXML
    private Label stat_TCPtotal;
    @FXML
    private Label stat_TCPprozent;
    @FXML
    private Label stat_andereTotal;
    @FXML
    private Label stat_andereProzent;
    @FXML
    private Label stat_bytesTotalUdp;
    @FXML
    private Label stat_byteskByteUdp;
    @FXML
    private Label stat_byteskBitUdp;
    @FXML
    private Label stat_bytesTotalTcp;
    @FXML
    private Label stat_byteskByteTcp;
    @FXML
    private Label stat_byteskBitTcp;
    @FXML
    private Label stat_bytesTotalOthers;
    @FXML
    private Label stat_byteskByteOthers;
    @FXML
    private Label stat_byteskBitOthers;
    @FXML
    private Label stat_bytesTotalGesamt;
    @FXML
    private Label stat_byteskByteGesamt;
    @FXML
    private Label stat_byteskBitGesamt;
    @FXML
    private Label stat_paketlengthUDPmin;
    @FXML
    private Label stat_paketlengthUDPmax;
    @FXML
    private Label stat_paketlengthUDPmean;
    @FXML
    private Label stat_paketlengthUDPmostUsed;
    @FXML
    private Label stat_paketlengthUDPmostUsedNumber;
    @FXML
    private Label stat_paketlengthUDPjumboNumber;
    @FXML
    private Label stat_paketlengthUDPjumbopercent;
    @FXML
    private Label stat_paketlengthTCPmin;
    @FXML
    private Label stat_paketlengthTCPmax;
    @FXML
    private Label stat_paketlengthTCPmean;
    @FXML
    private Label stat_paketlengthTCPmostUsed;
    @FXML
    private Label stat_paketlengthTCPmostUsedNumber;
    @FXML
    private Label stat_paketlengthTCPjumboNumber;
    @FXML
    private Label stat_paketlengthTCPjumbopercent;
    @FXML
    private Label stat_paketlengthOthermin;
    @FXML
    private Label stat_paketlengthOthermax;
    @FXML
    private Label stat_paketlengthOthermean;
    @FXML
    private Label stat_paketlengthOthermostUsed;
    @FXML
    private Label stat_paketlengthOthermostUsedNumber;
    @FXML
    private Label stat_paketlengthOtherjumboNumber;
    @FXML
    private Label stat_paketlengthOtherjumbopercent;
    @FXML
    private Label stat_paketlengthTotalmin;
    @FXML
    private Label stat_paketlengthTotalmax;
    @FXML
    private Label stat_paketlengthTotalmean;
    @FXML
    private Label stat_paketlengthTotalmostUsed;
    @FXML
    private Label stat_paketlengthTotalmostUsedNumber;
    @FXML
    private Label stat_paketlengthTotaljumboNumber;
    @FXML
    private Label stat_paketlengthTotaljumbopercent;
    @FXML
    private Label stat_paketlengthUDPmostUsedPercent;
    @FXML
    private Label stat_paketlengthTCPmostUsedPercent;
    @FXML
    private Label stat_paketlengthOthermostUsedPercent;
    @FXML
    private Label stat_paketlengthTotalmostUsedPercent;
    @FXML
    private Label stat_arrival_001min;
    @FXML
    private Label stat_arrival_001max;
    @FXML
    private Label stat_arrival_001percent;
    @FXML
    private Label stat_arrival_001mean;
    @FXML
    private Label stat_arrival_01min;
    @FXML
    private Label stat_arrival_01max;
    @FXML
    private Label stat_arrival_01mean;
    @FXML
    private Label stat_arrival_01percent;
    @FXML
    private Label stat_arrival_1min;
    @FXML
    private Label stat_arrival_1max;
    @FXML
    private Label stat_arrival_1mean;
    @FXML
    private Label stat_arrival_1percent;
    @FXML
    private Label stat_arrival_10min;
    @FXML
    private Label stat_arrival_10max;
    @FXML
    private Label stat_arrival_10mean;
    @FXML
    private Label stat_arrival_10percent;
    @FXML
    private Label stat_arrival_60min;
    @FXML
    private Label stat_arrival_60max;
    @FXML
    private Label stat_arrival_60mean;
    @FXML
    private Label stat_arrival_60percent;
    @FXML
    private PieChart paketPieChart;
    @FXML
    private ToggleButton togglePketLengthUDP;
    @FXML
    private BorderPane borderPaneBursts;
    @FXML
    private ComboBox<Zeitintervall> burst_intervalSelector;
    @FXML
    private ComboBox<Integer> burst_faktorCombBox;
    @FXML
    private ComboBox<Integer> burst_mittelwerteBox;
    @FXML
    private ToggleButton toggleCDF_UDP;
    @FXML
    private TabPane rootTabPane;
    @FXML
    private Tab zeitintervall0;
    @FXML
    private ChoiceBox<String> choice_Paketankunftsrate;
    @FXML
    private Tab zeitintervall1;
    @FXML
    private Tab zeitintervall2;
    @FXML
    private Tab zeitintervall3;
    @FXML
    private Tab zeitintervall4;
    @FXML
    private ChoiceBox<String> choice_Paketlänge;
    @FXML
    private Tab paketLengthHistogram;
    @FXML
    private Tab udp;
    @FXML
    private ChoiceBox<String> choice_CDF;
    @FXML
    private Tab tcp;
    @FXML
    private Tab tcp1;
    @FXML
    private Tab total;

    private String cdf_choice;
    private String laenge_choice;
    private String ankunftsrate_choice;
    @FXML
    private AnchorPane anchor_0_01;
    @FXML
    private AnchorPane anchor_0_1;
    @FXML
    private AnchorPane anchor_1;
    @FXML
    private AnchorPane anchor_10;
    @FXML
    private AnchorPane anchor_60;
    @FXML
    private AnchorPane anchor_laenge_udp;
    @FXML
    private AnchorPane anchor_laenge_tcp;
    @FXML
    private AnchorPane anchor_laenge_others;
    @FXML
    private AnchorPane anchor_laenge_total;
    @FXML
    private AnchorPane anchor_cdf_udp;
    @FXML
    private AnchorPane anchor_cdf_tcp;
    @FXML
    private AnchorPane anchor_cdf_others;
    @FXML
    private AnchorPane anchor_cdf_total;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mittelwerte = new ArrayList<>();
        borderPaneBursts.setCenter(burstChart);
        burstChart.getData().add(burst_series);
        
        choice_CDF.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            cdf_choice = newValue;
            if (cdf_choice.equals("UDP - CDF")) {
                anchor_cdf_udp.setVisible(true);
                anchor_cdf_tcp.setVisible(false);
                anchor_cdf_others.setVisible(false);
                anchor_cdf_total.setVisible(false);
            } else if (cdf_choice.equals("TCP - CDF")) {
                anchor_cdf_udp.setVisible(false);
                anchor_cdf_tcp.setVisible(true);
                anchor_cdf_others.setVisible(false);
                anchor_cdf_total.setVisible(false);
            } else if (cdf_choice.equals("others - CDF")) {
                anchor_cdf_udp.setVisible(false);
                anchor_cdf_tcp.setVisible(false);
                anchor_cdf_others.setVisible(true);
                anchor_cdf_total.setVisible(false);
            } else if (cdf_choice.equals("total - CDF")) {
                anchor_cdf_udp.setVisible(false);
                anchor_cdf_tcp.setVisible(false);
                anchor_cdf_others.setVisible(false);
                anchor_cdf_total.setVisible(true);                
            }
        });
        choice_Paketlänge.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            laenge_choice = newValue;
            if (laenge_choice.equals("UDP - Paketlänge")) {
                anchor_laenge_udp.setVisible(true);
                anchor_laenge_tcp.setVisible(false);
                anchor_laenge_total.setVisible(false);
                anchor_laenge_others.setVisible(false);
            } else if (laenge_choice.equals("TCP - Paketlänge")) {
                anchor_laenge_udp.setVisible(false);
                anchor_laenge_tcp.setVisible(true);
                anchor_laenge_total.setVisible(false);
                anchor_laenge_others.setVisible(false);
            } else if (laenge_choice.equals("others - Paketlänge")) {
                anchor_laenge_udp.setVisible(false);
                anchor_laenge_tcp.setVisible(false);
                anchor_laenge_total.setVisible(true);
                anchor_laenge_others.setVisible(false);
            } else if (laenge_choice.equals("total - Paketlänge")) {
                anchor_laenge_udp.setVisible(false);
                anchor_laenge_tcp.setVisible(false);
                anchor_laenge_total.setVisible(false);
                anchor_laenge_others.setVisible(true);
            }
        });
        choice_Paketankunftsrate.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            ankunftsrate_choice = newValue;
            if (ankunftsrate_choice.equals("0,01s")) {
                //pane_Einzel.setVisible(true);
            } else if (ankunftsrate_choice.equals("0,01s")) {
                anchor_0_01.setVisible(true);
                anchor_0_1.setVisible(false);
                anchor_1.setVisible(false);
                anchor_10.setVisible(false);
                anchor_60.setVisible(false);
            } else if (ankunftsrate_choice.equals("0,1s")) {
                anchor_0_01.setVisible(false);
                anchor_0_1.setVisible(true);
                anchor_1.setVisible(false);
                anchor_10.setVisible(false);
                anchor_60.setVisible(false);
            } else if (ankunftsrate_choice.equals("1s")) {
                anchor_0_01.setVisible(false);
                anchor_0_1.setVisible(false);
                anchor_1.setVisible(true);
                anchor_10.setVisible(false);
                anchor_60.setVisible(false);
            } else if (ankunftsrate_choice.equals("10s")) {
                anchor_0_01.setVisible(false);
                anchor_0_1.setVisible(false);
                anchor_1.setVisible(false);
                anchor_10.setVisible(true);
                anchor_60.setVisible(false);
            } else if (ankunftsrate_choice.equals("60s")) {
                anchor_0_01.setVisible(false);
                anchor_0_1.setVisible(false);
                anchor_1.setVisible(false);
                anchor_10.setVisible(false);
                anchor_60.setVisible(true);
            } 
        });
        
    }

    public void startUp() {
        setStatistikLabels();

        startTimeInterval(z0_pakets, z0_chart, z0_hurst, currentMessung.getZeitintervalle().get(0));
        startTimeInterval(z1_pakets, z1_chart, z1_hurst, currentMessung.getZeitintervalle().get(1));
        startTimeInterval(z2_pakets, z2_chart, z2_hurst, currentMessung.getZeitintervalle().get(2));
        startTimeInterval(z3_pakets, z3_chart, z3_hurst, currentMessung.getZeitintervalle().get(3));
        startTimeInterval(z4_pakets, z4_chart, z4_hurst, currentMessung.getZeitintervalle().get(4));

        setPaketLengthTab(paket_length_data_udp, paket_length_chart_udp, currentMessung.getPaketlengthHisto_udp());
        setPaketLengthTab(paket_length_data_tcp, paket_length_chart_tcp, currentMessung.getPaketlengthHisto_tcp());
        setPaketLengthTab(paket_length_data_other, paket_length_chart_other, currentMessung.getPaketlengthHisto_other());
        setPaketLengthTab(paket_length_data_total, paket_length_chart_total, currentMessung.getPaketlengthHisto_total());

        setCDFTab(udp_data, udp_chart, currentMessung.getCdf_udp());
        setCDFTab(tcp_data, tcp_chart, currentMessung.getCdf_tcp());
        setCDFTab(other_data, other_chart, currentMessung.getCdf_other());
        setCDFTab(total_data, total_chart, currentMessung.getCdf_total());

        if (!currentMessung.getBigFile()) {
            setBurstTab();
        }
        
    }

    private void setPaketLengthTab(ListView paketList, LineChart chart, PaketLengthHistogram histo) {
        paketList.setItems(histo.getShown_Paketlengths());
        paketList.setCellFactory(HistogramDataListView -> new HistogramDataListViewCell());
        chart.setData(histo.getPaket_length_series());
    }

    private void setCDFTab(ListView paketList, LineChart chart, CDF cdf) {
        paketList.setItems(cdf.getShown_Paketlengths());
        paketList.setCellFactory(CDFDataListView -> new CDFDataListViewCell());
        chart.setData(cdf.get_cdf_series());
    }

    private void setStatistikLabels() {
        setAllgemeinLabels();
        setArrivalRateLabels();
        setPaketLengthLabels();
        setByteLabels();
    }

    private void setAllgemeinLabels() {
        stat_filename.setText(currentMessung.getName());
        stat_duration.setText(Double.toString(currentMessung.getDuration()));
        stat_totalPakets.setText(Long.toString(currentMessung.getNumberOfPakets()));
        long stat_udp_pakets = currentMessung.getTypeTotalPakets(Protocol.UDP);
        stat_UDPtotal.setText(Long.toString(stat_udp_pakets));
        stat_UDPprozent.setText(Double.toString(Math.round((double) stat_udp_pakets / currentMessung.getNumberOfPakets() * 10000d) / 100d));
        long stat_tcp_pakets = currentMessung.getTypeTotalPakets(Protocol.TCP);
        stat_TCPtotal.setText(Long.toString(stat_tcp_pakets));
        stat_TCPprozent.setText(Double.toString(Math.round((double) stat_tcp_pakets / currentMessung.getNumberOfPakets() * 10000d) / 100d));
        long stat_rest_pakets = currentMessung.getTypeTotalPakets(Protocol.OTHER);
        stat_andereTotal.setText(Long.toString(stat_rest_pakets));
        stat_andereProzent.setText(Double.toString(Math.round((double) stat_rest_pakets / currentMessung.getNumberOfPakets() * 10000d) / 100d));

        ObservableList<PieChart.Data> paketPieChartData = FXCollections.observableArrayList();
        if (stat_udp_pakets != 0) {
            paketPieChartData.add(new PieChart.Data("UDP", stat_udp_pakets));
        }
        if (stat_tcp_pakets != 0) {
            paketPieChartData.add(new PieChart.Data("TCP", stat_tcp_pakets));
        }
        if (stat_rest_pakets != 0) {
            paketPieChartData.add(new PieChart.Data("Andere", stat_rest_pakets));
        }
        paketPieChart.setData(paketPieChartData);
    }

    private void setArrivalRateLabels() {
        setSingleIntervalLabels(currentMessung.getZeitintervalle().get(0), stat_arrival_001min, stat_arrival_001max, stat_arrival_001mean, stat_arrival_001percent);
        setSingleIntervalLabels(currentMessung.getZeitintervalle().get(1), stat_arrival_01min, stat_arrival_01max, stat_arrival_01mean, stat_arrival_01percent);
        setSingleIntervalLabels(currentMessung.getZeitintervalle().get(2), stat_arrival_1min, stat_arrival_1max, stat_arrival_1mean, stat_arrival_1percent);
        setSingleIntervalLabels(currentMessung.getZeitintervalle().get(3), stat_arrival_10min, stat_arrival_10max, stat_arrival_10mean, stat_arrival_10percent);
        setSingleIntervalLabels(currentMessung.getZeitintervalle().get(4), stat_arrival_60min, stat_arrival_60max, stat_arrival_60mean, stat_arrival_60percent);
    }

    private void setSingleIntervalLabels(Zeitintervall interval, Label min, Label max, Label mean, Label percent) {
        min.setText(Integer.toString(interval.getMinArrivalrate()));
        max.setText(Integer.toString(interval.getMaxArrivalrate()));
        mean.setText(Double.toString(interval.getMeanArrivalRate()));
        percent.setText(Double.toString(interval.getMaxPeak()));
    }

    public void setPaketLengthLabels() {
        setSingleProtocolPaketLengthLabels(Protocol.UDP, stat_paketlengthUDPmin, stat_paketlengthUDPmax, stat_paketlengthUDPmean, stat_paketlengthUDPmostUsed, stat_paketlengthUDPmostUsedNumber, stat_paketlengthUDPmostUsedPercent, stat_paketlengthUDPjumboNumber, stat_paketlengthUDPjumbopercent);
        setSingleProtocolPaketLengthLabels(Protocol.TCP, stat_paketlengthTCPmin, stat_paketlengthTCPmax, stat_paketlengthTCPmean, stat_paketlengthTCPmostUsed, stat_paketlengthTCPmostUsedNumber, stat_paketlengthTCPmostUsedPercent, stat_paketlengthTCPjumboNumber, stat_paketlengthTCPjumbopercent);
        setSingleProtocolPaketLengthLabels(Protocol.OTHER, stat_paketlengthOthermin, stat_paketlengthOthermax, stat_paketlengthOthermean, stat_paketlengthOthermostUsed, stat_paketlengthOthermostUsedNumber, stat_paketlengthOthermostUsedPercent, stat_paketlengthOtherjumboNumber, stat_paketlengthOtherjumbopercent);
        setSingleProtocolPaketLengthLabels(Protocol.TOTAL, stat_paketlengthTotalmin, stat_paketlengthTotalmax, stat_paketlengthTotalmean, stat_paketlengthTotalmostUsed, stat_paketlengthTotalmostUsedNumber, stat_paketlengthTotalmostUsedPercent, stat_paketlengthTotaljumboNumber, stat_paketlengthTotaljumbopercent);
    }

    private void setSingleProtocolPaketLengthLabels(Protocol protocol, Label min, Label max, Label mean, Label mU, Label mU_number, Label mU_per, Label jumbo_number, Label jumbo_percent) {
        PaketLength minPL = currentMessung.getMinPaketLength(protocol);
        if (minPL.getLength() == -1) {
            min.setText("n/A");
        } else {
            min.setText(Integer.toString(minPL.getLength()));
        }
        PaketLength maxPL = currentMessung.getMaxPaketLength(protocol);
        if (maxPL.getLength() == -1) {
            max.setText("n/A");
        } else {
            max.setText(Integer.toString(maxPL.getLength()));
        }
        mean.setText(Double.toString(currentMessung.getMeanPaketLength(protocol)));
        PaketLength mostUsed = currentMessung.getMostUsedPaket(protocol);
        if (mostUsed.getLength() == -1) {
            mU.setText("n/A");
        } else {
            mU.setText(Integer.toString(mostUsed.getLength()));
        }
        mU_number.setText(Integer.toString(mostUsed.getCount()));
        if (protocol == Protocol.TOTAL) {
            mU_per.setText(Double.toString(Math.round((double) mostUsed.getCount() / currentMessung.getNumberOfPakets() * 10000d) / 100d));
        } else {
            mU_per.setText(Double.toString(Math.round((double) mostUsed.getCount() / currentMessung.getTypeTotalPakets(protocol) * 10000d) / 100d));
        }
        jumbo_number.setText(Integer.toString(currentMessung.getNumberPaketsBigger1560Byte(protocol)));
        if (protocol == Protocol.TOTAL) {
            jumbo_percent.setText(Double.toString(Math.round((double) currentMessung.getNumberPaketsBigger1560Byte(protocol) / currentMessung.getNumberOfPakets() * 10000d) / 100d));
        } else {
            jumbo_percent.setText(Double.toString(Math.round((double) currentMessung.getNumberPaketsBigger1560Byte(protocol) / currentMessung.getTypeTotalPakets(protocol) * 10000d) / 100d));
        }
    }

    private void setByteLabels() {
        setSingleProtocolByteLabels(Protocol.UDP, stat_bytesTotalUdp, stat_byteskByteUdp, stat_byteskBitUdp);
        setSingleProtocolByteLabels(Protocol.TCP, stat_bytesTotalTcp, stat_byteskByteTcp, stat_byteskBitTcp);
        setSingleProtocolByteLabels(Protocol.OTHER, stat_bytesTotalOthers, stat_byteskByteOthers, stat_byteskBitOthers);
        setSingleProtocolByteLabels(Protocol.TOTAL, stat_bytesTotalGesamt, stat_byteskByteGesamt, stat_byteskBitGesamt);
    }

    private void setSingleProtocolByteLabels(Protocol protocol, Label bytesTotal, Label byteskByte, Label byteskBit) {
        long totalProtocolBytes = currentMessung.getTotalBytes(protocol);
        bytesTotal.setText(Long.toString(totalProtocolBytes));
        byteskByte.setText(Double.toString(currentMessung.getAverageKBytePerSecond(totalProtocolBytes)));
        byteskBit.setText(Double.toString(currentMessung.getAverageKBitPerSecond(totalProtocolBytes)));
    }

    private void startTimeInterval(ListView paket, LineChart chart, ListView hurst, Zeitintervall interval) {
        paket.setItems(interval.getXyListe());
        paket.setCellFactory(paketListView -> new PaketListViewCell());
        chart.setData(interval.getChartSeries());
        hurst.setItems(interval.getHurst_Faktoren());
        hurst.setCellFactory(HurstListView -> new HurstListViewCell());
    }

    /**
     * @param currentMessung the currentMessung to set
     */
    public void setCurrentMessung(Messung currentMessung) {
        this.currentMessung = currentMessung;
    }

    // BURST TAB -----------------------------------------------------------------------------------------------------------------------------
    // 3 control comboboxes behave like this:
    // when changing the factor, only datapoints get updated in updateBurstDataPoints()
    // when changing mittelwert number, those are updated in updateMittelwerteList(), which calls updateBurstDataPoints() afterwards to adapt data points to new mittelwert number
    // when changing interval, updateIntervalData() is called, which calls updateMittelwerteList() afterwards
    // leads to this update cascade: updateIntervalData() -> updateMittelwerteList() -> updateBurstDataPoints()
    public void setBurstTab() {
        //set initial selection and change-listener for the 3 comboboxes
        setIntervalSelectorComboBox();
        setMittelwertSelectorComboBox();
        setFaktorSelectorComboBox();

        //x axis auto range deactivated, for better visual impression. Setting x axis upper bound manual to a bit more than needed
        burstChart.getXAxis().setAutoRanging(false);
        NumberAxis xAxis = (NumberAxis) burstChart.getXAxis();
        int size = burst_activeInterval.getChartSeriesRaw().getData().size();
        xAxis.setUpperBound(Math.round(burst_activeInterval.getChartSeriesRaw().getData().get(size - 1).getXValue().doubleValue() + 3.0));

        //setting initial selected data by starting update cascade at top
        updateIntervalData();
    }

    //generating empty custom Burst-chart
    public BurstScatterChart<Number, Number> createBurstChart() {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("t [s]");
        yAxis.setLabel("Î» [1/Intervall]");
        BurstScatterChart<Number, Number> chart = new BurstScatterChart<>(xAxis, yAxis);
        return chart;
    }

    public void setIntervalSelectorComboBox() {
        //set choices in combobox and set initial selected
        burst_intervalSelector.getItems().clear();
        burst_intervalSelector.getItems().addAll(currentMessung.getZeitintervalle());
        burst_intervalSelector.getSelectionModel().select(0);
        burst_activeInterval = burst_intervalSelector.getSelectionModel().getSelectedItem();

        //cell-factory to set the name of Zeitintervalle as displayed item in the combobox
        Callback<ListView<Zeitintervall>, ListCell<Zeitintervall>> factory = lv -> new ListCell<Zeitintervall>() {
            @Override
            protected void updateItem(Zeitintervall item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };
        burst_intervalSelector.setCellFactory(factory);
        burst_intervalSelector.setButtonCell(factory.call(null));

        //change-listener: new interval is set active and update is called
        burst_intervalSelector.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            burst_activeInterval = newValue;
            updateIntervalData();
        });
    }

    public void setMittelwertSelectorComboBox() {
        //set choices in combobox and set initial selected
        burst_mittelwerteBox.getItems().clear();
        burst_mittelwerteBox.getItems().addAll(1, 2, 3, 5, 10, 20);
        burst_mittelwerteBox.getSelectionModel().select(0);
        burst_activeAnzahlMittelwerte = burst_mittelwerteBox.getSelectionModel().getSelectedItem();

        //change-listener: new mittelwert number is set active and update is called
        burst_mittelwerteBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            burst_activeAnzahlMittelwerte = newValue;
            updateMittelwerteList();
        });
    }

    public void setFaktorSelectorComboBox() {
        //set choices in combobox and set initial selected
        burst_faktorCombBox.getItems().clear();
        burst_faktorCombBox.getItems().addAll(3, 5, 8, 10, 15, 20, 50, 100);
        burst_faktorCombBox.getSelectionModel().select(0);
        burst_activeFactor = burst_faktorCombBox.getSelectionModel().getSelectedItem();

        //change-listener: new factor is set active and update is called
        burst_faktorCombBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            burst_activeFactor = newValue;
            updateBurstDataPoints(burst_activeFactor);
        });
    }

    public void updateIntervalData() {
//        burstChart.setAnimated(false);
        //clear old interval data and repopulate burst_activeSeries
        burst_activeSeries.getData().clear();
        List<Data<Number, Number>> newData = new ArrayList<>();
        for (Data<Number, Number> data : burst_activeInterval.getChartSeriesRaw().getData()) {
            newData.add(new Data<>(data.getXValue(), data.getYValue()));
        }
        burst_activeSeries.getData().addAll(newData);
        //updating mittelwerte since data for mittelwerte has changed
        updateMittelwerteList();
    }

    public void updateMittelwerteList() {
//        burstChart.setAnimated(false);
        //clear old mittelwerte data & markers
        double mittelwertLength = (double) burst_activeInterval.getPaketsPerInterval().size() / burst_activeAnzahlMittelwerte;
        double counter = 0.0;
        burstChart.removeAllHorizontalMarkers();
        mittelwerte.clear();

        //repopulate mittelwerte-list
        for (int i = 0; i < burst_activeAnzahlMittelwerte; i++) {
            int lb = (int) counter;
            int ub = (int) (counter + mittelwertLength) - 1;

            if (burst_activeInterval.getDownsampled()) {
                //find out where the bound-indices are in the downsampled chartseries
                int lb_InSeries = 0, ub_InSeries = 0;
                double lb_value = Double.parseDouble(burst_activeInterval.getXyListe().get(lb).getTime());
                double ub_value = Double.parseDouble(burst_activeInterval.getXyListe().get(ub - 1).getTime());
                List<Data<Number, Number>> activeIntervalData = burst_activeSeries.getData();
                for (int j = 0; j < activeIntervalData.size(); j++) {
                    if (lb_InSeries == 0 && activeIntervalData.get(j).getXValue().doubleValue() >= lb_value) {
                        lb_InSeries = j;
                    }
                    if (ub_InSeries == 0 && activeIntervalData.get(j).getXValue().doubleValue() >= ub_value) {
                        ub_InSeries = j;
                        break;
                    }
                }
                if (ub_InSeries == 0) {
                    ub_InSeries = activeIntervalData.size();
                }
                mittelwerte.add(new BurstTabMittelwert(burst_activeInterval.getMeanArrivalRate(lb, ub), lb, ub, burst_activeInterval.getName(), lb_InSeries, ub_InSeries));
            } else {
                //without downsampling, bounds in data and shown data are identical
                mittelwerte.add(new BurstTabMittelwert(burst_activeInterval.getMeanArrivalRate(lb, ub), lb, ub, burst_activeInterval.getName()));
            }            
            counter += mittelwertLength;
        }
        //add all horizontal markers and update datapoints, since mittelwerte changed
        burstChart.addAllHorizontalMarkers(mittelwerte);
        updateBurstDataPoints(burst_activeFactor);
    }

    public void updateBurstDataPoints(Integer factor) {
        //clear old burst data series & vertical markers. setAnimated false needed because of strange JavaFX bug with graphs being cleared while animated: https://stackoverflow.com/questions/13763770/null-pointer-exception-in-javafx-line-chart
        burstChart.removeAllVerticalMarkers();
        burstChart.setAnimated(false);
        burst_series.getData().clear();

        //repopulate burst data series
        List<Data<Number, Number>> activeIntervalData = burst_activeSeries.getData();
        List<Data<Number, Number>> newData = new ArrayList<>();

        for (BurstTabMittelwert burstTabMittelwert : mittelwerte) {
            double threshold = burstTabMittelwert.getValue() * factor;
            for (int i = burstTabMittelwert.getLb_inSeries(); i < burstTabMittelwert.getUb_inSeries(); i++) {
                Data<Number, Number> dataPoint = activeIntervalData.get(i);
                if (dataPoint.getYValue().doubleValue() > threshold) {
                    newData.add(dataPoint);
                }
            }
        }
        burst_series.getData().addAll(newData);
        //add new markers to graph
        burstChart.addAllVerticalMarkers(burst_series);

        burstChart.setAnimated(true);
    }

    // BURST TAB END -----------------------------------------------------------------------------------------------------------------------------
    @FXML
    private void toggleUDPZeroValues(ActionEvent event) {
        Boolean buttonPressed = togglePketLengthUDP.isSelected();
        if (Objects.equals(buttonPressed, Boolean.TRUE)) {
            hideZeroValuesPaketLenghtUDP();
        } else {
            addAllZeroValuesPaketLenghtUDP();
        }
    }

    public void addAllZeroValuesPaketLenghtUDP() {
        currentMessung.getPaketlengthHisto_udp().getShown_Paketlengths().removeAll(currentMessung.getPaketlengthHisto_udp().getPaketlengths());
        currentMessung.getPaketlengthHisto_udp().getShown_Paketlengths().addAll(currentMessung.getPaketlengthHisto_udp().getPaketlengths());
    }

    public void hideZeroValuesPaketLenghtUDP() {
        Iterator itr = currentMessung.getPaketlengthHisto_udp().getShown_Paketlengths().iterator();
        while (itr.hasNext()) {
            PaketLength paketLength = (PaketLength) itr.next();
            if (paketLength.getCount() == 0) {
                itr.remove();
            }
        }
    }

    @FXML
    private void toggleUDP_CDFZeroValues(ActionEvent event) {
        Boolean buttonPressed = toggleCDF_UDP.isSelected();
        if (Objects.equals(buttonPressed, Boolean.TRUE)) {
            hideZeroValuesCDF_UDP();
        } else {
            addAllZeroValuesCDF_UDP();
        }
    }
    
    public void addAllZeroValuesCDF_UDP() {
        currentMessung.getCdf_udp().getShown_Paketlengths().removeAll(currentMessung.getCdf_udp().getPaketlengths());
        currentMessung.getCdf_udp().getShown_Paketlengths().addAll(currentMessung.getCdf_udp().getPaketlengths());
    }

    public void hideZeroValuesCDF_UDP() {
        Iterator itr = currentMessung.getCdf_udp().getShown_Paketlengths().iterator();
        while (itr.hasNext()) {
            PaketLength paketLength = (PaketLength) itr.next();
            if (paketLength.getCount() == 0) {
                itr.remove();
            }
        }
    }
}
