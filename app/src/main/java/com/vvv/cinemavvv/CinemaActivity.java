
package com.vvv.cinemavvv;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class CinemaActivity extends AppCompatActivity {

    private int id;
    private ImageView imageCinema;
    private TextView dateV;
    private TextView name;
    private TextView description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema);
        if (getIntent() != null) {
            id = getIntent().getIntExtra("ID", 1);
        }
        imageCinema = (ImageView) findViewById(R.id.image_cinema_c);
        dateV = (TextView) findViewById(R.id.date_v);
        name = (TextView) findViewById(R.id.name_c);
        description = (TextView) findViewById(R.id.description);
        loadData();
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadData() {
        Cursor res = (new DBHelper(this)).getCinema(id);
        if (res.getCount() > 0) {
            res.moveToFirst();
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(res.getString(1));
            }
            Glide.with(this)
                    .load(res.getString(2))
                    //.placeholder(R.drawable.no_image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //кэш изображений , что бы грузил без интернета
                    .into(imageCinema);
            name.setText(res.getString(1) + "/" + res.getString(3));
            description.setText(res.getString(5));
            dateV.setText(res.getString(4));
        }
    }
}
