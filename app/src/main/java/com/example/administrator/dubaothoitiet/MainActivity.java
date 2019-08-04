package com.example.administrator.dubaothoitiet;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.administrator.adapter.GioAdapter;
import com.example.administrator.model.Gio;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    TextView txtTenThanhPho,txtSunRise,txtSunSet,txtCoords, txtTenQuocGia, txtNhietDo, txtTrangThai, txtDoAm, txtMay, txtGio, txtNgayCapNhat,txtcontent;
    Button btnXacNhanCity, btnNgayKeTiep;
    ListView list12h;
    ScrollView scrollView;
    ArrayList<Gio> listGio;
    GioAdapter gioAdapter;
    ImageView imgTrangThai;
    Map<String, String> listKey;
    private Location location;
    //dt tương tác với Google API
    private GoogleApiClient gac;
    String vitri;



    public static AutoCompleteTextView edtSearch;
    String[] dsThanhPho;
    ArrayAdapter<String> adapterThanhPho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Khoitao();
        addControls();
        addEvents();
        // Trước tiên chúng ta cần phải kiểm tra play services
        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
        }




    }

    private void Khoitao() {
        listKey = new HashMap<>();
        listKey.put("An Giang", "435182");
        listKey.put("Bà Rịa - Vũng Tàu", "352089");
        listKey.put("Vũng Tàu", "352089");
        listKey.put("Bắc Giang", "352098");
        listKey.put("Bắc Kạn", "352107");
        listKey.put("Bạc Liêu", "352113");
        listKey.put("Bắc Ninh", "352118");
        listKey.put("Bến Tre", "352226");
        listKey.put("Hà Nội", "353412");
        listKey.put("Hanoi", "353412");
        listKey.put("Phủ Lý", "353404");
        listKey.put("Phu Ly", "353404");
        listKey.put("Thành phố Hồ Chí Minh", "353981");
        listKey.put("Ho Chi Minh City","353981");
        listKey.put("Thanh Pho Ho Chi Minh", "353981");
        listKey.put("Hà Nam","354310");
        listKey.put("Hạ Long", "355736");
        listKey.put("Hải Phòng", "353511");
        listKey.put("Bình Phước", "354474");
        listKey.put("Đà Nẵng","352954");
        listKey.put("Biên Hòa", "353021");
        listKey.put("Huế", "356204");
        listKey.put("Nha Trang","354222");

    }

    public void dispLocation(View view) {
        getLocation();
    }
    /**
     * Phương thức này dùng để hiển thị trên UI
     * */
    private ArrayList getLocation() {
        ArrayList<Double> arrayList=new ArrayList();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Kiểm tra quyền hạn
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        } else {
            location = LocationServices.FusedLocationApi.getLastLocation(gac);
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                String s1 = String.valueOf(latitude);
                String s2=String.valueOf(longitude);

                // Hiển thị
                //txtTenQuocGia.setText(latitude + ", " + longitude);
                GetWeatherData1(s1,s2);
            }
        }

        return arrayList;
    }
    /**
     * Tạo đối tượng google api client
     * */
    protected synchronized void buildGoogleApiClient() {
        if (gac == null) {
            gac = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API).build();
        }
    }
    /**
     * Phương thức kiểm chứng google play services trên thiết bị
     * */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, 1000).show();
            } else {
                Toast.makeText(this, "Thiết bị này không hỗ trợ.", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Đã kết nối với google api, lấy vị trí
        getLocation();
    }
    @Override
    public void onConnectionSuspended(int i) {
        gac.connect();
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Lỗi kết nối: " + connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }
    protected void onStart() {
        gac.connect();
        super.onStart();
    }
    protected void onStop() {
        gac.disconnect();
        super.onStop();
    }



    private void addEvents() {

        btnXacNhanCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.setBackgroundResource(R.drawable.bg3);

                String city = edtSearch.getText().toString();
                //Toast.makeText(MainActivity.this, city, Toast.LENGTH_SHORT).show();
                //LayLocationKey(city);
                //String thanhpho = LayLocationKey(city);
                String key = listKey.get(city);
               // Toast.makeText(MainActivity.this, key, Toast.LENGTH_SHORT).show();
                //LayThoiTiet12h(key);
                    GetWeatherData(city);

            }
        });
        btnNgayKeTiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyHienThiCacNgayKeTiep();
            }
        });
    }

    private void xuLyHienThiCacNgayKeTiep() {
        String city = edtSearch.getText().toString();
        Intent intent = new Intent(MainActivity.this, ManHinhCacNgayKeTiepActivity.class);
        intent.putExtra("city", city);
        startActivity(intent);
    }
    public void GetWeatherData1(String data1,String data2) {

        String url = "http://api.openweathermap.org/data/2.5/weather?lat=" + data1 + "&lon="+data2+"&units=metric&appid=9956a5636758a4d3c680a758e3d684d8&lang=vi";
        LayThoiTiet(url, true);
    }
    private void LayThoiTiet(String url, final Boolean kt)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                            String country = jsonObjectSys.getString("country");
                            //txtTenQuocGia.setText("Tên Quốc Gia: " + country);
                            String sunrise =jsonObjectSys.getString("sunrise");
                            String sunset =jsonObjectSys.getString("sunset");

                            long l1 = Long.valueOf(sunrise);
                            Date date1 = new Date(l1 * 1000L);
                            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(" HH:mm");
                            String dayAfterFormat1 = simpleDateFormat1.format(date1);
                            txtSunRise.setText(dayAfterFormat1);

                            long l2 = Long.valueOf(sunset);
                            Date date2 = new Date(l2 * 1000L);
                            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(" HH:mm");
                            String dayAfterFormat2 = simpleDateFormat2.format(date2);
                            txtSunSet.setText(dayAfterFormat2);

                            String day = jsonObject.getString("dt");
                            String name = jsonObject.getString("name");
                            txtTenThanhPho.setText( name+" , "+country);
                            String key = listKey.get(name);
                            if (kt) LayThoiTiet12h(key);
                            String content = getResources().getString(R.string.thoi_tiet_o);
                            txtcontent.setText(content+" "+name);

                            long l = Long.valueOf(day);
                            Date date = new Date(l * 1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH:mm:ss");
                            String dayAfterFormat = simpleDateFormat.format(date);
                            txtNgayCapNhat.setText(dayAfterFormat);

                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                            //String status = jsonObjectWeather.getString("main");
                            String status = jsonObjectWeather.getString("description");
                            String icon = jsonObjectWeather.getString("icon");
                            if (status.indexOf("mưa") >= 0)
                            {
                                Glide.with(MainActivity.this).load(R.drawable.icon_rain).into(imgTrangThai);
                                setBgMua();
                            }
                            else if (status.indexOf("rông") >= 0|| status.indexOf("bão") >= 0)
                            {
                                Glide.with(MainActivity.this).load(R.drawable.rong_bao).into(imgTrangThai);
                                setBgMua();
                            }
                            else Picasso.with(MainActivity.this).load("http://openweathermap.org/img/w/" + icon + ".png").into(imgTrangThai);
                            txtTrangThai.setText(status);

                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String nhietdo = jsonObjectMain.getString("temp");
                            String doam = jsonObjectMain.getString("humidity");

                            //chuyển nhiệt độ thành 1 số nguyên
                            Double d = Double.valueOf(nhietdo);
                            String nhietDoChuyenSangSoNguyen = String.valueOf(d.intValue());
                            txtNhietDo.setText(nhietDoChuyenSangSoNguyen + "℃");
                            txtDoAm.setText(doam + "%");

                            JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                            String tocDoGio = jsonObjectWind.getString("speed");
                            txtGio.setText(tocDoGio + "m/s");

                            JSONObject jsonObjectCloud = jsonObject.getJSONObject("clouds");
                            String may = jsonObjectCloud.getString("all");
                            txtMay.setText(may+"%");

                            JSONObject jsonObjectcoords =jsonObject.getJSONObject("coord");
                            String lon=jsonObjectcoords.getString("lon");
                            String lat=jsonObjectcoords.getString("lat");
                            txtCoords.setText("[ "+lon+","+lat+" ]");



                        } catch (JSONException e) {
                            Log.e("LOI", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }
    //lấy key location của thành phố
    private void LayLocationKey(final String thanhpho)
    {
        String city = thanhpho.replace(" ", "%20");
        Toast.makeText(this, city, Toast.LENGTH_SHORT).show();
        String url = "http://dataservice.accuweather.com/locations/v1/cities/autocomplete?apikey=mIDcfsvTpxjIcdtCxNb0pXdJNekCLR7X&q="+city+"&language=vi/";

      // String url ="http://dataservice.accuweather.com/locations/v1/cities/autocomplete?apikey=mIDcfsvTpxjIcdtCxNb0pXdJNekCLR7X&q=H%C3%A0%20N%E1%BB%99i&language=vi";
       // String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q="+thanhpho+"&units=metric&cnt=7&appid=9956a5636758a4d3c680a758e3d684d8&lang=vi";
        Log.d("AAA", url);
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest jsonArrayRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    LayThoiTiet12h(jsonObject.getString("Key"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Lỗi loaction 2", Toast.LENGTH_SHORT).show();
                Log.d("AAA", "Lỗi : "+error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    private void LayThoiTiet12h(String  locationKey)
    {
        listGio.clear();
       String url = "http://dataservice.accuweather.com/forecasts/v1/hourly/12hour/"+locationKey+"?apikey=mIDcfsvTpxjIcdtCxNb0pXdJNekCLR7X&language=vi-vn&details=true&metric=true";
       // Log.d("AAA", url);
       RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
              for (int i = 0; i < response.length(); i++)
              {
                  try {
                      JSONObject jsonObject = response.getJSONObject(i);
                      String date = jsonObject.getString("DateTime");
                      long date1 = jsonObject.getLong("EpochDateTime");
                      Date ngay = new Date(date1*1000L);
                      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                      String gio = simpleDateFormat.format(ngay);
                      int icon = jsonObject.getInt("WeatherIcon");
                      JSONObject jsonTemperature = jsonObject.getJSONObject("Temperature");
                      String nhietdo = jsonTemperature.getDouble("Value")+"℃";//+jsonTemperature.getString("Unit");
                      JSONObject jsonWind = jsonObject.getJSONObject("Wind");
                      JSONObject jsonSpeed = jsonWind.getJSONObject("Speed");
                      String tocdogio = jsonSpeed.getDouble("Value")+jsonSpeed.getString("Unit");
                      JSONObject jsonRain = jsonObject.getJSONObject("Rain");
                      String luongmua = jsonRain.getDouble("Value")+jsonRain.getString("Unit");
                      listGio.add(new Gio(gio, icon,nhietdo,tocdogio, luongmua));
                  } catch (JSONException e) {
                      e.printStackTrace();
                    Toast.makeText(MainActivity.this, " Lỗi lấy thời tiết 12h", Toast.LENGTH_SHORT).show();
                  }

              }
              gioAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, " Lỗi lấy thời tiết 12h", Toast.LENGTH_SHORT).show();
                Log.d("AAA", "lỗi 12h: "+error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    private void setBgMua()
    {
        scrollView.setBackgroundResource(R.drawable.bg_mua);
        AnimationDrawable progressAnimation = (AnimationDrawable) scrollView.getBackground();
        progressAnimation.start();
    }
    public void GetWeatherData(final String data) {

        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + data + "&units=metric&appid=9956a5636758a4d3c680a758e3d684d8&lang=vi";
        LayThoiTiet(url, false);
    }

    private void addControls() {
        scrollView = (ScrollView) findViewById(R.id.scrollView1);
        edtSearch = findViewById(R.id.edtSearch);
        txtTenThanhPho = findViewById(R.id.txtTenThanhPho);
       // txtTenQuocGia = findViewById(R.id.txtTenQuocGia);
        txtNhietDo = findViewById(R.id.txtNhietDo);
        txtTrangThai = findViewById(R.id.txtTrangThai);
        txtDoAm = findViewById(R.id.txtDoAm);
        txtMay = findViewById(R.id.txtMay);
        txtGio = findViewById(R.id.txtGio);
        txtNgayCapNhat = findViewById(R.id.txtNgayCapNhat);
        btnXacNhanCity = findViewById(R.id.btnXacNhanCity);
        btnNgayKeTiep = findViewById(R.id.btnNgayKeTiep);
        imgTrangThai = findViewById(R.id.imgTrangThai);
        txtcontent= findViewById(R.id.txtcontent);
        txtSunRise=findViewById(R.id.textviewSunrise);
        txtSunSet=findViewById(R.id.textviewSunset);
        txtCoords=findViewById(R.id.textviewCoord);
        list12h = (ListView) findViewById(R.id.list12h);
        listGio = new ArrayList<>();
        gioAdapter = new GioAdapter(MainActivity.this, R.layout.dong_gio, listGio);
        list12h.setAdapter(gioAdapter);
        dsThanhPho = getResources().getStringArray(R.array.thanhpho);
        adapterThanhPho = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,dsThanhPho);
        edtSearch.setAdapter(adapterThanhPho);

    }
}



