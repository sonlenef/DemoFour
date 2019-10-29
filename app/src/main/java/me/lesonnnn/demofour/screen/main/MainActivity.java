package me.lesonnnn.demofour.screen.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import me.lesonnnn.demofour.R;
import me.lesonnnn.demofour.screen.info.InfoActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEdtURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mEdtURL = findViewById(R.id.edtURL);
        Button btnGetData = findViewById(R.id.btnGetData);

        btnGetData.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnGetData) {
            String urlString = mEdtURL.getText().toString().trim();
            if (!urlString.equals("")) {
                Intent i = new Intent(MainActivity.this, InfoActivity.class);
                i.putExtra("urlString", urlString);
                startActivity(i);
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!!!", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}
