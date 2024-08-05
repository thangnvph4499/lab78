package fpoly.duyltph38444.lab5.demo5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fpoly.duyltph38444.lab5.R;
import fpoly.duyltph38444.lab5.demo5.delete.InterfaceDelete;
import fpoly.duyltph38444.lab5.demo5.select.InterfaceSelect;
import fpoly.duyltph38444.lab5.demo5.select.SvrResponseSelect;
import fpoly.duyltph38444.lab5.demo5.update.InterfaceUpdate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Demo5MainActivity extends AppCompatActivity {
    EditText txt1,txt2,txt3;
    TextView txtKQ;
    Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo5_main);
        txt1=findViewById(R.id.txt1);
        txt2=findViewById(R.id.txt2);
        txt3=findViewById(R.id.txt3);
        txtKQ=findViewById(R.id.txtKQ);
        btn1=findViewById(R.id.btn1);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                insertData(txt1,txt2,txt3,txtKQ);
//                selectData();
//       
                updateData(txt1,txt2,txt3,txtKQ);
            }
        });
    }
    private void insertData(EditText txt1, EditText txt2, EditText txt3, TextView tvKQ){
        //B1. tao doi tuong chua du lieu
        SanPham s=new SanPham(txt1.getText().toString(),
                txt2.getText().toString(),txt3.getText().toString());
        //b2. tao doi tuong retrofit
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://10.24.53.226/000/2024071/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //b3. goi ham insert
        InterfaceInsertSanPham insertSanPham
                =retrofit.create(InterfaceInsertSanPham.class);
        //chuan bi ham
        Call<SvrResponseSanPham> call
                =insertSanPham.insertSanPham(s.getMaSP(),s.getTenSP(),s.getMoTa());
      //thuc thi ham
        call.enqueue(new Callback<SvrResponseSanPham>() {
            //thanh cong
            @Override
            public void onResponse(Call<SvrResponseSanPham> call, Response<SvrResponseSanPham> response) {
                SvrResponseSanPham res=response.body();
                tvKQ.setText(res.getMessage());
            }
//that bai
            @Override
            public void onFailure(Call<SvrResponseSanPham> call, Throwable t) {
                tvKQ.setText(t.getMessage());
            }
        });
    }
    //update
    private void updateData(EditText txt1, EditText txt2, EditText txt3, TextView tvKQ){
        //B1. tao doi tuong chua du lieu
        SanPham s=new SanPham(txt1.getText().toString(),
                txt2.getText().toString(),txt3.getText().toString());
        //b2. tao doi tuong retrofit
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://10.24.53.226/000/2024071/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //b3. goi ham insert
        InterfaceUpdate updateSanPham
                =retrofit.create(InterfaceUpdate.class);
        //chuan bi ham
        Call<SvrResponseSanPham> call
                =updateSanPham.updateSanPham(s.getMaSP(),s.getTenSP(),s.getMoTa());
        //thuc thi ham
        call.enqueue(new Callback<SvrResponseSanPham>() {
            //thanh cong
            @Override
            public void onResponse(Call<SvrResponseSanPham> call, Response<SvrResponseSanPham> response) {
                SvrResponseSanPham res=response.body();
                tvKQ.setText(res.getMessage());
            }
            //that bai
            @Override
            public void onFailure(Call<SvrResponseSanPham> call, Throwable t) {
                tvKQ.setText(t.getMessage());
            }
        });
    }
    //DELETE
    private void DeleteData(EditText txt1){

        //b2. tao doi tuong retrofit
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://10.24.53.226/000/2024071/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //b3. goi ham insert
        InterfaceDelete deleteSanPham
                =retrofit.create(InterfaceDelete.class);
        //chuan bi ham
        Call<SvrResponseSanPham> call
                =deleteSanPham.deleteSanPham(txt1.getText().toString());
        //thuc thi ham
        call.enqueue(new Callback<SvrResponseSanPham>() {
            //thanh cong
            @Override
            public void onResponse(Call<SvrResponseSanPham> call, Response<SvrResponseSanPham> response) {
                SvrResponseSanPham res=response.body();
                txtKQ.setText(res.getMessage());
            }
            //that bai
            @Override
            public void onFailure(Call<SvrResponseSanPham> call, Throwable t) {
                txtKQ.setText(t.getMessage());
            }
        });
    }
    String strKQ="";
    List<SanPham> ls;
    private void selectData(){
        strKQ="";
        //1. Tao doi tuong retrofit
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://10.24.53.226/000/2024071/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //2. Goi ham select trong interface
        //2.1. Tao doi tuong
        InterfaceSelect interfaceSelect=retrofit.create(InterfaceSelect.class);
        //2.2. Chuan bij ham
        Call<SvrResponseSelect> call=interfaceSelect.selectSanPham();
        //2.3 thuc thi request
        call.enqueue(new Callback<SvrResponseSelect>() {
            @Override
            public void onResponse(Call<SvrResponseSelect> call, Response<SvrResponseSelect> response) {
                SvrResponseSelect res=response.body();//lay ket qua tu server tra ve
                //chuyen doi ket qua sang dang list
                ls=new ArrayList<>(Arrays.asList(res.getProducts()));
                //doc list
                for(SanPham p: ls){
                    strKQ+="MaSP: "+p.getMaSP()+"; TenSP: "+p.getTenSP()+"; MoTa: "+p.getMoTa()+"\n";
                }
                txtKQ.setText(strKQ);
            }

            @Override
            public void onFailure(Call<SvrResponseSelect> call, Throwable throwable) {
                 txtKQ.setText(throwable.getMessage());
            }

        });
    }
}