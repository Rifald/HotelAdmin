package com.ribal.hoteladmin;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TiketAdapter extends RecyclerView.Adapter<TiketAdapter.TiketViewHolder>{
    private Context mCtx;
    private List<Tiket> tiketList;


    public TiketAdapter(Context mCtx, List<Tiket> tiketList) {
        this.mCtx = mCtx;
        this.tiketList = tiketList;
    }

    @NonNull
    @Override
    public TiketAdapter.TiketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TiketAdapter.TiketViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.layout_tiket, parent, false)
        );
    }
    @Override
    public void onBindViewHolder(@NonNull TiketAdapter.TiketViewHolder holder, final int position) {
        Tiket tiket= tiketList.get(position);

        holder.textViewNama.setText(tiket.getHotel());


        holder.textViewNama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 *  Kodingan untuk tutorial Selanjutnya :p Read detail data
                 */
                mCtx.startActivity(TiketDetail.getActIntent((Activity) mCtx).putExtra("data", tiketList.get(position)));


            }
        });
    }

    @Override
    public int getItemCount() {
        return tiketList.size();
    }


    class TiketViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewNama ;


        public TiketViewHolder(View itemView) {
            super(itemView);

            textViewNama = itemView.findViewById(R.id.hotel_nama);

        }
    }
}
