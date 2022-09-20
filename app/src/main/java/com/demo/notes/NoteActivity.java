package com.demo.notes;

import static com.demo.notes.MainActivity.selectedNote;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.demo.notes.databinding.ActivityNoteBinding;

public class NoteActivity extends AppCompatActivity {
    ActivityNoteBinding binding;
    Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        note = selectedNote;

        binding = ActivityNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        binding.toolbar.setTitle(note.title);
        binding.content.setText(note.content);
    }
}
