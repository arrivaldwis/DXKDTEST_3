package id.odt.modul3rx.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.odt.modul3rx.R;
import id.odt.modul3rx.adapter.PasienAdapter;
import id.odt.modul3rx.model.Pasien;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.rv_data)
    RecyclerView rv_data;
    @BindView(R.id.fab_add)
    FloatingActionButton fab_add;
    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;
    private ArrayList<Pasien> pasiensList = new ArrayList<>();
    private PasienAdapter mAdapter;
    private boolean orientationPosition = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkAndShowSplash();
        setUI();
    }

    private void setUI() {
        ButterKnife.bind(this);
        fab_add.setOnClickListener(this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rv_data);
        setRV();
    }

    private void setRV() {
        layoutManagerPotrait();
        mAdapter = new PasienAdapter(this, pasiensList);
        rv_data.setAdapter(mAdapter);
    }

    private void layoutManagerPotrait() {
        LinearLayoutManager ll_manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_data.setLayoutManager(ll_manager);
    }

    private void layoutManagerLandscape() {
        GridLayoutManager ll_manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        rv_data.setLayoutManager(ll_manager);
    }

    private void checkAndShowSplash() {
        SharedPreferences prefs = getSharedPreferences("splash", MODE_PRIVATE);
        boolean isShow = prefs.getBoolean("show", false);
        if (!isShow) {
            SharedPreferences.Editor editor = getSharedPreferences("splash", MODE_PRIVATE).edit();
            editor.putBoolean("show", true);
            editor.apply();

            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
        }
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add);
        final TextInputEditText tv_name = dialog.findViewById(R.id.tv_name);
        final TextInputEditText tv_pekerjaan = dialog.findViewById(R.id.tv_pekerjaan);
        Button btn_submit = dialog.findViewById(R.id.btn_submit);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        final Spinner sp_gender = dialog.findViewById(R.id.sp_gender);

        populateGender(sp_gender);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tv_name.getText().toString().isEmpty()) {
                    tv_name.setError("Harap isi nama!");
                    return;
                }

                if (tv_pekerjaan.getText().toString().isEmpty()) {
                    tv_pekerjaan.setError("Harap isi pekerjaan!");
                    return;
                }

                Pasien model = new Pasien(tv_name.getText().toString(),
                        tv_pekerjaan.getText().toString(),
                        sp_gender.getSelectedItem().toString());

                pasiensList.add(model);
                mAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void populateGender(Spinner sp_gender) {
        ArrayList<String> gender = new ArrayList<>();
        gender.add("Male");
        gender.add("Female");
        sp_gender.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, gender));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add:
                showDialog();
                break;
        }
    }

    Parcelable mListState;

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        mListState = rv_data.getLayoutManager().onSaveInstanceState();
        outState.putParcelable("DATA", mListState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null)
            mListState = savedInstanceState.getParcelable("DATA");
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mListState != null) {
            rv_data.getLayoutManager().onRestoreInstanceState(mListState);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManagerLandscape();
        } else {
            layoutManagerPotrait();
        }
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            //if (viewHolder instanceof mAdapter.ViewHolder) {
                // get the removed item name to display it in snack bar
                String name = pasiensList.get(viewHolder.getAdapterPosition()).getNama();

                // backup of removed item for undo purpose
                final Pasien deletedItem = pasiensList.get(viewHolder.getAdapterPosition());
                final int deletedIndex = viewHolder.getAdapterPosition();

                // remove the item from recycler view
                mAdapter.removeItem(viewHolder.getAdapterPosition());

                // showing snack bar with Undo option
                Snackbar snackbar = Snackbar
                        .make(coordinator, name + " Berhasil dihapus!", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // undo is selected, restore the deleted item
                        mAdapter.restoreItem(deletedItem, deletedIndex);
                    }
                });
                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            //}
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            // view the background view
        }
    };
}
