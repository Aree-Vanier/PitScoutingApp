package ca.lakeeffect.scoutingapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Ajay on 9/25/2016.
 *
 * Pager Adapter for the input pane
 */
public class InputPagerAdapter extends FragmentStatePagerAdapter {

    final int PAGENUM = 4;

    public RobotPage robotPage;
    public AutoStrategy autoStrategy;
    public PhotoPage photoPage;
    public TeleStrategy teleStrategy;

    public InputPagerAdapter(FragmentManager fm) {
        super(fm);
        robotPage = new RobotPage();
        autoStrategy = new AutoStrategy();
        photoPage = new PhotoPage();
        teleStrategy = new TeleStrategy();
    }
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                robotPage = new RobotPage();
                return robotPage;
            case 1:
                autoStrategy = new AutoStrategy();
                return autoStrategy;
            case 2:
                teleStrategy = new TeleStrategy();
                return teleStrategy;
            case 3:
                photoPage = new PhotoPage();
                return photoPage;
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGENUM;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return "Robot ";
            case 1:
                return "Auto Strategy";
            case 2:
                return "Teleop Strategy";
            case 3:
                return "Photos";
        }
        return "";
    }

    @Override
    public int getItemPosition(Object object){
        //Ignore sketchyness
        return POSITION_NONE;
    }
}
