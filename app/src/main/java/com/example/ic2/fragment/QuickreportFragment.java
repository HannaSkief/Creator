package com.example.ic2.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ic2.common.Common;
import com.example.ic2.quickReportActivity.QuickReportActivity;
import com.example.ic2.R;
import com.google.android.material.card.MaterialCardView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuickreportFragment extends Fragment implements View.OnClickListener {

    View view;
    MaterialCardView cardNonConformance,cardAccident,cardObservation,cardNearMiss,cardImprovement,cardPrevention;

    public QuickreportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_quickreport, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        cardNonConformance=view.findViewById(R.id.cardNonConformance);
        cardAccident=view.findViewById(R.id.cardAccident);
        cardObservation=view.findViewById(R.id.cardObservation);
        cardNearMiss=view.findViewById(R.id.cardNearMiss);
        cardImprovement=view.findViewById(R.id.cardImprovement);
        cardPrevention=view.findViewById(R.id.cardPrevention);

        cardNonConformance.setOnClickListener(this);
        cardAccident.setOnClickListener(this);
        cardObservation.setOnClickListener(this);
        cardNearMiss.setOnClickListener(this);
        cardImprovement.setOnClickListener(this);
        cardPrevention.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Common.selected_report=null;
        Intent intent=new Intent(getActivity(),QuickReportActivity.class);
        switch (view.getId()){
            case R.id.cardNonConformance:intent.putExtra("reportType",getString(R.string.non_conformance));break;
            case R.id.cardAccident:intent.putExtra("reportType",getString(R.string.accident));break;
            case R.id.cardObservation:intent.putExtra("reportType",getString(R.string.observation));break;
            case R.id.cardNearMiss:intent.putExtra("reportType",getString(R.string.near_miss));break;
            case R.id.cardImprovement:intent.putExtra("reportType",getString(R.string.improvement));break;
            case R.id.cardPrevention:intent.putExtra("reportType",getString(R.string.prevention));break;
        }
        startActivity(intent);
    }
}
