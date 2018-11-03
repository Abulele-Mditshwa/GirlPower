package com.example.admin.girlpower;
/*
Tells the user what to do in each emergency.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Options_in_Emergency extends Fragment {

    private ListView listView;
    //Danger examples
    private String dangerExamples[] = {"Active Shooter","       Hazardous Materials","Fire","     Suspicious Person","     Suspicious Package","Utility Failure","   Criminal Activities"};

    // images that depicts the Long string.
    private int[] images = {R.drawable.gun,R.drawable.hazard,R.drawable.ic_launcher_fire_image,R.drawable.ic_launcher_suspicious_person,R.drawable.suspicious_package,R.drawable.power,R.drawable.criminal};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // links the XML to this design.
        View view  = inflater.inflate(R.layout.option_in_emergency, container,false);
        listView = (ListView)view.findViewById(R.id.listView2);
        listView.setAdapter(new ListAdapterView(getActivity(),R.layout.support, dangerExamples));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                //Active Shooter
                if(position == 0){
                    Intent intent = new Intent(getActivity(),active_shooter.class);
                    startActivity(intent); // opens the active shooter activity.
                }//end if 0


                //Hazardous Materials
                else if(position == 1){
                    Intent intent2 = new Intent(getActivity(),hazardous_materials.class);
                    startActivity(intent2);

                }//end else 1


                //fire
                else if(position == 2){

                    Intent intent4 = new Intent(getActivity(),fire.class);
                    startActivity(intent4);

                }//end else 2


                //Suspicious person
                else if(position == 3){
                    Intent intent5 = new Intent(getActivity(),suspicious_person.class);
                    startActivity(intent5);
                }//end else 3

                //suspicious package
                else if(position == 4){
                    Intent intent6 = new Intent(getActivity(),suspicious_package.class);
                    startActivity(intent6);
                }//end else 4


                //power failure
                else if(position == 5){
                    Intent intent6 = new Intent(getActivity(),power_failure.class);
                    startActivity(intent6);
                }//end else 5

                //criminal activities
                else if(position == 6){
                    Intent intent7 = new Intent(getActivity(),criminal_activities.class);
                    startActivity(intent7);
                }//end else


            }//end onItemClick

        });//end anonymous inner class




        return view;
    }//end onCreateView.


    class ListAdapterView extends ArrayAdapter{

        public ListAdapterView(Context context,int resource, String[] objects){
            super(context, resource, objects);

        }//end constructor.

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View view=((Activity)getContext()).getLayoutInflater().inflate(R.layout.support,null);
            TextView txt1 = (TextView) view.findViewById(R.id.textViewList);
            txt1.setText(dangerExamples[position]);
            ImageView img = (ImageView) view.findViewById(R.id.image_for_list);

            try{

                img.setBackgroundResource(images[position]);
            }catch (Resources.NotFoundException e){
                e.printStackTrace();
            }

            return view;
        }
    }//end ListAdapterView


}//end Options_in_Emergency fragment.
