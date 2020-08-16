package com.example.kyles.a2020skeletonapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;

public class summaryScreen extends AppCompatActivity {
    String name;
    //int startPowerCells;
    boolean moveInit;
    int autonLow;
    int autonOuter;
    int autonInner;
    int autonShot;
    boolean autonCollect;

    int teleopShot;
    int teleopLow;
    int teleopOuter;
    int teleopInner;
    int ballCap;
    boolean crossBumps;
    boolean ableRotate;
    boolean rotationControl;
    boolean positionControl;

    boolean buddyClimb;
    boolean climb;
    double climbTime;
    boolean level;

    boolean playedDefense;
    String defenseComments;
    int defensePenalties;
    String teamNumber;
    String matchNumber;

    String comments;

    int penalties;

    String tabletName = "RED2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_screen);

        getData();

        Button toConnectionButton = (Button) findViewById(R.id.toConnectionButton);
        toConnectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmationBox();
            }
        });
    }

    public void getData() {
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        TextView summaryView = (TextView) findViewById(R.id.summaryView);

        name = data.getString("USER_NAME");

        //startPowerCells = data.getInt("START_POWER_CELLS");
        moveInit = data.getBoolean("MOVE_INIT");
        autonLow = data.getInt("AUTON_LOW");
        autonOuter = data.getInt("AUTON_OUTER");
        autonInner = data.getInt("AUTON_INNER");
        autonShot = data.getInt("AUTON_SHOT");
        autonCollect = data.getBoolean("AUTON_COLLECT");

        teleopShot = data.getInt("TELEOP_SHOT");
        teleopLow = data.getInt("TELEOP_LOW");
        teleopOuter = data.getInt("TELEOP_OUTER");
        teleopInner = data.getInt("TELEOP_INNER");
        ballCap = data.getInt("BALL_CAP");
        crossBumps = data.getBoolean("CROSS_BUMPS");
        ableRotate = data.getBoolean("ABLE_ROTATE");
        rotationControl = data.getBoolean("ROTATIONAL_CONTROL");
        positionControl = data.getBoolean("POSITIONAL_CONTROL");

        buddyClimb = data.getBoolean("BUDDY_CLIMB");
        climb = data.getBoolean("CLIMB");
        climbTime = data.getDouble("CLIMB_TIME");
        level = data.getBoolean("LEVEL");

        playedDefense = data.getBoolean("PLAYED_DEFENSE");
        defenseComments = data.getString("DEFENSE_COMMENTS");
        defensePenalties = data.getInt("DEFENSE_PENALTIES");

        comments = data.getString("COMMENTS");
        penalties = data.getInt("PENALTIES");

        teamNumber = data.getString("TEAM_NUMBER");

        matchNumber = data.getString("MATCH_NUMBER");


        String summary = "MATCH #: " + matchNumber
                + System.lineSeparator() + "TEAM #: " + teamNumber
                //+ System.lineSeparator() + "STARTING POWER CELLS: " + startPowerCells
                + System.lineSeparator() + "MOVED OUT OF INIT LINE? " + moveInit
                + System.lineSeparator() + "CAN THEY COLLECT IN AUTON?: " + autonCollect
                + System.lineSeparator() + "TOTAL AUTON BALLS SHOT: " + autonShot
                + System.lineSeparator() + "AUTON BOTTOM SCORED: " + autonLow
                + System.lineSeparator() + "AUTON OUTER SCORED: " + autonOuter
                + System.lineSeparator() + "AUTON INNER SCORED: " + autonInner
                + System.lineSeparator()
                + System.lineSeparator() + "PENALTIES: " + penalties
                + System.lineSeparator()
                + System.lineSeparator() + "CAN THEY CROSS THE BUMPS? " + crossBumps
                + System.lineSeparator() + "BALL CAPACITY: " + ballCap
                + System.lineSeparator() + "TOTAL TELEOP BALLS SHOT: " + teleopShot
                + System.lineSeparator() + "TELEOP BOTTOM SCORED: " + teleopLow
                + System.lineSeparator() + "TELEOP OUTER SCORED: " + teleopOuter
                + System.lineSeparator() + "TELEOP INNER SCORED: " + teleopInner
                + System.lineSeparator()
                + System.lineSeparator() + "ABLE TO ROTATE THE WHEEL? " + ableRotate
                + System.lineSeparator() + "SUCCESSFUL ROTATIONAL CONTROL? " + rotationControl
                + System.lineSeparator() + "SUCCESSFUL POSITIONAL CONTROL? " + positionControl
                + System.lineSeparator()
                + System.lineSeparator() + "DEFENSE? " + playedDefense
                + System.lineSeparator() + "DEFENSE PENALTIES: " + defensePenalties
                + System.lineSeparator()
                + System.lineSeparator() + "ABLE TO BUDDY CLIMB? " + buddyClimb
                + System.lineSeparator() + "ABLE TO CLIMB? " + climb
                + System.lineSeparator() + "CLIMB TIME: " + climbTime + " SECONDS"
                + System.lineSeparator() + "ABLE TO LEVEL? " + level;
        summaryView.setText(summary);
    }

    private void confirmationBox() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert!");
        builder.setMessage("You are about to save and connect to the Master. Are you sure the data is correct?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String fileName = fileName(teamNumber, matchNumber);
                String inputData = combineData();
                saveDataToFile(inputData, fileName);
                Intent intent = new Intent(summaryScreen.this, qrCodes.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void saveDataToFile(String input, String filename) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS + "/Unprocessed Files"), filename);
        File unprocessedFolder = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS) +  "/Unprocessed Files");
        /*File masterFolder = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS) + "/Master Files");
        File masterFile = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS +"/Master Files"), filename);
        if (!masterFolder.exists()) {
            masterFolder.mkdirs();
        }*/
        boolean successUnprocessedCreate = true;

        if (!unprocessedFolder.exists()) {
            successUnprocessedCreate = unprocessedFolder.mkdir();
        }

/*        if (successUnprocessedCreate) {
            Toast.makeText(SummaryScreen.this, "Unprocessed Directory created",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(SummaryScreen.this, "Error",
                    Toast.LENGTH_SHORT).show();
        }  */

        try {
            FileWriter fileWriter1 = new FileWriter(file);
            fileWriter1.append(input);
            fileWriter1.flush();
            fileWriter1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*try {
            FileWriter fileWriter1 = new FileWriter(masterFile);
            fileWriter1.append(input);
            fileWriter1.flush();
            fileWriter1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }


    public String date() {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH) + 1;
        int year = c.get(Calendar.YEAR);
        String[] fixedDayMonth = fixDate(day, month);
        String formatteddate = fixedDayMonth[0] + "" + fixedDayMonth[1] + "" + year;
        return formatteddate;
    }

    private String[] fixDate (int day, int month) {
        int lengthDay = (int)(Math.log10(day) + 1);
        int lengthMonth = (int)(Math.log10(month) + 1);
        String stringDay = day + "";
        String stringMonth = month + "";
        if (lengthDay == 1) {
            stringDay = "0" + stringDay;
        }
        if (lengthMonth == 1) {
            stringMonth = "0" + stringMonth;
        }

        String[] toReturn = {stringMonth, stringDay};
        return toReturn;
    }

    private String fileName(String teamNum, String matchNum) {
        //String currDate = date();
        return "MATCH" + matchNum + " TEAM" + teamNum + " " + tabletName + ".txt";
    }

    private String combineData() {
        String combined = date() + "," + teamNumber + "," + matchNumber
                //+ "," + startPowerCells
                + "," + moveInit + "," + autonCollect
                + "," + autonShot + "," + autonLow + "," + autonOuter + "," + autonInner
                + "," + penalties
                + "," + crossBumps + "," + ballCap
                + "," + teleopShot + "," + teleopLow + "," + teleopOuter + "," + teleopInner
                + "," + ableRotate + "," + rotationControl + "," + positionControl
                + "," + playedDefense + "," + defenseComments + "," + defensePenalties
                + "," + buddyClimb
                + "," + climb + "," + climbTime +"," + level
                + "," + comments
                + "," + name + "," + tabletName;
        return combined;
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        Bundle data = new Bundle();

        data.putString("USER_NAME", name);
        data.putString("TEAM_NUMBER", teamNumber);
        data.putString("MATCH_NUMBER", matchNumber);
        //data.putInt("START_POWER_CELLS", startPowerCells);
        data.putBoolean("MOVE_INIT", moveInit);
        data.putInt("AUTON_LOW", autonLow);
        data.putInt("AUTON_OUTER", autonOuter);
        data.putInt("AUTON_INNER", autonInner);
        data.putInt("AUTON_SHOT", autonShot);
        data.putBoolean("AUTON_COLLECT", autonCollect);

        data.putInt("TELEOP_SHOT", teleopShot);
        data.putInt("TELEOP_LOW", teleopLow);
        data.putInt("TELEOP_OUTER", teleopOuter);
        data.putInt("TELEOP_INNER", teleopInner);
        data.putInt("BALL_CAP", ballCap);
        data.putBoolean("CROSS_BUMPS", crossBumps);
        data.putBoolean("ABLE_ROTATE", ableRotate);
        data.putBoolean("ROTATIONAL_CONTROL", rotationControl);
        data.putBoolean("POSITIONAL_CONTROL", positionControl);

        data.putBoolean("BUDDY_CLIMB", buddyClimb);
        data.putBoolean("CLIMB", climb);
        data.putDouble("CLIMB_TIME", climbTime);
        data.putBoolean("LEVEL", level);

        data.putBoolean("PLAYED_DEFENSE", playedDefense);
        data.putString("DEFENSE_COMMENTS", defenseComments);
        data.putInt("DEFENSE_PENALTIES", defensePenalties);

        data.putString("COMMENTS", comments);
        data.putInt("PENALTIES", penalties);
        resultIntent.putExtras(data);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
