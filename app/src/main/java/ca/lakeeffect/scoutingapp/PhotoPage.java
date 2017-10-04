package ca.lakeeffect.scoutingapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class PhotoPage extends Fragment implements View.OnClickListener {

    ListView list;
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<Bitmap> images = new ArrayList<Bitmap>();
    //    Bitmap[] images;
    Button addPhoto;
    int robotNum;
    BitmapFactory.Options opt;

    String lastPhoto = "";
    String lastPhotoSD = "";

    public PhotoPage() {
        opt = new BitmapFactory.Options();
//        names.add("Test 1");
//        names.add("Test 2");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState) {
//        images.add(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
//        images.add(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        View view = inflator.inflate(R.layout.photopage, container, false);

        list = ((ListView) ((PercentRelativeLayout) view.findViewById(R.id.photoPage)).findViewById(R.id.photos));
        addPhoto = ((Button) ((PercentRelativeLayout) view.findViewById(R.id.photoPage)).findViewById(R.id.addPhoto));
        addPhoto.setOnClickListener(this);

        view.setTag("page3");

        loadList();


        return view;
    }

    public void onClick(View v) {
        if (v == addPhoto) {
            dispatchTakePictureIntent();
        }
//        Toast.makeText(getActivity(),
//                getActivity().toString(), Toast.LENGTH_LONG).show();

    }

    private void dispatchTakePictureIntent() {
        File dir = new File(Environment.getExternalStorageDirectory() + "/#PitScoutingData/" + robotNum + "/images/");

        if (!dir.exists()) {
            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + "/#PitScoutingData/" + robotNum + "/images/");
            wallpaperDirectory.mkdirs();
        }
        Uri path = Uri.fromFile(new File(new File(Environment.getExternalStorageDirectory() + "/#PitScoutingData/" + robotNum + "/images/"), images.size() + ".png"));
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, path);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1);
        } else {
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity().getApplicationContext(), "Camera Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("Result");
        if (requestCode == 1 && resultCode == RESULT_OK) {
            System.out.println("Got Image");
            Bitmap image = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/#PitScoutingData/" + robotNum + "/images/"+images.size()+".png");
            updateList(image);
        }
    }

    //To add new image
    private void updateList(Bitmap image) {
        images.add(image);
        CustomList photoList = new CustomList(this.getActivity(), names.toArray(new String[names.size()]), images.toArray(new Bitmap[images.size()]), R.layout.photo_list);
        list.setAdapter(photoList);
    }

    //For initial load
    private void loadList() {
        File dir = new File(Environment.getExternalStorageDirectory() + "/#PitScoutingData/" + robotNum + "/images/");
        File[] files = dir.listFiles();
        System.out.println(files.length);
        images.clear();
        for (int i = 0; i < files.length; i++) {
            String name = files[i].getName();
            System.out.println(name);
            Bitmap image = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/#PitScoutingData/" + robotNum + "/images/" + name, opt);
            images.add(image);
        }
        System.out.println("Loading List");
        CustomList photoList = new CustomList(this.getActivity(), names.toArray(new String[names.size()]), images.toArray(new Bitmap[images.size()]), R.layout.photo_list);
        list.setAdapter(photoList);
    }
}

