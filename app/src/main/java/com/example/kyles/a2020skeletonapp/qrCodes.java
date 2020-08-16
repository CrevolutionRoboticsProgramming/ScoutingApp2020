package com.example.kyles.a2020skeletonapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class qrCodes extends AppCompatActivity {

    //File[] files;
    ArrayList<File> files = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_codes);

        checkFiles();




    }

    private void checkFiles() {
        String unprocessedPath = Environment.getExternalStorageDirectory().toString() + "/Documents/Unprocessed Files";
        TextView unprocessedView = (TextView) findViewById(R.id.unprocessedFilesView);
        Log.d("Files", "Path: " + unprocessedPath);
        File directory = new File(unprocessedPath);
        File[] tempFiles = directory.listFiles();
        String fileList = "";
        for (File file : tempFiles) {
            if (!files.contains(file)) {
                files.add(file);
            }
            Log.d("Files", "File name: " + file.getName());
            fileList = fileList + (file.getName() + System.lineSeparator());
        }
        unprocessedView.setText(fileList);
    }

    public void QRCodeGen(View v) {
        String unprocessedPath = Environment.getExternalStorageDirectory().toString() + "/Documents/Unprocessed Files";
        for (File currfile : files) {


            try {
                FileReader fileReader = new FileReader(currfile.getAbsolutePath());
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                String line = bufferedReader.readLine();
                alertBoxQrCode(line, currfile);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void alertBoxQrCode(final String input, final File currFile) {
        String filename = currFile.getName();
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(input, BarcodeFormat.QR_CODE, 500, 500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);


            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(filename);
            final ImageView qrCodeView = new ImageView(qrCodes.this);
            qrCodeView.setImageBitmap(bitmap);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            qrCodeView.setLayoutParams(lp);
            builder.setView(qrCodeView);

            builder.setPositiveButton("Complete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String processedPath = Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOCUMENTS).toString() + "/Processed Files";
                    File processedFile = new File(processedPath, currFile.getName());

                    try {
                        FileWriter fileWriter2 = new FileWriter(processedFile);
                        fileWriter2.append(input);
                        fileWriter2.flush();
                        fileWriter2.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    new File(currFile.getAbsolutePath()).delete();
                    files.remove(currFile);

                    checkFiles();
                }
            });

            builder.setNegativeButton("Skip", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    checkFiles();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, mainMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
