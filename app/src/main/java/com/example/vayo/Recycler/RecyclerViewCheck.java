package com.example.vayo.Recycler;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vayo.Model.Booking;
import com.example.vayo.Database.DatabaseHelper;
import com.example.vayo.R;

import java.util.List;

public class RecyclerViewCheck extends RecyclerView.Adapter<RecyclerViewCheck.MyViewHolderCheck>
{
    private Context mContext ;
    private List<Booking> mData ;
    private DatabaseHelper myDb;



    public RecyclerViewCheck(Context mContext, List<Booking> mData) {
        this.mContext = mContext;
        this.mData = mData;
        myDb = new DatabaseHelper(mContext);

    }

    @Override
    public MyViewHolderCheck onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.check_booking_item,parent,false);
        return new MyViewHolderCheck(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolderCheck holder, final int position) {

        holder.booking_id.setText("#Order number: " + mData.get(position).getID());
        holder.movie_name.setText("Movie Name: " + mData.get(position).getMOVIE_NAME());
        holder.movie_location.setText("Cinema Name: " + mData.get(position).getCINEMA_NAME());
        holder.movie_showing_date.setText("Showing Date: " + mData.get(position).getDATE());
        holder.movie_status.setText("Confirmation Status: " + mData.get(position).getSTATUS());
        holder.seats.setText("Booked Seats: " + mData.get(position).getSEATS());
        holder.userName.setText("UserName: " + mData.get(position).getUSERNAME());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new AlertDialog.Builder(mContext)
                        .setTitle("Confirmation entry")
                        .setMessage("Are you sure you want to confirm this booking?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                try {
                                    myDb.updateBookings(mData.get(position).getID(),"yes");
                                    holder.movie_status.setText("Confirmation Status: yes");
                                    Toast.makeText(mContext,"This booking was successfully confirmed",Toast.LENGTH_SHORT).show();

                                }
                                catch (Exception e)
                                {
                                    Toast.makeText(mContext,e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        })

                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();



            }
        });





    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolderCheck extends RecyclerView.ViewHolder {

        TextView booking_id, movie_name,movie_location, movie_showing_date,movie_status, seats, userName;
        CardView cardView ;

        public MyViewHolderCheck(View itemView) {
            super(itemView);

            booking_id =  itemView.findViewById(R.id.check_booking_id) ;
            movie_name = itemView.findViewById(R.id.check_booking_name);
            movie_location =  itemView.findViewById(R.id.check_booking_location);
            movie_showing_date =  itemView.findViewById(R.id.check_booking_date);
            movie_status =  itemView.findViewById(R.id.check_booking_confirmation);
            seats = itemView.findViewById(R.id.check_booking_seats);
            userName =  itemView.findViewById(R.id.check_booking_user);
            cardView = itemView.findViewById(R.id.card_check_id);


        }
    }

}
