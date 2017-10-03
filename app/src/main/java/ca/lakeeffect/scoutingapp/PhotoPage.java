package ca.lakeeffect.scoutingapp;

import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class PhotoPage extends Fragment implements View.OnClickListener{

    ListView list;
    String[] names = {"Test 1", "Test 2"};
    Integer[] images = {R.mipmap.ic_launcher,R.mipmap.ic_launcher};



    public PhotoPage() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState){
        View view = inflator.inflate(R.layout.photopage, container, false);

        list = ((ListView) ((PercentRelativeLayout) view.findViewById(R.id.photoPage)).findViewById(R.id.photos));
        view.setTag("page3");

        CustomList photoList = new CustomList(this.getActivity(), names, images, R.layout.photo_list);
        list.setAdapter(photoList);

        return view;
    }

    public void onClick(View v){
//        Toast.makeText(getActivity(),
//                getActivity().toString(), Toast.LENGTH_LONG).show();

    }
}