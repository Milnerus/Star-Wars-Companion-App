package cz.schoolproject.starwarscompanion;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.RatingBar;
import android.widget.TextView;

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

import cz.schoolproject.starwarscompanion.models.MovieModel;

public class CharactersActivity extends AppCompatActivity {
    private TextView tvData;
    private ListView lvMovies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Postavy");
        lvMovies = (ListView) findViewById(R.id.lvMovies);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BD0000")));
        getSupportActionBar().setIcon(R.drawable.ic_menu_characters);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public class JSONTask extends AsyncTask<String, String, List<MovieModel>> {

        @Override
        protected List<MovieModel> doInBackground(String... params) {
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
                JSONArray parentArray= parentObject.getJSONArray("movies");

                List<MovieModel> movieModelList = new ArrayList<>();

                for(int i=0; i<parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    MovieModel movieModel = new MovieModel();
                    movieModel.setMovie(finalObject.getString("movie"));
                    movieModel.setYear(finalObject.getInt("year"));
                    movieModel.setRating((float) finalObject.getDouble("rating"));
                    movieModel.setDirector(finalObject.getString("director"));
                    movieModel.setDuration(finalObject.getString("duration"));
                    movieModel.setTagline(finalObject.getString("tagline"));
                    movieModel.setImage(finalObject.getString("image"));
                    movieModel.setStory(finalObject.getString("story"));

                    List<MovieModel.Cast> castList = new ArrayList<>();
                    for(int j=0; j<finalObject.getJSONArray("cast").length(); j++){
                        JSONObject castObject =(finalObject.getJSONArray("cast").getJSONObject(j));
                        MovieModel.Cast cast = new MovieModel.Cast();
                        cast.setName(castObject.getString("name"));
                        castList.add(cast);
                    }
                    movieModel.setCastList(castList);
                    // adding the final object in the list
                    movieModelList.add(movieModel);
                }
                return  movieModelList;



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
        protected void onPostExecute(List<MovieModel> result) {
            super.onPostExecute(result);

            MovieAdapter adapter = new MovieAdapter(getApplicationContext(), R.layout.row, result);
            lvMovies.setAdapter(adapter);

        }
    }

    public class MovieAdapter extends ArrayAdapter {
        public List<MovieModel> movieModelList;
        private int resource;
        private LayoutInflater inflater;
        public MovieAdapter(Context context, int resource, List<MovieModel> objects) {
            super(context, resource, objects);
            movieModelList=objects;
            this.resource=resource;
            inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView= inflater.inflate(resource, null);
            }
            ImageView ivMovieIcon;
            TextView tvMovie;
            TextView tvTagline;
            TextView tvYear;
            TextView tvDuration;
            TextView tvDirector;
            RatingBar rbMovie;
            TextView tvCast;
            TextView tvStory;

            ivMovieIcon = (ImageView)convertView.findViewById(R.id.ivIcon);
            tvMovie = (TextView)convertView.findViewById(R.id.tvMovie);
            tvTagline = (TextView)convertView.findViewById(R.id.tvTagline);
            tvYear= (TextView)convertView.findViewById(R.id.tvYear);
            tvDuration=(TextView)convertView.findViewById(R.id.tvDuration);
            tvDirector=(TextView)convertView.findViewById(R.id.tvDirector);
            rbMovie = (RatingBar)convertView.findViewById(R.id.rbMovie);
            tvCast = (TextView)convertView.findViewById(R.id.tvCast);
            tvStory=(TextView)convertView.findViewById(R.id.tvStory);

            tvMovie.setText(movieModelList.get(position).getMovie());
            tvTagline.setText(movieModelList.get(position).getTagline());
            tvYear.setText("Year:" + movieModelList.get(position).getYear());
            tvDuration.setText(movieModelList.get(position).getDuration());
            tvDirector.setText(movieModelList.get(position).getDirector());

            // rating bar

            rbMovie.setRating(movieModelList.get(position).getRating()/2);

            StringBuffer stringBuffer = new StringBuffer();
            for(MovieModel.Cast cast : movieModelList.get(position).getCastList()){
                stringBuffer.append(cast.getName() + ",");
            }
            tvCast.setText(stringBuffer.toString());
            tvStory.setText(movieModelList.get(position).getStory());


            return convertView;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            new JSONTask().execute("http://jsonparsing.parseapp.com/jsonData/moviesData.txt");
            return true;
        }

        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_quit) {
           finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
