package ca.lakeeffect.scoutingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by Ajay on 9/25/2016.
 */
public class RobotPage extends Fragment{

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState){

        View view = inflator.inflate(R.layout.robotpage, container, false);

        view.setTag("page1");

//        ((TextView) view.findViewById(R.id.autoPeg)).setTextSize(15);
        return view;

    }
}
