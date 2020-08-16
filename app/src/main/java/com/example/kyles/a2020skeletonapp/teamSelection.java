package com.example.kyles.a2020skeletonapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.util.SimpleArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class teamSelection extends AppCompatActivity {
    String name = "";

    int penalties = 0;

    //static int startPowerCells = 1;
    static boolean moveInit = false;
    static int autonLow = 0;
    static int autonOuter = 0;
    static int autonInner = 0;
    static int autonShot = 0;
    static boolean autonCollect = false;

    static int teleopShot = 0;
    static int teleopLow = 0;
    static int teleopOuter = 0;
    static int teleopInner = 0;
    static int balLCap = 0;
    static boolean crossBumps = false;
    static boolean ableRotate = false;
    static boolean rotationControl = false;
    static boolean positionControl = false;

    static boolean buddyClimb = false;
    static boolean climb = false;
    static double climbTime = 0;
    static boolean level = false;

    boolean playedDefense = false;
    String defenseComments = null;
    int defensePenalties = 0;

    String comments = null;

    AutoCompleteTextView autoCompleteTextView;
    ArrayList<String> compInfo = new ArrayList<>();
    String currentTeams[];


    int originalTeamPos;

    String selectedMatch = "1";
    String selectedTeam;

    String currentTeamNum = null;
    String currentMatchNum;

    boolean backTrack = false;

    private final SimpleArrayMap<String, String[]> matchInfo = new SimpleArrayMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_selection);
        getName();
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) +
                "/Events.txt");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                compInfo.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                compInfo);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView teamNumView = findViewById(R.id.teamNumView);
                String text = parent.getItemAtPosition(position).toString();
                String parts[] = text.split(":");
                selectedTeam = parts[0];
                if (!(currentTeamNum == null)) {
                    if (backTrack) {
                        if (!(selectedTeam.equals(currentTeamNum))) {
                            diffTeam();
                        }
                    }

                } else {
                    currentTeamNum = selectedTeam;
                    teamNumView.setText(selectedTeam);
                    originalTeamPos = position;
                }
            }
        });


        Button backButton = findViewById(R.id.backButton);
        Button nextButton = findViewById(R.id.nextButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmationBox();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toTeleop();

            }
        });
        final TextView currMatchView = findViewById(R.id.matchNumView);
        Button addBtn = findViewById(R.id.addBtn);
        Button subBtn = findViewById(R.id.subBtn);
        Button plusTenBtn = findViewById(R.id.plusTenBtn);
        Button minusTenBtn = findViewById(R.id.minusTenBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedMatch = currMatchView.getText().toString();
                int intCurrMatch = Integer.parseInt(selectedMatch);
                intCurrMatch += 1;
                selectedMatch = Integer.toString(intCurrMatch);
                if (backTrack) {
                    if (!(selectedMatch.equals(currentMatchNum))) {
                        diffMatch();
                    }
                } else {
                    currMatchView.setText(selectedMatch);
                }
            }
        });
        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedMatch = currMatchView.getText().toString();
                int intCurrMatch = Integer.parseInt(selectedMatch);
                if (intCurrMatch > 1) {
                    intCurrMatch -= 1;
                    selectedMatch = Integer.toString(intCurrMatch);
                    if (backTrack) {
                        if (!(selectedMatch.equals(currentMatchNum))) {
                            diffMatch();
                        }
                    } else {
                        currMatchView.setText(selectedMatch);
                    }
                }
            }
        });

        plusTenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedMatch = currMatchView.getText().toString();
                int intCurrMatch = Integer.parseInt(selectedMatch);
                intCurrMatch += 10;
                selectedMatch = Integer.toString(intCurrMatch);
                if (backTrack) {
                    if (!(selectedMatch.equals(currentMatchNum))) {
                        diffMatch();
                    }
                } else {
                    currMatchView.setText(selectedMatch);
                }
            }
        });

        minusTenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedMatch = currMatchView.getText().toString();
                int intCurrMatch = Integer.parseInt(selectedMatch);
                if (intCurrMatch > 9) {
                    intCurrMatch -= 10;
                    selectedMatch = Integer.toString(intCurrMatch);
                    if (backTrack) {
                        if (!(selectedMatch.equals(currentMatchNum))) {
                            diffMatch();
                        }
                    } else {
                        currMatchView.setText(selectedMatch);
                    }
                }
            }
        });



    }

    private void getName() {
        Intent intent = getIntent();
        name = intent.getStringExtra("USER_NAME");
    }

    private void confirmationBox() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert!");
        builder.setMessage("Backing out to the main menu will result in the current data being recorded" +
                " to be reset. Are you sure you want to go to the main menu?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //startPowerCells = 1;
                moveInit = false;
                autonLow = 0;
                autonOuter = 0;
                autonInner = 0;
                autonShot = 0;
                autonCollect = false;

                teleopShot = 0;
                teleopLow = 0;
                teleopOuter = 0;
                teleopInner = 0;
                balLCap = 0;
                crossBumps = false;
                ableRotate = false;
                rotationControl = false;
                positionControl = false;

                playedDefense = false;
                defenseComments = null;
                defensePenalties = 0;

                buddyClimb = false;
                climb = false;
                climbTime = 0;
                level = false;

                penalties = 0;
                finish();
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
    private void nullTeamOrMatch() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert!");
        builder.setMessage("Either the match or team number fields, or both, aren't filled in. Please" +
                " do so before proceeding.");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void toTeleop() {
        backTrack = false;
        Intent toTeleop = new Intent(this, TeleopScreen.class);
        Bundle data = new Bundle();
        data.putString("USER_NAME", name);

        data.putString("TEAM_NUMBER", currentTeamNum);
        data.putString("MATCH_NUMBER", selectedMatch);

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
        data.putInt("BALL_CAP", balLCap);
        data.putBoolean("CROSS_BUMPS", crossBumps);
        data.putBoolean("ABLE_ROTATE", ableRotate);
        data.putBoolean("ROTATIONAL_CONTROL", rotationControl);
        data.putBoolean("POSITIONAL_CONTROL", positionControl);

        data.putBoolean("PARK", buddyClimb);
        data.putBoolean("CLIMB", climb);
        data.putDouble("CLIMB_TIME", climbTime);
        data.putBoolean("LEVEL", level);

        data.putBoolean("PLAYED_DEFENSE", playedDefense);
        data.putString("DEFENSE_COMMENTS", defenseComments);
        data.putInt("DEFENSE_PENALTIES", defensePenalties);

        data.putString("COMMENTS", comments);
        data.putInt("PENALTIES", penalties);
        toTeleop.putExtras(data);
        startActivityForResult(toTeleop, 3);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (3) : {
                if (resultCode == Activity.RESULT_OK) {
                    Bundle moreData = data.getExtras();
                    name = moreData.getString("USER_NAME");
                    currentTeamNum = moreData.getString("TEAM_NUMBER");
                    currentMatchNum = moreData.getString("MATCH_NUMBER");

                    //startPowerCells = moreData.getInt("START_POWER_CELLS");
                    moveInit = moreData.getBoolean("MOVE_INIT");
                    autonLow = moreData.getInt("AUTON_LOW");
                    autonOuter = moreData.getInt("AUTON_OUTER");
                    autonInner = moreData.getInt("AUTON_INNER");
                    autonShot = moreData.getInt("AUTON_SHOT");
                    autonCollect = moreData.getBoolean("AUTON_COLLECT");

                    teleopShot = moreData.getInt("TELEOP_SHOT");
                    teleopLow = moreData.getInt("TELEOP_LOW");
                    teleopOuter = moreData.getInt("TELEOP_OUTER");
                    teleopInner = moreData.getInt("TELEOP_INNER");
                    balLCap = moreData.getInt("BALL_CAP");
                    crossBumps = moreData.getBoolean("CROSS_BUMPS");
                    ableRotate = moreData.getBoolean("ABLE_ROTATE");
                    rotationControl = moreData.getBoolean("ROTATIONAL_CONTROL");
                    positionControl = moreData.getBoolean("POSITIONAL_CONTROL");

                    buddyClimb = moreData.getBoolean("PARK");
                    climb = moreData.getBoolean("CLIMB");
                    climbTime = moreData.getDouble("CLIMB_TIME");
                    level = moreData.getBoolean("LEVEL");

                    playedDefense = moreData.getBoolean("PLAYED_DEFENSE");
                    defenseComments = moreData.getString("DEFENSE_COMMENTS");
                    defensePenalties = moreData.getInt("DEFENSE_PENALTIES");

                    comments = moreData.getString("COMMENTS");
                    penalties = moreData.getInt("PENALTIES");

                    backTrack = true;
                    TextView teamNumView = findViewById(R.id.teamNumView);
                    teamNumView.setText(currentTeamNum);
                }
                break;
            }

        }
    }






    private void diffMatch() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert!");
        builder.setMessage("Changing the match or team will result in the current data being reset. Do " +
                "you want to proceed?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentTeams = matchInfo.get(selectedMatch);

                //startPowerCells = 1;
                moveInit = false;
                autonLow = 0;
                autonOuter = 0;
                autonInner = 0;
                autonShot = 0;
                autonCollect = false;

                teleopShot = 0;
                teleopLow = 0;
                teleopOuter = 0;
                teleopInner = 0;
                balLCap = 0;
                crossBumps = false;
                ableRotate = false;
                rotationControl = false;
                positionControl = false;

                buddyClimb = false;
                climb = false;
                climbTime = 0;
                level = false;

                playedDefense = false;
                defenseComments = null;
                defensePenalties = 0;

                comments = null;
                penalties = 0;
                //reset all the robot data

                backTrack = false;
                TextView matchView = findViewById(R.id.matchNumView);
                matchView.setText(selectedMatch);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TextView matchView = findViewById(R.id.matchNumView);
                selectedMatch = currentMatchNum;
                matchView.setText(selectedMatch);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void diffTeam() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert!");
        builder.setMessage("Changing the match or team will result in the current data being reset. Do " +
                "you want to proceed?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //reset the variables
                //startPowerCells = 1;
                moveInit = false;
                autonLow = 0;
                autonOuter = 0;
                autonInner = 0;
                autonShot = 0;
                autonCollect = false;

                teleopShot = 0;
                teleopLow = 0;
                teleopOuter = 0;
                teleopInner = 0;
                balLCap = 0;
                crossBumps = false;
                ableRotate = false;
                rotationControl = false;
                positionControl = false;

                buddyClimb = false;
                climb = false;
                climbTime = 0;
                level = false;

                playedDefense = false;
                defenseComments = null;
                defensePenalties = 0;

                backTrack = false;
                penalties = 0;

                currentTeamNum = selectedTeam;
                TextView teamNumView = findViewById(R.id.teamNumView);
                teamNumView.setText(selectedTeam);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TextView teamNumView = findViewById(R.id.teamNumView);
                selectedTeam = currentTeamNum;
                for (String i : compInfo) {
                    if (i.contains(selectedTeam)) {
                        autoCompleteTextView.setText(i);
                    }
                }
                teamNumView.setText(selectedTeam);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        confirmationBox();
    }

}