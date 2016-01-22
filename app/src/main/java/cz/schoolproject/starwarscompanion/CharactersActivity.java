package cz.schoolproject.starwarscompanion;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class CharactersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Postavy");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BD0000")));
        getSupportActionBar().setIcon(R.drawable.ic_menu_characters);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
