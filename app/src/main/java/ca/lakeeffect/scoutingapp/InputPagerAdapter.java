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

    final int PAGENUM = 3;

    public RobotPage robotPage;
    public StrategyPage strategyPage;
    public PhotoPage photoPage;


    public InputPagerAdapter(FragmentManager fm) {
        super(fm);
        robotPage = new RobotPage();
        strategyPage = new StrategyPage();
        photoPage = new PhotoPage();
    }
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                robotPage = new RobotPage();
                return robotPage;
            case 1:
                strategyPage = new StrategyPage();
                return strategyPage;
            case 2:
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
                return "Robot Info ";
            case 1:
                return "Strategy Info";
            case 2:
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
