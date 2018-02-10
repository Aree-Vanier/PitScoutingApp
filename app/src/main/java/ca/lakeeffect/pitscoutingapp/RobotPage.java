package ca.lakeeffect.pitscoutingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class RobotPage extends Fragment{

    Spinner mechanism;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState){

        View view = inflator.inflate(R.layout.robot_page, container, false);

        view.setTag("page2");

        mechanism = (Spinner) view.findViewById(R.id.mechanismType);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.mechanisms, android.R.layout.simple_spinner_item);
        mechanism.setAdapter(adapter);

//        ((TextView) view.findViewById(R.id.autoPeg)).setTextSize(15);
        return view;

    }
}
