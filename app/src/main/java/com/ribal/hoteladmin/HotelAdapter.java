package com.ribal.hoteladmin;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    private Context mCtx;
    private List<Hotel> hotelList;


    public HotelAdapter(Context mCtx, List<Hotel> hotelList) {
        this.mCtx = mCtx;
        this.hotelList = hotelList;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HotelViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.layout_hotel, parent, false)
        );
    }
    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, final int position) {
        Hotel hotel = hotelList.get(position);

        holder.textViewNama.setText(hotel.getNama());
        holder.textViewAlamat.setText("Alamat : " + hotel.getAlamat());
        holder.textViewHarga.setText("Harga : Rp. " + hotel.getHarga());
        holder.textViewJumlah.setText("Tersedia " + hotel.getJumlah() + " Kamar");
        Picasso.get()
                .load(hotel.getUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageview);

        holder.cvMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 *  Kodingan untuk tutorial Selanjutnya :p Read detail data
                 */
                mCtx.startActivity(EditHotel.getActIntent((Activity) mCtx).putExtra("data", hotelList.get(position)));


            }
        });
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }


    class HotelViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewNama, textViewAlamat, textViewHarga, textViewJumlah ;
        public ImageView imageview;
        public CardView cvMain;

        public HotelViewHolder(View itemView) {
            super(itemView);

            textViewNama = itemView.findViewById(R.id.tv_nama);
            textViewAlamat = itemView.findViewById(R.id.tv_alamat);
            textViewHarga = itemView.findViewById(R.id.tv_harga);
            textViewJumlah = itemView.findViewById(R.id.tv_jlh);
            imageview = itemView.findViewById(R.id.imageView);
            cvMain = (CardView) itemView.findViewById(R.id.cv_hotel);



        }
    }
}
