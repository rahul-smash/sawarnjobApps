package com.sarwarajobsapp.adapter;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.sarwarajobsapp.R;
import com.sarwarajobsapp.candidateList.CandidateListActionaleActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CandidateSamriddhiActionAdadpter extends RecyclerView.Adapter<CandidateSamriddhiActionAdadpter.MyViewHolder> {

    private CandidateListActionaleActivity ira1;
    private JSONArray moviesList;
    Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtSrNo, txtName, txtDateOFBirth, txtState, txtCity;

        public MyViewHolder(View view) {
            super(view);

            txtSrNo = view.findViewById(R.id.txtSrNo);
            txtName = view.findViewById(R.id.txtName);
            txtDateOFBirth = view.findViewById(R.id.txtDateOFBirth);
            txtState = view.findViewById(R.id.txtState);

        }
    }

    public CandidateSamriddhiActionAdadpter(JSONArray moviesList, CandidateListActionaleActivity ira) {
        this.moviesList = moviesList;
        ira1 = ira;


    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.samriddhi_listitem_row, parent, false);
        mContext = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Log.i("---@@candidateSamriddhiActionAdadpter", "" + position);
        JSONObject jsonObject = null;
        try {
            if (position % 2 == 1) {
                holder.itemView.setBackgroundColor(Color.parseColor("#e6e6e6"));
                jsonObject = moviesList.getJSONObject(position);
                Log.i("---@@jsonObject", "" + jsonObject.toString());
holder.txtSrNo.setText(jsonObject.getString("first_name"));

                holder.txtName.setText(jsonObject.getString("last_name"));
                holder.txtDateOFBirth.setText(jsonObject.getString("dob"));

                holder.txtState.setText(jsonObject.getString("looking_job_type"));

            } else {
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));

                jsonObject = moviesList.getJSONObject(position);
                Log.i("---@@jsonObject", "" + jsonObject.toString());

                holder.txtSrNo.setText(jsonObject.getString("first_name"));

                holder.txtName.setText(jsonObject.getString("last_name"));
                holder.txtDateOFBirth.setText(jsonObject.getString("dob"));

                holder.txtState.setText(jsonObject.getString("looking_job_type"));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        System.out.println("@@Leng===" + moviesList.length());
        return moviesList.length();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


}