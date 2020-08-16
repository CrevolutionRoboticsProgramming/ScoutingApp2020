package com.example.kyles.a2020skeletonapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.os.SystemClock;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TeleopScreen extends AppCompatActivity implements generalMethods {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    static String name;

    //static int startPowerCells;
    static boolean moveInit;
    static int autonLow;
    static int autonOuter;
    static int autonInner;
    static int autonShot;
    static boolean autonCollect;

    static int teleopShot;
    static int teleopLow;
    static int teleopOuter;
    static int teleopInner;
    static int ballCap;
    static boolean crossBumps;
    static boolean ableRotate;
    static boolean rotationControl;
    static boolean positionControl;

    static boolean buddyClimb;
    static boolean climb;
    static double climbTime;
    static boolean level;

    static boolean chronometerRunning = false;



    static boolean playedDefense;
    static String defenseComments;
    static int defensePenalties;

    static String comments = null;

    static int penalties = 0;

    String matchNumber;
    String teamNumber;
    ImageView imageView;
    ArrayList<String> compInfo = new ArrayList<>();

    int[] powerCellGroupBtnIds = {R.id.teleopBottomMinusBtn, R.id.teleopBottomPlusBtn,
                                R.id.teleopOuterMinusBtn, R.id.teleopOuterPlusBtn,
                                R.id.teleopInnerMinusBtn, R.id.teleopInnerPlusBtn,
                                R.id.teleopShotMinusBtn, R.id.teleopShotPlusBtn,
                                R.id.teleopBallCapMinusBtn, R.id.teleopBallCapPlusBtn};
    int[] powerCellGroupTxtIds = {R.id.teleopBottomView, R.id.teleopOuterView, R.id.teleopInnerView,  R.id.teleopCollectedView, R.id.ballsCollectedText,
                                  R.id.ballCapText, R.id.teleopCapacityView};
    int[] controlPanelGroupIds = {R.id.rotateCheckbox, R.id.rotationalControlCheckbox, R.id.positionalControlCheckBox};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teleop_screen);

        getData();
        setTitles(teamNumber, matchNumber);
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


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));





    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 2:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle moreData = data.getExtras();
                    name = moreData.getString("USER_NAME");

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
                    ballCap = moreData.getInt("BALL_CAP");
                    crossBumps = moreData.getBoolean("CROSS_BUMPS");
                    ableRotate = moreData.getBoolean("ABLE_ROTATE");
                    rotationControl = moreData.getBoolean("ROTATIONAL_CONTROL");
                    positionControl = moreData.getBoolean("POSITIONAL_CONTROL");

                    buddyClimb = moreData.getBoolean("BUDDY_CLIMB");
                    climb = moreData.getBoolean("CLIMB");
                    climbTime = moreData.getDouble("CLIMB_TIME");
                    level = moreData.getBoolean("LEVEL");

                    playedDefense = moreData.getBoolean("PLAYED_DEFENSE");
                    defenseComments = moreData.getString("DEFENSE_COMMENTS");
                    defensePenalties = moreData.getInt("DEFENSE_PENALTIES");

                    comments = moreData.getString("COMMENTS");
                    penalties = moreData.getInt("PENALTIES");
                }

                break;
        }
    }

    @Override
    public void backToTeamMatch(View v) {
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



    public void checkDefense(View v) {
        boolean checked = ((CheckBox) v).isChecked();
        TextView defenseView = findViewById(R.id.defenseTextView);
        EditText defenseEditText = findViewById(R.id.defenseCommentBox);
        TextView DefensePenaltiesTextView = findViewById(R.id.DefensePenaltiesTextView);
        TextView defPenaltiesView = findViewById(R.id.defPenaltiesView);
        Button defPenaltiesPlusBtn = findViewById(R.id.defPenaltiesPlusBtn);
        Button defPenaltiesMinusBtn = findViewById(R.id.defPenaltiesMinusBtn);
        if (checked) {
            playedDefense = true;
            defenseView.setVisibility(View.VISIBLE);
            defenseEditText.setVisibility(View.VISIBLE);
            DefensePenaltiesTextView.setVisibility(View.VISIBLE);
            defPenaltiesView.setVisibility(View.VISIBLE);
            defPenaltiesPlusBtn.setVisibility(View.VISIBLE);
            defPenaltiesMinusBtn.setVisibility(View.VISIBLE);
            defensePenalties = Integer.parseInt(defPenaltiesView.getText().toString());
        } else {
            playedDefense = false;
            defenseComments = null;
            defenseView.setVisibility(View.GONE);
            defenseEditText.setVisibility(View.GONE);
            DefensePenaltiesTextView.setVisibility(View.GONE);
            defPenaltiesView.setVisibility(View.GONE);
            defPenaltiesPlusBtn.setVisibility(View.GONE);
            defPenaltiesMinusBtn.setVisibility(View.GONE);
            defensePenalties = 0;
        }
    }



    private void nullTeamOrMatch() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert!");
        builder.setMessage("The Team Number input field isn't filled in yet. Please do so before proceeding");

        final AutoCompleteTextView input = new AutoCompleteTextView(TeleopScreen.this);
        input.setThreshold(1);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                compInfo);
        input.setAdapter(arrayAdapter);
        input.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                String parts[] = text.split(":");
                teamNumber = parts[0];
                setTitles(teamNumber, matchNumber);
            }
        });
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);
        builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!(teamNumber == null)) {
                    sendData();

                } else {
                    nullTeamOrMatch();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                teamNumber = null;
                setTitles(teamNumber, matchNumber);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void setTitles(String teamNumber, String matchNumber) {
        TextView teamMatchView = (TextView) findViewById(R.id.teamMatchView);
        String combined = "    Match " + matchNumber + ", Team " + teamNumber;
        teamMatchView.setText(combined);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_teleop_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void autonButtonCheck(View v) {
        switch (v.getId()) {
            case R.id.autoBottomMinusBtn:
                if (autonLow > 0) {
                    autonLow -= 1;
                    autonTextViewUpdate("autoBottomView");
                }
                break;
            case R.id.autoOuterMinusBtn:
                if (autonOuter > 0) {
                    autonOuter -= 1;
                    autonTextViewUpdate("autoOuterView");
                }
                break;
            case R.id.autoInnerMinusBtn:
                if (autonInner > 0) {
                    autonInner -= 1;
                    autonTextViewUpdate("autoInnerView");
                }
                break;
            case R.id.autoShotMinusBtn:
                if (autonShot > 0) {
                    autonShot -= 1;
                    autonTextViewUpdate("autoCollectedView");
                }
                break;


            case R.id.autoBottomPlusBtn:
                autonLow += 1;
                autonTextViewUpdate("autoBottomView");
                break;
            case R.id.autoOuterPlusBtn:
                autonOuter += 1;
                autonTextViewUpdate("autoOuterView");
                break;
            case R.id.autoInnerPlusBtn:
                autonInner += 1;
                autonTextViewUpdate("autoInnerView");
                break;
            case R.id.autoShotPlusBtn:
                autonShot += 1;
                autonTextViewUpdate("autoCollectedView");
                break;
        }

    }

    /*@Override
    public void autonRadioButtonCheck(View v) {
        boolean checked  = ((RadioButton) v).isChecked();
        switch (v.getId()) {
            case R.id.noInitLineRadioButton:
                if (checked) {
                    moveInit = false;
                }
                break;
            case R.id.moveInitLineRadioButton:
                if (checked) {
                    moveInit = true;
                }
                break;
            case R.id.oneStart:
                if (checked) {
                    startPowerCells = 1;
                }
                break;
            case R.id.twoStart:
                if (checked) {
                    startPowerCells = 2;
                }
                break;
            case R.id.threeStart:
                if (checked) {
                    startPowerCells = 3;
                }
                break;
        }
    }
    */

    @Override
    public void autonCheckboxCheck(View v) {
        boolean checked = ((CheckBox) v).isChecked();
        switch (v.getId()) {
            case R.id.autonInitLineCheckbox:
                if (checked) {
                    moveInit = true;
                } else {
                    moveInit = false;
                }

                break;
            case R.id.autonCollectCheckbox:
                if (checked) {
                    autonCollect = true;
                } else {
                    autonCollect = false;
                }

                break;
        }

    }

    @Override
    public void autonTextViewUpdate(String textView) {
        TextView autoBottomView = findViewById(R.id.autoBottomView);
        TextView autoOuterView = findViewById(R.id.autoOuterView);
        TextView autoInnerView = findViewById(R.id.autoInnerView);
        TextView autoCollectedView = findViewById(R.id.autoCollectedView);

        switch (textView) {
            case "autoBottomView":
                autoBottomView.setText(autonLow + "");
                break;
            case "autoOuterView":
                autoOuterView.setText(autonOuter + "");
                break;
            case "autoInnerView":
                autoInnerView.setText(autonInner + "");
                break;
            case "autoCollectedView":
                autoCollectedView.setText(autonShot + "");
                break;
        }
    }


    @Override
    public void teleopButtonCheck(View v) {
        switch (v.getId()) {
            case R.id.teleopBottomMinusBtn:
                if (teleopLow > 0) {
                    teleopLow -= 1;
                    teleopTextViewUpdate("teleopBottomView");
                }
                break;
            case R.id.teleopOuterMinusBtn:
                if (teleopOuter > 0) {
                    teleopOuter -= 1;
                    teleopTextViewUpdate("teleopOuterView");
                }
                break;
            case R.id.teleopInnerMinusBtn:
                if (teleopInner > 0) {
                    teleopInner -= 1;
                    teleopTextViewUpdate("teleopInnerView");
                }
                break;
            case R.id.teleopShotMinusBtn:
                if (teleopShot > 0) {
                    teleopShot -= 1;
                    teleopTextViewUpdate("teleopCollectedView");
                }
                break;
            case R.id.teleopBallCapMinusBtn:
                if (ballCap > 0) {
                    ballCap -= 1;
                    teleopTextViewUpdate("teleopBallCapView");
                }
                break;
            case R.id.penaltyMinusBtn:
                if (penalties > 0) {
                    penalties -= 1;
                    teleopTextViewUpdate("teleopPenaltyView");
                }
                break;


            case R.id.teleopBottomPlusBtn:
                teleopLow += 1;
                teleopTextViewUpdate("teleopBottomView");
                break;
            case R.id.teleopOuterPlusBtn:
                teleopOuter += 1;
                teleopTextViewUpdate("teleopOuterView");
                break;
            case R.id.teleopInnerPlusBtn:
                teleopInner += 1;
                teleopTextViewUpdate("teleopInnerView");
                break;
            case R.id.teleopShotPlusBtn:
                teleopShot += 1;
                teleopTextViewUpdate("teleopCollectedView");
                break;
            case R.id.teleopBallCapPlusBtn:
                if (ballCap < 5) {
                    ballCap += 1;
                    teleopTextViewUpdate("teleopBallCapView");
                }
                break;
            case R.id.penaltyPlusBtn:
                penalties += 1;
                teleopTextViewUpdate("teleopPenaltyView");
                break;



        }

    }

    @Override
    public void teleopCheckboxCheck(View v) {
        boolean checked = ((CheckBox) v).isChecked();
        switch (v.getId()) {
            case R.id.crossBumpCheckbox:
                if (checked) {
                    crossBumps = true;
                } else {
                    crossBumps = false;
                }
                break;
            case R.id.rotateCheckbox:
                if (checked) {
                    ableRotate = true;
                } else {
                    ableRotate = false;
                }
                break;
            case R.id.rotationalControlCheckbox:
                if (checked) {
                    rotationControl = true;
                } else {
                    rotationControl = false;
                }
                break;
            case R.id.positionalControlCheckBox:
                if (checked) {
                    positionControl = true;
                } else {
                    positionControl = false;
                }
                break;
        }
    }

    @Override
    public void teleopImageGroupUpdate(View v) {
        boolean checked = ((RadioButton) v).isChecked();
        switch (v.getId()) {
            case R.id.powerCellRadioButton:
                if (checked) {
                    powerCellVisibility(true);
                    controlPanelVisibility(false);
                }
                break;
            case R.id.controlPanelRadioButton:
                if (checked) {
                    powerCellVisibility(false);
                    controlPanelVisibility(true);
                }
                break;
        }
    }

    @Override
    public void teleopTextViewUpdate(String textView) {
        TextView teleopBottomView = findViewById(R.id.teleopBottomView);
        TextView teleopOuterView = findViewById(R.id.teleopOuterView);
        TextView teleopInnerView = findViewById(R.id.teleopInnerView);
        TextView teleopCollectedView = findViewById(R.id.teleopCollectedView);
        TextView teleopBallCapView = findViewById(R.id.teleopCapacityView);
        TextView teleopPenaltyView = findViewById(R.id.penaltiesView);

        switch (textView) {
            case "teleopBottomView":
                teleopBottomView.setText(teleopLow + "");
                break;
            case "teleopOuterView":
                teleopOuterView.setText(teleopOuter + "");
                break;
            case "teleopInnerView":
                teleopInnerView.setText(teleopInner + "");
                break;
            case "teleopCollectedView":
                teleopCollectedView.setText(teleopShot + "");
                break;
            case "teleopBallCapView":
                teleopBallCapView.setText(ballCap + "");
                break;
            case "teleopPenaltyView":
                teleopPenaltyView.setText(penalties + "");
                break;
        }
    }

    @Override
    public void defenseBtnCheck(View v) {
        TextView defPenaltiesView = findViewById(R.id.defPenaltiesView);
        switch (v.getId()) {
            case R.id.defPenaltiesMinusBtn:
                if (defensePenalties > 0) {
                    defensePenalties -= 1;
                }
                defPenaltiesView.setText(defensePenalties + "");
                break;
            case R.id.defPenaltiesPlusBtn:
                defensePenalties += 1;
                defPenaltiesView.setText(defensePenalties + "");
                break;
        }
    }

    public void powerCellVisibility(boolean flag) {
        ImageView powerStationView = findViewById(R.id.teleopPowerStationView);
        for (int i : powerCellGroupBtnIds) {
            Button button = findViewById(i);
            if (flag) {
                button.setVisibility(View.VISIBLE);
                powerStationView.setVisibility(View.VISIBLE);
            } else {
                button.setVisibility(View.GONE);
                powerStationView.setVisibility(View.GONE);
            }
        }
        for (int i: powerCellGroupTxtIds) {
            TextView textView = findViewById(i);
            if (flag) {
                textView.setVisibility(View.VISIBLE);
            } else {
                textView.setVisibility(View.GONE);
            }
        }
    }

    public void controlPanelVisibility(boolean flag) {
        ImageView controlPanelView = findViewById(R.id.controlPanelImageView);
        for (int i : controlPanelGroupIds) {
            CheckBox checkBox = findViewById(i);
            if (flag) {
                checkBox.setVisibility(View.VISIBLE);
                controlPanelView.setVisibility(View.VISIBLE);
            } else {
                checkBox.setVisibility(View.GONE);
                controlPanelView.setVisibility(View.GONE);
            }
        }
    }



    @Override
    public void endgameCheckboxCheck(View v) {
        boolean checked = ((CheckBox) v).isChecked();
        switch (v.getId()) {
            case R.id.buddyClimbCheckbox:
                if (checked) {
                    buddyClimb = true;
                } else {
                    buddyClimb = false;
                }
                break;
            case R.id.climbCheckbox:
                if (checked) {
                    climb = true;
                } else {
                    climb = false;
                }
                break;
            case R.id.levelCheckbox:
                if (checked) {
                    level = true;
                } else {
                    level = false;
                }
                break;
        }
    }

    @Override
    public void endgameChronometerControl(View v) {
        Chronometer climbChronoMeter = findViewById(R.id.climbChronoMeter);
        Button chronoStartStopBtn = findViewById(R.id.chronoStartStopBtn);
        switch (v.getId()) {
            case R.id.chronoStartStopBtn:
                if (chronometerRunning) {
                    climbChronoMeter.stop();
                    climbTime = (SystemClock.elapsedRealtime() - climbChronoMeter.getBase()) / 1000;
                    chronometerRunning = false;
                    chronoStartStopBtn.setText("START");

                } else {
                    climbChronoMeter.setBase(SystemClock.elapsedRealtime() - (long)(climbTime * 1000));
                    climbChronoMeter.start();
                    chronometerRunning = true;
                    chronoStartStopBtn.setText("STOP");
                }
                break;
            case R.id.chronoResetBtn:
                climbChronoMeter.setBase(SystemClock.elapsedRealtime());
                climbChronoMeter.stop();
                climbTime = 0;
                chronometerRunning = false;
                chronoStartStopBtn.setText("START");
                break;
        }
    }

    public void check(View view) {
        if (!(teamNumber == null)) {
            sendData();
        } else {
            nullTeamOrMatch();
        }
    }

    @Override
    public void getData() {
        Intent intent = getIntent();
        Bundle data = intent.getExtras();

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


    }

    @Override
    public void sendData() {
        Intent toSummary = new Intent(this, summaryScreen.class);
        EditText editText = findViewById(R.id.CommentBox);
        comments = editText.getText().toString();
        if (comments.contains(",")) {
            comments = comments.replaceAll(",", "...");
        }
        if (comments.contains(", ")) {
            comments = comments.replaceAll(", ", "...");
        }

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

        toSummary.putExtras(data);
        startActivityForResult(toSummary, 2);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = null;
            switch(getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    rootView = inflater.inflate(R.layout.fragment_auton, container, false);
                    TextView autoInnerView = rootView.findViewById(R.id.autoInnerView);
                    TextView autoOuterView = rootView.findViewById(R.id.autoOuterView);
                    TextView autoBottomView = rootView.findViewById(R.id.autoBottomView);
                    TextView autoCollectedView = rootView.findViewById(R.id.autoCollectedView);

                    autoInnerView.setText(autonInner + "");
                    autoOuterView.setText(autonOuter + "");
                    autoBottomView.setText(autonLow + "");
                    autoCollectedView.setText(autonShot + "");

                    CheckBox autonInitLineCheckbox = rootView.findViewById(R.id.autonInitLineCheckbox);
                    CheckBox autonCollectCheckbox = rootView.findViewById(R.id.autonCollectCheckbox);

                    if (moveInit) {
                        autonInitLineCheckbox.setChecked(true);
                    } else {
                        autonInitLineCheckbox.setChecked(false);
                    }

                    if (autonCollect) {
                        autonCollectCheckbox.setChecked(true);
                    } else {
                        autonCollectCheckbox.setChecked(false);
                    }


                    /*RadioGroup initLineRadioGroup = rootView.findViewById(R.id.autonInitLineRadioGroup);
                    RadioGroup startConfigRadioGroup = rootView.findViewById(R.id.autonStartingConfigRadioGroup);
                    RadioButton noMoveInitLine = rootView.findViewById(R.id.noInitLineRadioButton);
                    RadioButton moveInitLine = rootView.findViewById(R.id.moveInitLineRadioButton);
                    RadioButton oneStart = rootView.findViewById(R.id.oneStart);
                    RadioButton twoStart = rootView.findViewById(R.id.twoStart);
                    RadioButton threeStart = rootView.findViewById(R.id.threeStart);

                    initLineRadioGroup.clearCheck();
                    startConfigRadioGroup.clearCheck();

                    if (moveInit) {
                        moveInitLine.setChecked(true);
                    } else {
                        noMoveInitLine.setChecked(true);
                    }

                    switch (startPowerCells) {
                        case 1:
                            oneStart.setChecked(true);
                            break;
                        case 2:
                            twoStart.setChecked(true);
                            break;
                        case 3:
                            threeStart.setChecked(true);
                            break;
                    }
                    */


                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.fragment_teleop, container, false);
                    TextView teleopInnerView = rootView.findViewById(R.id.teleopInnerView);
                    TextView teleopOuterView = rootView.findViewById(R.id.teleopOuterView);
                    TextView teleopBottomView = rootView.findViewById(R.id.teleopBottomView);
                    TextView teleopShotView = rootView.findViewById(R.id.teleopCollectedView);
                    TextView ballCapView = rootView.findViewById(R.id.teleopCapacityView);
                    TextView penaltyView = rootView.findViewById(R.id.penaltiesView);

                    teleopInnerView.setText(teleopInner + "");
                    teleopOuterView.setText(teleopOuter + "");
                    teleopBottomView.setText(teleopLow + "");
                    teleopShotView.setText(teleopShot + "");
                    ballCapView.setText(ballCap + "");
                    penaltyView.setText(penalties + "");

                    RadioGroup teleopRadioGroup = rootView.findViewById(R.id.teleopRadioGroup);
                    RadioButton powerCellRadioButton = rootView.findViewById(R.id.powerCellRadioButton);
                    teleopRadioGroup.clearCheck();
                    powerCellRadioButton.setChecked(true);

                    CheckBox crossBumpCheckbox = rootView.findViewById(R.id.crossBumpCheckbox);
                    CheckBox rotateCheckbox = rootView.findViewById(R.id.rotateCheckbox);
                    CheckBox rotationalControlCheckbox = rootView.findViewById(R.id.rotationalControlCheckbox);
                    CheckBox positionalControlCheckbox = rootView.findViewById(R.id.positionalControlCheckBox);

                    crossBumpCheckbox.setChecked(crossBumps);
                    rotateCheckbox.setChecked(ableRotate);
                    rotationalControlCheckbox.setChecked(rotationControl);
                    positionalControlCheckbox.setChecked(positionControl);

                    break;
                case 3:
                    rootView = inflater.inflate(R.layout.fragment_defense, container, false);
                    EditText defenseCommentBox = rootView.findViewById(R.id.defenseCommentBox);
                    final CheckBox defenseCheckBox = rootView.findViewById(R.id.playedDefenseCheckbox);

                    TextView DefensePenaltiesTextView = rootView.findViewById(R.id.DefensePenaltiesTextView);
                    TextView defPenaltiesView = rootView.findViewById(R.id.defPenaltiesView);
                    Button defPenaltiesPlusBtn = rootView.findViewById(R.id.defPenaltiesPlusBtn);
                    Button defPenaltiesMinusBtn = rootView.findViewById(R.id.defPenaltiesMinusBtn);

                    defenseCommentBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            if (actionId == EditorInfo.IME_ACTION_DONE) {
                                defenseComments = v.getText().toString();

                                return true;
                            }
                            return false;
                        }
                    });
                    defenseCheckBox.setChecked(playedDefense);
                    if (playedDefense) {
                        defenseCommentBox.setVisibility(View.VISIBLE);
                        DefensePenaltiesTextView.setVisibility(View.VISIBLE);
                        defPenaltiesView.setVisibility(View.VISIBLE);
                        defPenaltiesPlusBtn.setVisibility(View.VISIBLE);
                        defPenaltiesMinusBtn.setVisibility(View.VISIBLE);
                        defensePenalties = Integer.parseInt(defPenaltiesView.getText().toString());
                    } else {
                        defenseCommentBox.setVisibility(View.GONE);
                        DefensePenaltiesTextView.setVisibility(View.GONE);
                        defPenaltiesView.setVisibility(View.GONE);
                        defPenaltiesPlusBtn.setVisibility(View.GONE);
                        defPenaltiesMinusBtn.setVisibility(View.GONE);
                        defensePenalties = 0;
                    }

                    defenseCommentBox.setText(defenseComments);

                    defenseCommentBox.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (!hasFocus) {
                                EditText editText = (EditText) v;
                                if (defenseCheckBox.isChecked()) {
                                    defenseComments = editText.getText().toString();
                                    if (defenseComments.contains(",")) {
                                        defenseComments = defenseComments.replaceAll(",", "...");
                                    }
                                    if (defenseComments.contains(", ")) {
                                        defenseComments = defenseComments.replaceAll(", ", "...");
                                    }
                                } else {
                                    defenseComments = null;
                                }

                            }
                        }
                    });
                    break;
                case 4:
                    rootView = inflater.inflate(R.layout.fragment_endgame, container, false);
                    CheckBox buddyClimbCheckbox, climbCheckBox, levelCheckBox;
                    buddyClimbCheckbox = rootView.findViewById(R.id.buddyClimbCheckbox);
                    climbCheckBox = rootView.findViewById(R.id.climbCheckbox);
                    levelCheckBox = rootView.findViewById(R.id.levelCheckbox);

                    if (buddyClimb) {
                        buddyClimbCheckbox.setChecked(true);
                    } else {
                        buddyClimbCheckbox.setChecked(false);
                    }

                    if (climb) {
                        climbCheckBox.setChecked(true);
                    } else {
                        climbCheckBox.setChecked(false);
                    }

                    if (level) {
                        levelCheckBox.setChecked(true);
                    } else {
                        levelCheckBox.setChecked(false);
                    }

                    EditText commentbox = rootView.findViewById(R.id.CommentBox);
                    commentbox.setText(comments);

                    commentbox.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (!hasFocus) {
                                EditText commentBox = (EditText) v;
                                comments = commentBox.getText().toString();
                                if (comments.contains(",")) {
                                    comments = comments.replaceAll(",", "...");
                                }
                                if (comments.contains(", ")) {
                                    comments = comments.replaceAll(", ", "...");
                                }
                            }
                        }
                    });


                    Chronometer climbChronoMeter = rootView.findViewById(R.id.climbChronoMeter);
                    Button chronoStartStopBtn = rootView.findViewById(R.id.chronoStartStopBtn);
                    climbChronoMeter.stop();
                    chronometerRunning = false;
                    chronoStartStopBtn.setText("START");

                    int climbTimeInt = (int)climbTime;
                    String climbTimeSec = Integer.toString(climbTimeInt % 60);
                    String climbTimeMin = Integer.toString(climbTimeInt / 60);

                    if (Integer.parseInt(climbTimeSec) < 10) {
                        climbTimeSec = "0" + climbTimeSec;
                    }
                    if (Integer.parseInt(climbTimeMin) < 10) {
                        climbTimeMin = "0" + climbTimeMin;
                    }

                    climbChronoMeter.setText(climbTimeMin + ":" + climbTimeSec);
                    break;
            }
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch(position) {
                case 1:
                    break;
            }
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }
    }


    View wow;
    @Override
    public void onBackPressed() {
        backToTeamMatch(wow);
    }
}
