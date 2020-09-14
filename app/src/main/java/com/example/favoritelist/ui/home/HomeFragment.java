    package com.example.favoritelist.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.favoritelist.MascotaAdapter;
import com.example.favoritelist.MascotaItem;
import com.example.favoritelist.R;

import java.util.ArrayList;

    public class HomeFragment extends Fragment {

    private ArrayList<MascotaItem> mascotaItems = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new MascotaAdapter(mascotaItems, getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mascotaItems.add(new MascotaItem(R.drawable.a,"Rata","0","0"));
        mascotaItems.add(new MascotaItem(R.drawable.b,"Perro","1","0"));
        mascotaItems.add(new MascotaItem(R.drawable.c,"Pajarito","2","0"));
        mascotaItems.add(new MascotaItem(R.drawable.d,"Oso","3","0"));
        mascotaItems.add(new MascotaItem(R.drawable.e,"Pez","4","0"));
        mascotaItems.add(new MascotaItem(R.drawable.f,"Loro","5","0"));
        mascotaItems.add(new MascotaItem(R.drawable.g,"Gato","6","0"));
        mascotaItems.add(new MascotaItem(R.drawable.i,"Cerdo","7","0"));

        return root;
    }
}