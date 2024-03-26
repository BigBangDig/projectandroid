package com.example.projectandroid;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.projectandroid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemSelectedListener {
    private static final String URLTAMPIL = "http://192.168.23.179/fetchdata/select.php";
    private static final String URLDELETE = "http://192.168.23.179/fetchdata/delete.php";
    private static final String URLINSERT = "http://192.168.23.179/fetchdata/insert.php";
    private static final String URLUBAH = "http://192.168.23.179/fetchdata/edit.php";

    private ListView list;
    private AlertDialog.Builder dialog;
    private SwipeRefreshLayout swipe;
    private List<Data> itemList = new ArrayList<>();
    private MhsAdapter adapter;
    private Spinner spinAlamat;
    private EditText tid, tnik, tnama;
    private String id, nik, nama, alamat;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipe = findViewById(R.id.swipe);
        list = findViewById(R.id.list);

        fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogForm("", "", "", "", "Tambah");
            }
        });

        adapter = new MhsAdapter(MainActivity.this, itemList);
        list.setAdapter(adapter);

        swipe.setOnRefreshListener(this);
        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
                itemList.clear();
                adapter.notifyDataSetChanged();
                callVolley();
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String idx = itemList.get(position).getId();
                final CharSequence[] dialogitem = {"Edit", "Delete"};
                dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ubah(idx);
                                break;
                            case 1:
                                hapus(idx);
                                break;
                        }
                    }
                }).show();
                return false;
            }
        });
    }

    @Override
    public void onRefresh() {
        callVolley();
    }

    private void callVolley() {
        itemList.clear();
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);

        JsonArrayRequest jArr = new JsonArrayRequest(URLTAMPIL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Data item = new Data();
                        item.setId(obj.getString("id"));
                        item.setNik(obj.getString("nik"));
                        item.setNama(obj.getString("nama"));
                        item.setAlamat(obj.getString("alamat"));
                        itemList.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
                swipe.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "gagal koneksi ke server, cek setingan koneksi anda", Toast.LENGTH_LONG).show();
                swipe.setRefreshing(false);
            }
        });

        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mRequestQueue.add(jArr);
    }

    private void ubah(String id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLUBAH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String idx = jObj.getString("id");
                            String nikx = jObj.getString("nik");
                            String namax = jObj.getString("nama");
                            String alamatx = jObj.getString("alamat");
                            DialogForm(idx, nikx, namax, alamatx, "UPDATE");
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "gagal koneksi ke server, cek setingan koneksi anda", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }

    private void hapus(String id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLDELETE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callVolley();
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "gagal koneksi ke server, cek setingan koneksi anda", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        alamat = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    void DialogForm(String idx, String nikx, String namax, String alamatx, String button) {
        dialog = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.form_mahasiswa, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.drawable.iconbdt);
        // Menambahkan code yang hilang untuk menyelesaikan metode DialogForm
        dialog.setTitle("Tambah Data");

        tid = dialogView.findViewById(R.id.inId);
        tnik = dialogView.findViewById(R.id.inNik);
        tnama = dialogView.findViewById(R.id.inNama);
        spinAlamat = dialogView.findViewById(R.id.spinneralamat);

        // Set nilai untuk masing-masing EditText
        tid.setText(idx);
        tnik.setText(nikx);
        tnama.setText(namax);

        // Set nilai untuk Spinner alamat
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.daftar_kota, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAlamat.setAdapter(adapter);
        // Pilih posisi spinner berdasarkan alamatx
        if (alamatx != null) {
            spinAlamat.setSelection(getPosisi(spinAlamat, alamatx));
        }

        dialog.setPositiveButton(button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                id = tid.getText().toString();
                nik = tnik.getText().toString();
                nama = tnama.getText().toString();
                // Ambil nilai alamat langsung dari Spinner
                alamat = spinAlamat.getSelectedItem().toString();

                // Panggil fungsi simpan
                simpan();

                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private int getPosisi(Spinner spinAlamat, String alamat) {
        for (int i = 0; i < spinAlamat.getCount(); i++) {
            if (alamat.equals(spinAlamat.getItemAtPosition(i).toString())) {
                return i;
            }
        }
        return 0; // Mengembalikan nilai tidak valid jika tidak ditemukan kesamaan
    }

    void simpan() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLINSERT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callVolley();
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "gagal koneksi ke server, cek setingan koneksi anda", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                if (id.isEmpty()) {
                    params.put("nik", nik);
                    params.put("nama", nama);
                    params.put("alamat", alamat);
                } else {
                    params.put("id", id);
                    params.put("nik", nik);
                    params.put("nama", nama);
                    params.put("alamat", alamat);
                }
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }
}

