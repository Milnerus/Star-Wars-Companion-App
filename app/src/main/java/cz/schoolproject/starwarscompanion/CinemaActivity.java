package cz.schoolproject.starwarscompanion;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class CinemaActivity extends AppCompatActivity implements OnItemSelectedListener{
    private Spinner spinner;
    private RelativeLayout ep7;
    private RelativeLayout ep8;
    private RelativeLayout ep9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("V kinech");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BD0000")));
        getSupportActionBar().setIcon(R.drawable.ic_menu_cinema);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //spinner & episode layouts
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        ep7 = (RelativeLayout)this.findViewById(R.id.layout_ep7);
        ep8 = (RelativeLayout)this.findViewById(R.id.layout_ep8);
        ep9 = (RelativeLayout)this.findViewById(R.id.layout_ep9);

        List<String> epizody = new ArrayList<String>();
        epizody.add("Star Wars - Epizoda VII: Síla se probouzí");
        epizody.add("Star Wars - Epizoda VIII");
        epizody.add("Star Wars - Epizoda IX");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,epizody);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        if (spinner.getSelectedItem().toString().equals("Star Wars - Epizoda VII: Síla se probouzí")){
            ep7.setVisibility(RelativeLayout.VISIBLE);
            ep8.setVisibility(RelativeLayout.GONE);
            ep9.setVisibility(RelativeLayout.GONE);
        }
        else if (spinner.getSelectedItem().toString().equals("Star Wars - Epizoda VIII")){
            ep7.setVisibility(RelativeLayout.GONE);
            ep8.setVisibility(RelativeLayout.VISIBLE);
            ep9.setVisibility(RelativeLayout.GONE);
        }
        else if (spinner.getSelectedItem().toString().equals("Star Wars - Epizoda IX")){
            ep7.setVisibility(RelativeLayout.GONE);
            ep8.setVisibility(RelativeLayout.GONE);
            ep9.setVisibility(RelativeLayout.VISIBLE);
        }
        else {
            ep7.setVisibility(RelativeLayout.GONE);
            ep8.setVisibility(RelativeLayout.GONE);
            ep9.setVisibility(RelativeLayout.GONE);
            Toast.makeText(dataAdapter.getContext(), "Musíš zvolit epizodu", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (spinner.getSelectedItem().toString().equals("Star Wars - Epizoda VII: Síla se probouzí")){
            ep7.setVisibility(RelativeLayout.VISIBLE);
            ep8.setVisibility(RelativeLayout.GONE);
            ep9.setVisibility(RelativeLayout.GONE);
        }
        else if (spinner.getSelectedItem().toString().equals("Star Wars - Epizoda VIII")){
            ep7.setVisibility(RelativeLayout.GONE);
            ep8.setVisibility(RelativeLayout.VISIBLE);
            ep9.setVisibility(RelativeLayout.GONE);
        }
        else if (spinner.getSelectedItem().toString().equals("Star Wars - Epizoda IX")){
            ep7.setVisibility(RelativeLayout.GONE);
            ep8.setVisibility(RelativeLayout.GONE);
            ep9.setVisibility(RelativeLayout.VISIBLE);
        }else {
            ep7.setVisibility(RelativeLayout.GONE);
            ep8.setVisibility(RelativeLayout.GONE);
            ep9.setVisibility(RelativeLayout.GONE);
            Toast.makeText(parent.getContext(), "Musíš zvolit epizodu", Toast.LENGTH_LONG).show();
        }
        // Při výběru epizody
        String item = parent.getItemAtPosition(position).toString();

        // Ukáže vybranou epizodu
        Toast.makeText(parent.getContext(), "Zvoleno: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
