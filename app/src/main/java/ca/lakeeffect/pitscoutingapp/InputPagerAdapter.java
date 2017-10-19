package ca.lakeeffect.pitscoutingapp;

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
    public NotesPage notesPage;
    public PhotoPage photoPage;
    public TeleStrategy teleStrategyPage;

    public InputPagerAdapter(FragmentManager fm) {
        super(fm);
        robotPage = new RobotPage();
        notesPage = new NotesPage();
        photoPage = new PhotoPage();
        teleStrategyPage = new TeleStrategy();
    }
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                notesPage = new NotesPage();
                return notesPage;
            case 1:
                robotPage = new RobotPage();
                return robotPage;
            case 2:
                teleStrategyPage = new TeleStrategy();
                return teleStrategyPage;
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
                return "Notes";
            case 1:
                return "Robot";
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
