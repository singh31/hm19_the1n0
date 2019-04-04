package com.example.msq.LaVie;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class BloodReqAdapter extends RecyclerView.Adapter<BloodReqAdapter.RequestViewHolder> {

    private ArrayList<homePagePost> mlist;

    public BloodReqAdapter(ArrayList<homePagePost> mlist) {
        this.mlist = mlist;
    }

    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rv_blood_req_list, null);

        return new RequestViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, final int position) {
        homePagePost homePgPost = mlist.get(position);

        holder.bloodType.setText(homePgPost.getbType());
        holder.address.setText(homePgPost.getLocation());
        holder.qty.setText(String.valueOf(homePgPost.getQuantity()));
        holder.phNo.setText(String.valueOf(homePgPost.getContact()));

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRequest(position);
            }
        });
    }

    public void deleteRequest(final int position){

        final homePagePost post = mlist.get(position);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("reqinfo");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot Snapshot: dataSnapshot.getChildren()) {
                    homePagePost tempPost = Snapshot.getValue(homePagePost.class);
                    //System.out.println("Num : " + tempPost.getContact());
                    if(post.getContact() == tempPost.getContact()){
                        Snapshot.getRef().removeValue();
                        mlist.remove(position);
                        ArrayList<homePagePost> temp = (ArrayList<homePagePost>) mlist.clone();
                        mlist.clear();
                        mlist = temp;
                        notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    class RequestViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView bloodType, address, qty, phNo;
        Button delete;

        public RequestViewHolder(View itemView) {

            super(itemView);
            bloodType = itemView.findViewById(R.id.cv_blood_grp);
            address = itemView.findViewById(R.id.cv_address);
            qty = itemView.findViewById(R.id.cv_qty);
            phNo = itemView.findViewById(R.id.cv_phNo);
            cardView = itemView.findViewById(R.id.cv);
            delete = itemView.findViewById(R.id.deleteButton);
        }
    }



}
