package com.example.ic2.setting;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ic2.R;
import com.example.ic2.adapter.DynamicInfoRecyclerViewAdapter;
import com.example.ic2.async.location.AddLocationAsync;
import com.example.ic2.async.AsyncTaskCallback;
import com.example.ic2.async.location.DeleteLocationAsync;
import com.example.ic2.async.location.GetLocationAsync;
import com.example.ic2.async.location.UpdateLocationAsync;
import com.example.ic2.model.DynamicInfo;
import com.example.ic2.model.Location;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationsFragment extends Fragment implements DynamicInfoRecyclerViewAdapter.DynamicItemInterface {

    View view;
//    FloatingActionButton fab;
    RecyclerView rvLocations;
    List<DynamicInfo> locationList;
    ImageView imgAddLocation;
    public LocationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_locations, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        fab=view.findViewById(R.id.fab);
        imgAddLocation=view.findViewById(R.id.imgAddLocation);
        rvLocations=view.findViewById(R.id.rvLocations);
        rvLocations.setLayoutManager(new LinearLayoutManager(getActivity()));

        locationList=new ArrayList<>();
        rvLocations.setAdapter(new DynamicInfoRecyclerViewAdapter(locationList,getActivity(),(DynamicInfoRecyclerViewAdapter.DynamicItemInterface)LocationsFragment.this));
        imgAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewLocation();
            }
        });

        getAllLocation();
    }

    private void getAllLocation() {
        new GetLocationAsync(getActivity(), new AsyncTaskCallback<List<Location>>() {
            @Override
            public void handleResponse(List<Location> response) {
                locationList= new ArrayList<DynamicInfo>(response);
                rvLocations.setAdapter(new DynamicInfoRecyclerViewAdapter(locationList,getActivity(),(DynamicInfoRecyclerViewAdapter.DynamicItemInterface)LocationsFragment.this));

            }

            @Override
            public void handleFault(Exception e) {
                Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }

    private void addNewLocation() {

        View view=LayoutInflater.from(getActivity()).inflate(R.layout.dynamic_info_dailog,null);
        final EditText etLocationName;
        final CheckBox cbSelected;
        etLocationName=view.findViewById(R.id.etLocationName);
        cbSelected=view.findViewById(R.id.cbSelected);
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Add new location");
        builder.setView(view);
        builder.setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(etLocationName.getText().toString().trim().isEmpty()){
                    Toast.makeText(getActivity(), "PLease enter location name", Toast.LENGTH_SHORT).show();
                }else{
                    String locationName=etLocationName.getText().toString().trim();
                    String selected=cbSelected.isChecked()?"selected":"unselected";
                    new AddLocationAsync(new Location(locationName, selected), getActivity(), new AsyncTaskCallback<Location>() {
                        @Override
                        public void handleResponse(Location response) {
                            locationList.add(response);
                            rvLocations.getAdapter().notifyDataSetChanged();
                        }

                        @Override
                        public void handleFault(Exception e) {
                            Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).execute();
                }

            }
        });
        builder.setNegativeButton(getString(R.string.cancel),null);
        AlertDialog dialog=builder.create();
        dialog.show();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        positiveButton.setBackgroundColor(Color.TRANSPARENT);
        negativeButton.setBackgroundColor(Color.TRANSPARENT);

    }

    @Override
    public void onDeleteLocation(final DynamicInfo location) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete location : "+location.getName()+" ?");
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                new DeleteLocationAsync((Location) location, getActivity(), new AsyncTaskCallback<Location>() {
                    @Override
                    public void handleResponse(Location response) {
                        locationList.remove(response);
                        rvLocations.getAdapter().notifyDataSetChanged();
                    }

                    @Override
                    public void handleFault(Exception e) {

                    }
                }).execute();
            }
        });
        builder.setNegativeButton(getString(R.string.cancel),null);

        AlertDialog dialog=builder.create();
        dialog.show();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        positiveButton.setBackgroundColor(Color.TRANSPARENT);
        negativeButton.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onCheckedLocation(DynamicInfo location) {
         new UpdateLocationAsync((Location) location,getActivity()).execute();
    }

}
