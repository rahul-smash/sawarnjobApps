package com.sarwarajobsapp.adapter;


import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sarwarajobsapp.R;
import com.sarwarajobsapp.activity.EditPersonInfoActivity;
import com.sarwarajobsapp.activity.PersonInfoActivity;
import com.sarwarajobsapp.candidateList.CandidateListActionaleActivityConvert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CandidateSamriddhiActionAdadpterConvert extends RecyclerView.Adapter<CandidateSamriddhiActionAdadpterConvert.MyViewHolder> {

     CandidateSamriddhiActionAdadpterConvert ira1;
    private JSONArray moviesList;
    Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtSrNo, txtName, txtDateOFBirth, txtState, txtEdit,txtView;
        LinearLayout MainLinearView;

        public MyViewHolder(View view) {
            super(view);
            MainLinearView=view.findViewById(R.id.MainLinearView);
            txtSrNo = view.findViewById(R.id.txtSrNo);
            txtName = view.findViewById(R.id.txtName);
            txtDateOFBirth = view.findViewById(R.id.txtDateOFBirth);
            txtState = view.findViewById(R.id.txtState);
            txtView = view.findViewById(R.id.txtView);
            txtEdit = view.findViewById(R.id.txtEdit);

        }
    }

    public CandidateSamriddhiActionAdadpterConvert(JSONArray moviesList, CandidateSamriddhiActionAdadpterConvert ira) {
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
                holder.txtEdit.setTag(jsonObject+"");
                holder.txtEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                   //     Toast.makeText(mContext, "Pending",Toast.LENGTH_SHORT).show();
                     /*   SharedPreferences sharedPreferences = mContext.getSharedPreferences("MySharedPref",MODE_PRIVATE);

                        SharedPreferences.Editor myEdit = sharedPreferences.edit();

                        myEdit.putString("FirstName", etFirstName.getText().toString());
                        myEdit.putString("LastName", etLastName.getText().toString());
                        myEdit.putString("email", etEmail.getText().toString());
                        myEdit.putString("phone", etPhone.getText().toString());
                        myEdit.putString("dob", etStartDate.getText().toString());
                        myEdit.putString("llokingJobType", etLookingJobType.getText().toString());
                        myEdit.putString("location", etLoction.getText().toString());
                        myEdit.commit();*/
                        try {
                            JSONObject dataObj=new JSONObject(v.getTag().toString());
                            Intent intent = new Intent(mContext, EditPersonInfoActivity.class);
                            intent.putExtra("first_name", dataObj.getString("first_name"));
                            intent.putExtra("last_name", dataObj.getString("last_name"));
                            intent.putExtra("email", dataObj.getString("email"));
                            intent.putExtra("phone", dataObj.getString("phone"));
                            intent.putExtra("dob", dataObj.getString("dob"));
                            intent.putExtra("address", dataObj.getString("address"));

                            intent.putExtra("looking_job_type", dataObj.getString("looking_job_type"));
                            intent.putExtra("description", dataObj.getString("description"));

                            mContext.startActivity(intent);
                          //  mContext.startActivity(new Intent(mContext, PersonInfoActivity.class));

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                });
                holder.txtView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "Pending",Toast.LENGTH_SHORT).show();

                    }
                });
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));

                jsonObject = moviesList.getJSONObject(position);
                Log.i("---@@jsonObject", "" + jsonObject.toString());

                holder.txtSrNo.setText(jsonObject.getString("first_name"));

                holder.txtName.setText(jsonObject.getString("last_name"));
                holder.txtDateOFBirth.setText(jsonObject.getString("dob"));

                holder.txtState.setText(jsonObject.getString("looking_job_type"));
                holder.txtEdit.setTag(jsonObject+"");

                holder.txtEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            JSONObject dataObj=new JSONObject(v.getTag().toString());
                            Intent intent = new Intent(mContext, EditPersonInfoActivity.class);
                            intent.putExtra("first_name", dataObj.getString("first_name"));
                            intent.putExtra("last_name", dataObj.getString("last_name"));
                            intent.putExtra("email", dataObj.getString("email"));
                            intent.putExtra("phone", dataObj.getString("phone"));
                            intent.putExtra("dob", dataObj.getString("dob"));
                            intent.putExtra("address", dataObj.getString("address"));

                            intent.putExtra("looking_job_type", dataObj.getString("looking_job_type"));
                            intent.putExtra("description", dataObj.getString("description"));

                            mContext.startActivity(intent);
                            //  mContext.startActivity(new Intent(mContext, PersonInfoActivity.class));

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                    }
                });
                holder.txtView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "Pending",Toast.LENGTH_SHORT).show();

                    }
                });
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