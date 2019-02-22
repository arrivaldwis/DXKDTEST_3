package id.odt.modul3rx.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.odt.modul3rx.R;

public class DetailPasienActivity extends AppCompatActivity {

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_pekerjaan)
    TextView tv_pekerjaan;
    @BindView(R.id.img_pasien)
    ImageView img_pasien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pasien);
        setUI();
        getExtrasIntent();
    }

    private void setUI() {
        ButterKnife.bind(this);
    }

    private void getExtrasIntent() {
        String name = getIntent().getStringExtra("name");
        String jk = getIntent().getStringExtra("jk");
        String pekerjaan = getIntent().getStringExtra("pekerjaan");
        tv_name.setText(name);
        tv_pekerjaan.setText(pekerjaan);


        if (jk.equals("Male"))
            img_pasien.setBackgroundResource(R.drawable.boy);
        else
            img_pasien.setBackgroundResource(R.drawable.girl);
    }
}
