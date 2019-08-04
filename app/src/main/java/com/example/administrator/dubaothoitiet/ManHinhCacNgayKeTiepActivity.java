package com.example.administrator.dubaothoitiet;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.adapter.AdapterNgay;
import com.example.administrator.model.Ngay;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ManHinhCacNgayKeTiepActivity extends AppCompatActivity implements OnChartValueSelectedListener {
    private CombinedChart mChart;
    ArrayList<Integer> ds=new ArrayList<>();
    TextView txtThanhPho,txtToaDo;
    ImageButton btnBack;
    ListView lvNgay;
    ArrayList<Ngay> dsNgay;
    AdapterNgay adapterNgay;
    int[] data12 = new int[7];
    String thanhPho = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_cac_ngay_ke_tiep);
        addControls();
        addEvents();
        Intent intent = getIntent();
        String city = intent.getStringExtra("city");
        if (city.equals("")) {
            thanhPho = "Phu Ly";
            int[] data1 =Get7DaysData(thanhPho);
            //chart(data1);


        }else {
            int[] data1 = Get7DaysData(city);
            //chart(data1);

        }

        //chart




    }


    private void addEvents() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                MainActivity.edtSearch.setText("");
                MainActivity.edtSearch.requestFocus();
            }
        });
    }


    private void addControls() {
        txtToaDo= findViewById(R.id.txttoado);
        txtThanhPho = findViewById(R.id.txtThanhPho);
        btnBack = findViewById(R.id.btnBack);
        lvNgay = findViewById(R.id.lvNgay);
        dsNgay = new ArrayList<Ngay>();
        adapterNgay = new AdapterNgay(ManHinhCacNgayKeTiepActivity.this,R.layout.item,dsNgay);
        lvNgay.setAdapter(adapterNgay);
    }
    private void TaoBieuDo(int[] data2, String[] data3)
    {
        mChart = (CombinedChart) findViewById(R.id.combinedChart);
        mChart.getDescription().setEnabled(false);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);
        mChart.setOnChartValueSelectedListener(this);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);

        final List<String> xLabel = new ArrayList<>();
        for (int i = 0; i < 7; i++) xLabel.add(data3[i]);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xLabel.get((int) value % xLabel.size());
            }
        });

        CombinedData data = new CombinedData();
        LineData lineDatas = new LineData();
        lineDatas.addDataSet((ILineDataSet) dataChart(data2));

        data.setData(lineDatas);

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);

        mChart.setData(data);
        mChart.invalidate();
    }
    private static DataSet dataChart(int[] data) {

        LineData d = new LineData();


        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < 7; index++) {
            entries.add(new Entry(index, data[index]));
        }

        LineDataSet set = new LineDataSet(entries, "Biểu đồ nhiệt độ Max");
        set.setColor(Color.GREEN);
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.GREEN);
        set.setCircleRadius(5f);
        set.setFillColor(Color.GREEN);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.GREEN);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return set;
    }
    public void chart(int[]data1){
        mChart = (CombinedChart) findViewById(R.id.combinedChart);
        mChart.getDescription().setEnabled(false);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);
        mChart.setOnChartValueSelectedListener(this);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);

        final List<String> xLabel = new ArrayList<>();
        xLabel.add("1");
        xLabel.add("2");
        xLabel.add("3");
        xLabel.add("4");
        xLabel.add("5");
        xLabel.add("6");
        xLabel.add("7");
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xLabel.get((int) value % xLabel.size());
            }
        });

        CombinedData data = new CombinedData();
        LineData lineDatas = new LineData();



        LineData d = new LineData();


        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < 7; index++) {
            entries.add(new Entry(index, data1[index]));
        }
        LineDataSet set = new LineDataSet(entries, "Biểu đồ nhiệt độ Max");
        set.setColor(Color.GREEN);
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.GREEN);
        set.setCircleRadius(5f);
        set.setFillColor(Color.GREEN);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.GREEN);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);


        lineDatas.addDataSet((ILineDataSet) set);

        data.setData(lineDatas);

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);

        mChart.setData(data);
        mChart.invalidate();
    }

    public int[] Get7DaysData( String data) {
        final String[] data3 = new String[7];
        String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q="+data+"&units=metric&cnt=7&appid=9956a5636758a4d3c680a758e3d684d8&lang=vi";
        RequestQueue requestQueue = Volley.newRequestQueue(ManHinhCacNgayKeTiepActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                            String city = jsonObjectCity.getString("name");
                            txtThanhPho.setText(city);
                            JSONObject jsonObjectcoord=jsonObjectCity.getJSONObject("coord");
                            String lat=jsonObjectcoord.getString("lat");
                            String lon=jsonObjectcoord.getString("lon");
                            txtToaDo.setText("[ "+lat+" , "+lon+" ]");

                            JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                            for (int i = 0; i < jsonArrayList.length(); i++)
                            {
                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                                String day = jsonObjectList.getString("dt");

                                long d = Long.valueOf(day);
                                Date date = new Date(d * 1000L);

                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd");
                                String dayAfterFormat = simpleDateFormat.format(date);
                                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("EEEE");
                                data3[i] = simpleDateFormat1.format(date);
                                JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("temp");
                                String nhietDoMin = jsonObjectTemp.getString("min");
                                String nhietDoMax = jsonObjectTemp.getString("max");

                                Double min = Double.valueOf(nhietDoMin);
                                Double max = Double.valueOf(nhietDoMax);

                                String nhietDoMinChuyenSangNguyen = String.valueOf(min.intValue());
                                String nhietDoMaxChuyenSangNguyen = String.valueOf(max.intValue());

                                JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                                String icon = jsonObjectWeather.getString("icon");
                                String trangthai = jsonObjectWeather.getString("description");
                                int a=Integer.parseInt(nhietDoMaxChuyenSangNguyen);
                                data12[i]=a;
                                dsNgay.add(new Ngay(dayAfterFormat,trangthai,nhietDoMinChuyenSangNguyen,nhietDoMaxChuyenSangNguyen,icon));
                            }
                            adapterNgay.notifyDataSetChanged();
                            TaoBieuDo(data12, data3);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
        return data12;

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Toast.makeText(this, "Value: "
                + e.getY()
                + ", index: "
                + h.getX()
                + ", DataSet index: "
                + h.getDataSetIndex(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected() {

    }


}
