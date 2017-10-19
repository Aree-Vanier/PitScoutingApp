package ca.lakeeffect.pitscoutingapp;

import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Ajay on 9/25/2016.
 */
public class NotesPage extends Fragment implements View.OnClickListener {

    Button save;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState) {

        View view = inflator.inflate(R.layout.notes_page, container, false);

        view.setTag("page1");
        save = (Button) ((PercentRelativeLayout) view.findViewById(R.id.notesLayout)).findViewById(R.id.save);
        save.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == save) {
            final boolean status = ((MainActivity) getActivity()).saveData();

        }
    }


}
