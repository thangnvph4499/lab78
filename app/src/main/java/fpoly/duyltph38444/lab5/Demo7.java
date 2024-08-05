package fpoly.duyltph38444.lab5;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Demo7 extends AppCompatActivity {
    EditText txt1,txt2,txt3;
    Button btnSelect, btnInsert,btnUpdate,btnDelete;
    TextView tvKQ;
    Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo7);
        txt1=findViewById(R.id.txt1);
        txt2=findViewById(R.id.txt2);
        txt3=findViewById(R.id.txt3);
        btnInsert=findViewById(R.id.btnInsert);
        btnDelete=findViewById(R.id.btnDelete);
        btnUpdate=findViewById(R.id.btnUpdate);
        btnSelect=findViewById(R.id.btnSelect);
        tvKQ=findViewById(R.id.txtKQ);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
selectVolley();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateVolley();
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
insertVolley();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteVolley();
            }
        });

    }
    String strKQ="";
    private void selectVolley(){
        strKQ="";
        //1. tao request
        RequestQueue queue= Volley.newRequestQueue(context);
        //2.url:
        String url="https://hungnq28.000webhostapp.com/su2024/select.php";
        //3. goi request: JsonArrayRequest request=new JsonArrayRequest(url,thanhcong,thatbai);
        //lay ve object {
        JsonObjectRequest request1 =new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //lay ve array:  "products": [
                    JSONArray jsonArray=response.getJSONArray("products");
                    //for array
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject p=jsonArray.getJSONObject(i);//lay ve doi tuong i
                        String MaSP=p.getString("MaSP");
                        String TenSP=p.getString("TenSP");
                        String MoTa=p.getString("MoTa");
                        strKQ+="MaSP: "+MaSP+"; TenSP:"+TenSP+"; MoTa: "+MoTa+"\n";
                    }
                    tvKQ.setText(strKQ);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvKQ.setText(error.getMessage());
            }
        });
        //4. thuc thi request
        queue.add(request1);
    }

    //
    private void deleteVolley(){
        RequestQueue queue =Volley.newRequestQueue(context);
        String url ="https://hungnq28.000webhostapp.com/su2024/delete.php";
        StringRequest request =new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
tvKQ.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvKQ.setText((error.getMessage()));
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String ,String>mydata =new HashMap<>();
                mydata.put("MaSP",txt1.getText().toString());
                return mydata;
            }
        };
        queue.add(request);
    }
    private  void updateVolley(){
        RequestQueue queue =Volley.newRequestQueue(context);
        String url="https://hungnq28.000webhostapp.com/su2024/update.php";
        StringRequest request =new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
tvKQ.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvKQ.setText((error.getMessage()));
            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams()  {
                Map<String,String> mydata=new HashMap<>();
                mydata.put("MaSp",txt1.getText().toString());
                mydata.put("TenSP",txt2.getText().toString());
                mydata.put("MoTa",txt3.getText().toString());
                return mydata;
            }
        };
        queue.add(request);

    }
    private  void insertVolley(){
RequestQueue queue=Volley.newRequestQueue(context);
String url ="https://hungnq28.000webhostapp.com/su2024/insert.php";
StringRequest request =new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
    @Override
    public void onResponse(String response) {
tvKQ.setText(response.toString());
    }
}, new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError error) {
       tvKQ.setText(error.getMessage());
    }
}){
    @Nullable
    @Override
    protected Map<String, String> getParams(){
        Map<String,String> mydata=new HashMap<>();
        mydata.put("MaSp",txt1.getText().toString());
        mydata.put("TenSP",txt2.getText().toString());
        mydata.put("MoTa",txt3.getText().toString());
        return mydata;
    }
};
        queue.add(request);
    }



}