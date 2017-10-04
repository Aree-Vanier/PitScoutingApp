package ca.lakeeffect.scoutingapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class PhotoPage extends Fragment implements View.OnClickListener {

    ListView list;
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<Bitmap> images = new ArrayList<Bitmap>();
    Button addPhoto;
    int robotNum;

    String lastPhoto = "";
    String lastPhotoSD = "";

    public PhotoPage() {
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

        reloadList();


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
//        saveLastPhoto();
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

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        System.out.println("Result");
//        if (requestCode == 1 && resultCode == RESULT_OK) {
//            System.out.println("Got Image");
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
////            savePhoto(robotNum, imageBitmap);
//            images.add(Bitmap.createBitmap(imageBitmap));
//            names.add("image");
//            reloadList();
//        }
//    }

    private void reloadList() {
        System.out.println("Loading List");
        CustomList photoList = new CustomList(this.getActivity(), names.toArray(new String[names.size()]), images.toArray(new Bitmap[images.size()]), R.layout.photo_list);
        list.setAdapter(photoList);
    }

    private void savePhoto(int robot){ //, Bitmap image) {
        File direct = new File(Environment.getExternalStorageDirectory() + "/#PitScoutingData/" + robot + "/images/");

        if (!direct.exists()) {
            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + "/#PitScoutingData/" + robot + "/images/");
            wallpaperDirectory.mkdirs();
        }

//        File file = new File(new File(Environment.getExternalStorageDirectory() + "/#PitScoutingData/" + robot + "/images/"), images.size() + ".png");
//        if (file.exists()) {
//            file.delete();
//        }
//        try {
//            FileOutputStream out = new FileOutputStream(file);
//            image.compress(Bitmap.CompressFormat.PNG, 100, out);
//            out.flush();
//            out.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

//    /**
//     * Saves the path of the newest images
//     */
//    private void saveLastPhoto() {
//        lastPhotoSD = lastOnSD();
//        lastPhoto = lastOnMem();
//    }
//
//
//    /**
//     *
//     * @param temp The image returned by the intent
//     * @return The photo form the SD card or Memory if changed, otherwise the photo from the intent
//     */
//    private Bitmap getPhoto(Bitmap temp) {
//        if(lastPhoto != lastOnMem()){
//            //get from mem
//            return temp;
//        }
//        else if(lastPhotoSD != lastOnSD()) {
//            //get from SD
//            return temp;
//        }
//        // Return same image
//        else return temp;
//    }
//
//    /**
//     * Gets the path of the newest image on the SD card
//     * @return Image path
//     */
//    private String lastOnSD() {
//        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
//            File dir = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera");
//            try {
//                return dir.listFiles()[0].getCanonicalPath();
//            } catch (IOException e) {
//                e.printStackTrace();
//                return "";
//            }
//        }
//        return "";
//    }
//    /**
//     * Gets the path of the newest image on the system memory card
//     * @return Image path
//     */
//    private String lastOnMem() {
//        File dir = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera");
//        try {
//            return dir.listFiles()[0].getCanonicalPath();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "";
//        }
//    }
}
