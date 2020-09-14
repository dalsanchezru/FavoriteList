package com.example.favoritelist;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.favoritelist.FavDB;
import com.example.favoritelist.MascotaItem;

import java.util.ArrayList;

public class MascotaAdapter extends RecyclerView.Adapter<MascotaAdapter.ViewHolder> {

    private ArrayList<MascotaItem> mascotaItems;
    private Context context;
    private FavDB favDB;

    public MascotaAdapter(ArrayList<MascotaItem> mascotaItems, Context context) {
        this.mascotaItems = mascotaItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        favDB = new FavDB(context);
        //create table on first
        SharedPreferences prefs = context.getSharedPreferences("prefs", context.MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart) {
            createTableOnFirstStart();
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MascotaAdapter.ViewHolder holder, int position) {
        final MascotaItem mascotaItem = mascotaItems.get(position);

        readCursorData(mascotaItem, holder);
        holder.imageView.setImageResource(mascotaItem.getImageResourse());
        holder.titleTextView.setText(mascotaItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return mascotaItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView titleTextView;
        Button favBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            favBtn = itemView.findViewById(R.id.favBtn);

            //add to fav btn
            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    MascotaItem mascotaItem = mascotaItems.get(position);

                    if (mascotaItem.getFavStatus().equals("0")) {
                        mascotaItem.setFavStatus("1");
                        favDB.insertIntoTheDatabase(mascotaItem.getTitle(), mascotaItem.getImageResourse(),
                                mascotaItem.getKey_id(), mascotaItem.getFavStatus());
                        favBtn.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                    } else {
                        mascotaItem.setFavStatus("0");
                        favDB.remove_fav(mascotaItem.getKey_id());
                        favBtn.setBackgroundResource(R.drawable.ic_baseline_shadow_24);
                    }
                }
            });
        }
    }

    private void createTableOnFirstStart() {
        favDB.insertEmpty();

        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    private void readCursorData(MascotaItem mascotaItem, ViewHolder viewHolder) {
        Cursor cursor = favDB.read_all_data(mascotaItem.getKey_id());
        SQLiteDatabase db = favDB.getReadableDatabase();
        try {
            while (cursor.moveToNext()) {
                String item_fav_status = cursor.getString(cursor.getColumnIndex(favDB.FAVORITE_STATUS));
                mascotaItem.setFavStatus(item_fav_status);

                //check fav status
                if (item_fav_status != null && item_fav_status.equals("1")) {
                    viewHolder.favBtn.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                } else if (item_fav_status != null && item_fav_status.equals("0")) {
                    viewHolder.favBtn.setBackgroundResource(R.drawable.ic_baseline_shadow_24);
                }
            }
        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
        }
    }
}