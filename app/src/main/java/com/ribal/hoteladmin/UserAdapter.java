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

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{
    private Context mCtx;
    private List<User> UserList;


    public UserAdapter(Context mCtx, List<User> UserList) {
        this.mCtx = mCtx;
        this.UserList = UserList;
    }

    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserAdapter.UserViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.layout_user, parent, false)
        );
    }
    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, final int position) {
        User user= UserList.get(position);

        holder.textViewNama.setText(user.getEmail());


        holder.textViewNama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 *  Kodingan untuk tutorial Selanjutnya :p Read detail data
                 */
                mCtx.startActivity(DaftarTiket.getActIntent((Activity) mCtx).putExtra("data", UserList.get(position)));


            }
        });
    }

    @Override
    public int getItemCount() {
        return UserList.size();
    }


    class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewNama ;


        public UserViewHolder(View itemView) {
            super(itemView);

            textViewNama = itemView.findViewById(R.id.user_nama);

        }
    }
}
