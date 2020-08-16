package com.example.kyles.a2020skeletonapp;

import android.view.View;

public interface generalMethods {

    public void autonButtonCheck(View v);
    public void autonCheckboxCheck (View v);
    public void autonTextViewUpdate(String textView);

    public void teleopButtonCheck(View v);
    public void teleopCheckboxCheck (View v);
    public void teleopImageGroupUpdate (View v);
    public void teleopTextViewUpdate(String textView);

    public void defenseBtnCheck(View v);

    public void endgameCheckboxCheck(View v);
    public void endgameChronometerControl(View v);

    public void getData();
    public void sendData();

    public void backToTeamMatch(View v);
}
