package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends AppCompatActivity {

    private TextView notesLabel;
    private EditText notesEditText;
    private Button saveButton;
    private Button deleteButton;
    private TextView notesTextView;
    private static final String FILENAME = "notes.txt";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesLabel = findViewById(R.id.notesLabel);
        notesEditText = findViewById(R.id.notesEditText);
        notesLabel.setText("My Notes");

        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton);

        notesTextView = findViewById(R.id.notesTextView);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String note = notesEditText.getText().toString();
                try {
                    FileOutputStream fos = openFileOutput(FILENAME, MODE_APPEND);
                    OutputStreamWriter osw = new OutputStreamWriter(fos);
                    osw.write(note + "\n");
                    osw.close();
                    fos.close();
                    String existingNotes = notesTextView.getText().toString();
                    String newNotes = existingNotes + "\n\n" + note;
                    notesTextView.setText(newNotes);
                    Toast.makeText(MainActivity.this, "Note saved!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notesEditText.setText("");
                notesTextView.setText("");
                Toast.makeText(MainActivity.this, "Note deleted!", Toast.LENGTH_SHORT).show();
            }
        });

        File file = new File(getFilesDir(), FILENAME);
        if (file.exists()) {
            try {
                FileInputStream fis = openFileInput(FILENAME);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                fis.close();
                isr.close();
                br.close();
                notesTextView.setText(sb.toString().trim());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}