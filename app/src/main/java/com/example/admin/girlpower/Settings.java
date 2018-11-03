package com.example.admin.girlpower;

/*
This class holds our settings
 */


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends Fragment {




    String[] labels = {"Settings","About", "Log out"};

    private ListView listview; // will contain the list

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.settings,null); //set the view to the Fragment activity.

        View view  = inflater.inflate(R.layout.settings, container,false);


        listview = (ListView)view.findViewById(R.id.listView); //XML reference in this Fragment.

        // Tellling the list to adapt this format
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),android.R.layout.simple_list_item_1,labels
        );

        listview.setAdapter(arrayAdapter); // sets the listview


        // this basically determines each time the user clicks in each item of the listView.
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0){
                    // if this is on Settings

                    Intent intent = new Intent(getActivity(),User_inf.class); // go to this class
                    startActivity(intent);

                }//end if

                else if (position == 1){
                  //  Toast.makeText(getActivity(),"You clicked About", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(),About.class); // go to this class
                    startActivity(intent);
                }

                else{
                    //Toast.makeText(getActivity(),"You clicked Log out", Toast.LENGTH_SHORT).show();
                        showPopup(); // checks if the user wants to log out.
                }//end


            }//end onItemClick
        });//end anonymous inner class.



        return view;
    }//end onCreateView.


    // first step helper function
    private void showPopup() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setMessage("Are you sure?")
                .setPositiveButton("Logout", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialog, int which) {
                        logout(); // Last step. Logout function
                    }
                }).setNegativeButton("Cancel", null);

        AlertDialog alert1 = alert.create();
        alert1.show();
    }

    private void logout() {
        startActivity(new Intent(getActivity(),MainActivity.class));

        getActivity().finish(); // closes
        System.exit(0);
    }




}//end Settings fragment.
