package id.odt.modul3rx.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import id.odt.modul3rx.R;
import id.odt.modul3rx.activity.DetailPasienActivity;
import id.odt.modul3rx.model.Pasien;

public class PasienAdapter extends RecyclerView.Adapter<PasienAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Pasien> jobList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView mTextNama;
        TextView mTextPosition;
        LinearLayout lRow;
        ImageView imgPasien;

        ViewHolder(View v) {
            super(v);
            mTextNama = v.findViewById(R.id.tv_name);
            mTextPosition = v.findViewById(R.id.tv_position);
            imgPasien = v.findViewById(R.id.img_pasien);
            lRow = v.findViewById(R.id.ll_row);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PasienAdapter(Context context, ArrayList<Pasien> jobList) {
        this.context = context;
        this.jobList = jobList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PasienAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pasien, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Pasien job = jobList.get(position);
        holder.mTextNama.setText(job.getNama());
        String gender = job.getPekerjaan();
        holder.mTextPosition.setText(job.getPekerjaan());
        holder.lRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailPasienActivity.class);
                intent.putExtra("name", job.getNama());
                intent.putExtra("pekerjaan", job.getPekerjaan());
                intent.putExtra("jk", job.getJk());
                context.startActivity(intent);
            }
        });

        if (job.getJk().equals("Male"))
            holder.imgPasien.setBackgroundResource(R.drawable.boy);
        else
            holder.imgPasien.setBackgroundResource(R.drawable.girl);
    }

    public void removeItem(int position) {
        jobList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Pasien item, int position) {
        jobList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return jobList.size();
    }
}