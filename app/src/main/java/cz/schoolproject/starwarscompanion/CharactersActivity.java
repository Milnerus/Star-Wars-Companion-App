package cz.schoolproject.starwarscompanion;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.schoolproject.starwarscompanion.models.PersonModel;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class CharactersActivity extends AppCompatActivity {
    private TextView tvData;
    private ListView swPerson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Postavy");
        swPerson = (ListView) findViewById(R.id.swPerson);

        // Create default options which will be used for every
        //  displayImage(...) call if no options will be passed to this method
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).defaultDisplayImageOptions(defaultOptions).build();
        ImageLoader.getInstance().init(config); // Do it on Application start


        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BD0000")));
        getSupportActionBar().setIcon(R.drawable.ic_menu_characters);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
    public class JSONTask extends AsyncTask <String, String, List<PersonModel>> {

        @Override
        protected List<PersonModel> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                JSONObject parentObject=new JSONObject(finalJson);
                JSONArray parentArray= parentObject.getJSONArray("person");

                List<PersonModel> personModelList = new ArrayList<>();

                for(int i=0; i<parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    PersonModel personModel = new PersonModel();
                    personModel.setName(finalObject.getString("name"));
                    personModel.setSex(finalObject.getString("sex"));
                    personModel.setSpecies(finalObject.getString("species"));
                    personModel.setDateofdeath(finalObject.getString("dateofbirth"));
                    personModel.setDateofbirth(finalObject.getString("dateofdeath"));
                    personModel.setMaster(finalObject.getString("master"));
                    personModel.setPadawan(finalObject.getString("padawan"));
                    personModel.setSabercolor(finalObject.getString("sabercolor"));
                    personModel.setImage(finalObject.getString("image"));
                    personModel.setStory(finalObject.getString("story"));

                    List<PersonModel.Movie> movieList = new ArrayList<>();
                    for(int j=0; j<finalObject.getJSONArray("movie").length(); j++){
                        JSONObject castObject =(finalObject.getJSONArray("movie").getJSONObject(j));
                        PersonModel.Movie cast = new PersonModel.Movie();
                        cast.setName(castObject.getString("name"));
                        movieList.add(cast);
                    }
                    personModel.setMovieList(movieList);
                    // adding the final object in the list
                    personModelList.add(personModel);
                }
                return personModelList;



            } catch (MalformedURLException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection != null){
                    connection.disconnect();
                }
                try{
                    if(reader != null){
                        reader.close();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(List<PersonModel> result) {
            super.onPostExecute(result);

            PersonAdapter adapter = new PersonAdapter(getApplicationContext(), R.layout.row, result);
            swPerson.setAdapter(adapter);

        }
    }

    public class PersonAdapter extends ArrayAdapter{
        public List<PersonModel> personModelList;
        private int resource;
        private LayoutInflater inflater;
        public PersonAdapter(Context context, int resource, List<PersonModel> objects) {
            super(context, resource, objects);
            personModelList =objects;
            this.resource=resource;
            inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;


            if(convertView==null){
                holder = new ViewHolder();
                convertView= inflater.inflate(resource, null);
                holder.pIcon = (ImageView)convertView.findViewById(R.id.rowIcon);
                holder.pName = (TextView)convertView.findViewById(R.id.rowName);
                holder.pSex = (TextView)convertView.findViewById(R.id.rowSex);
                holder.pSpecies = (TextView)convertView.findViewById(R.id.rowSpecies);
                holder.pDateOfBirth =(TextView)convertView.findViewById(R.id.rowDateOfBirth);
                holder.pDateOfDeath =(TextView)convertView.findViewById(R.id.rowDateOfDeath);
                holder.pMaster=(TextView)convertView.findViewById(R.id.rowMaster);
                holder.pPadawan=(TextView)convertView.findViewById(R.id.rowPadawan);
                holder.pSaberColor=(TextView)convertView.findViewById(R.id.rowSaberColor);
                holder.pMovie = (TextView)convertView.findViewById(R.id.rowFilms);
                holder.pStory =(TextView)convertView.findViewById(R.id.rowStory);
                convertView.setTag(holder);
            } else {
                holder =(ViewHolder) convertView.getTag();
            }




            final ProgressBar progressBar  = (ProgressBar)convertView.findViewById(R.id.progressBar);

            ImageLoader.getInstance().displayImage(personModelList.get(position).getImage(), holder.pIcon, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    progressBar.setVisibility(View.GONE);
                }
            }); // Default options will be used
            holder.pName.setText("Jméno: " + personModelList.get(position).getName());
            holder.pSex.setText("Pohlaví: "+personModelList.get(position).getMaster());
            holder.pSpecies.setText("Rasa: " + personModelList.get(position).getSex());
            holder.pDateOfBirth.setText("Datum narození: " + personModelList.get(position).getDateofbirth());
            holder.pDateOfDeath.setText("Datum úmrtí: " + personModelList.get(position).getDateofdeath());
            holder.pMaster.setText("Mistr: " + personModelList.get(position).getMaster());
            holder.pPadawan.setText("Učedník: " + personModelList.get(position).getPadawan());
            holder.pSaberColor.setText("Barva meče: " + personModelList.get(position).getSabercolor());
            StringBuffer stringBuffer = new StringBuffer();
            for(PersonModel.Movie movie : personModelList.get(position).getMovieList()){
                stringBuffer.append(movie.getName() + "\n");
            }
            holder.pMovie.setText("Filmy: " + stringBuffer.toString());
            holder.pStory.setText("Příběh: " + personModelList.get(position).getStory());


            return convertView;
        }

        class ViewHolder{
            private ImageView pIcon;
            private TextView pName;
            private TextView pSex;
            private TextView pSpecies;
            private TextView pDateOfBirth;
            private TextView pDateOfDeath;
            private TextView pMaster;
            private TextView pPadawan;
            private TextView pSaberColor;
            private TextView pMovie;
            private TextView pStory;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.character, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_lightside) {
            if(!isOnline()) {
                Toast.makeText(CharactersActivity.this, "" + "Neni pripojeni k internetu, pro výpis postav se prosím připojte!!", Toast.LENGTH_SHORT).show();
                return false;
            }
            new JSONTask().execute("http://jarda.podpora.info/projekt/json/lightside.txt");
            return true;
        }
        if (id == R.id.action_darkside)
        {
            if(!isOnline()) {
                Toast.makeText(CharactersActivity.this, "" + "Neni pripojeni k internetu, pro výpis postav se prosím připojte!",Toast.LENGTH_SHORT).show();
                return false;
            }
            new JSONTask().execute("http://jarda.podpora.info/projekt/json/darkside.txt");
            return true;
        }
        if (id == R.id.action_quit)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
