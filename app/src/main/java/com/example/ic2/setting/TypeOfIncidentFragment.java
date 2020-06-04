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
import com.example.ic2.async.AsyncTaskCallback;
import com.example.ic2.async.location.AddLocationAsync;
import com.example.ic2.async.location.DeleteLocationAsync;
import com.example.ic2.async.typeOfIncident.AddNewTypeOfIncidentAsync;
import com.example.ic2.async.typeOfIncident.DeleteTypeOfIncidentAsync;
import com.example.ic2.async.typeOfIncident.GetTypeOfIncidentAsync;
import com.example.ic2.async.typeOfIncident.UpdateTypeOfIncidentAsync;
import com.example.ic2.model.DynamicInfo;
import com.example.ic2.model.Location;
import com.example.ic2.model.TypeOfIncident;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TypeOfIncidentFragment extends Fragment implements DynamicInfoRecyclerViewAdapter.DynamicItemInterface {

    View view;
    RecyclerView rvTypeOfIncident;
//    FloatingActionButton fab;
    ImageView imgAddType;
    List<DynamicInfo> typeOFincidentList;

    public TypeOfIncidentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_type_of_incident, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvTypeOfIncident=view.findViewById(R.id.rvTypeOfIncident);
        rvTypeOfIncident.setLayoutManager(new LinearLayoutManager(getActivity()));
//        fab=view.findViewById(R.id.fab);
        imgAddType=view.findViewById(R.id.imgAddType);
        imgAddType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewTypeOfIncident();
            }
        });

        getTypeOfIncident();

    }

    private void getTypeOfIncident() {

        new GetTypeOfIncidentAsync(getActivity(), new AsyncTaskCallback<List<TypeOfIncident>>() {
            @Override
            public void handleResponse(List<TypeOfIncident> response) {
                typeOFincidentList=new ArrayList<DynamicInfo>(response);
                rvTypeOfIncident.setAdapter(new DynamicInfoRecyclerViewAdapter(typeOFincidentList,getActivity(),
                        (DynamicInfoRecyclerViewAdapter.DynamicItemInterface)TypeOfIncidentFragment.this));
            }

            @Override
            public void handleFault(Exception e) {
                typeOFincidentList=new ArrayList<>();
                rvTypeOfIncident.setAdapter(new DynamicInfoRecyclerViewAdapter(typeOFincidentList,getActivity(),
                        (DynamicInfoRecyclerViewAdapter.DynamicItemInterface)TypeOfIncidentFragment.this));
                Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).execute();

    }

    private void addNewTypeOfIncident() {

        View view=LayoutInflater.from(getActivity()).inflate(R.layout.dynamic_info_dailog,null);
        final EditText etLocationName;
        final CheckBox cbSelected;
        etLocationName=view.findViewById(R.id.etLocationName);
        etLocationName.setHint("Type of incident");
        cbSelected=view.findViewById(R.id.cbSelected);
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Add new type of incident");
        builder.setView(view);
        builder.setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(etLocationName.getText().toString().trim().isEmpty()){
                    Toast.makeText(getActivity(), "PLease enter type of incident name", Toast.LENGTH_SHORT).show();
                }else{
                    String name=etLocationName.getText().toString().trim();
                    String selected=cbSelected.isChecked()?"selected":"unselected";
                    new AddNewTypeOfIncidentAsync(new TypeOfIncident(name, selected), getActivity(), new AsyncTaskCallback<TypeOfIncident>() {
                        @Override
                        public void handleResponse(TypeOfIncident response) {
                            typeOFincidentList.add(response);
                            rvTypeOfIncident.getAdapter().notifyDataSetChanged();
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
    public void onDeleteLocation(final DynamicInfo info) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete : "+info.getName()+" ?");
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                new DeleteTypeOfIncidentAsync((TypeOfIncident) info, getActivity(), new AsyncTaskCallback<TypeOfIncident>() {
                    @Override
                    public void handleResponse(TypeOfIncident response) {
                        typeOFincidentList.remove(response);
                        rvTypeOfIncident.getAdapter().notifyDataSetChanged();
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
    public void onCheckedLocation(DynamicInfo info) {
        new UpdateTypeOfIncidentAsync((TypeOfIncident) info,getActivity()).execute();
    }
}
