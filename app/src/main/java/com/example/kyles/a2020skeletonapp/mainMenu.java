package com.example.kyles.a2020skeletonapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class mainMenu extends AppCompatActivity {

    String name = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Intent intent = getIntent();
        name = intent.getStringExtra("USER_NAME");

        TextView userView = findViewById(R.id.userView);
        userView.setText(name);
        if (name == null) {
            inputName();
        }



        Button sendFilesButton = (Button) findViewById(R.id.sendFilesButton);
        Button scoutButton = (Button) findViewById(R.id.scoutButton);
        Button editNameBtn = findViewById(R.id.editNameBtn);

        sendFilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSendScreen();
            }
        });

        scoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toTeamMatchScreen();
            }
        });

        editNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputName();
            }
        });
    }

    public void inputName() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert!");
        builder.setMessage("Please enter your name.");

        final EditText input = new EditText(mainMenu.this);
        input.setText(name);
        input.setMaxLines(1);


        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);

        builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String placeholderName = input.getText().toString();
                String strWithoutSpace = placeholderName.replaceAll("\\s", "");
                if (!(strWithoutSpace.equals(""))) {
                    name = placeholderName;
                    TextView userView = findViewById(R.id.userView);
                    userView.setText(name);
                } else {
                    inputName();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void toSendScreen() {
        Intent intent = new Intent(this, qrCodes.class);
        intent.putExtra("START_POINT", "Main Menu");
        intent.putExtra("USER_NAME", name);
        startActivity(intent);
    }

    public void toTeamMatchScreen() {
        Intent intent = new Intent(this, teamSelection.class);
        if (name == null) {
            inputName();
        } else {
            intent.putExtra("USER_NAME", name);
            startActivity(intent);
        }
    }

}
