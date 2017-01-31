package com.vvv.cinemavvv;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.vvv.cinemavvv.api.API;
import com.vvv.cinemavvv.api.RetrofitProvider;
import com.vvv.cinemavvv.api.model.ListCinema;
import com.vvv.cinemavvv.listcinema.CinemaAdapter;
import com.vvv.cinemavvv.listcinema.CinemaItem;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;

    private static final String TAG = "TAB_LOG";

    private CinemaAdapter cinemaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);

        //тут мы нащ список инициализируем
        RecyclerView recyclerCity = (RecyclerView) findViewById(R.id.recycler_cinema);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerCity.setLayoutManager(llm);
        cinemaAdapter = new CinemaAdapter(this, callBack);
        recyclerCity.setAdapter(cinemaAdapter);
        recyclerCity.setItemAnimator(new DefaultItemAnimator());

        loadInternet();
    }

    private void loadLocal() {
        Cursor res = dbHelper.getAllCinema();
        if (res.getCount() > 0) {
            while (res.moveToNext()) {
                cinemaAdapter.addItem(new CinemaItem(
                        res.getInt(0),
                        res.getString(2),
                        res.getString(1),
                        res.getString(3),
                        res.getString(4),
                        res.getString(5)));
            }
        }
        cinemaAdapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(), R.string.no_eth, Toast.LENGTH_SHORT).show();
    }

    private void loadInternet() {
        API api = RetrofitProvider.retrofit(this).create(API.class);
        api.getListCinema()
                .subscribeOn(Schedulers.newThread()) //  выполняем в новом потоке
                .observeOn(AndroidSchedulers.mainThread()) // результат вернет в UI поток
                .map(ListCinema::getList) //это извлекаем из ответа только список данных
                .flatMap(Observable::from) //
                .distinct(ListCinema.ListData::getNameEng)
                .toList()
                .subscribe(response -> {
                    for (ListCinema.ListData listData : response) {
                        long temp = dbHelper.addCinema(listData.getImage(), listData.getName(), listData.getNameEng(), listData.getPremiere(), listData.getDescription()); //добавляем в БД, т.к сразу получаем всю инфу. то записываем ее всю
                        Log.d("TAB_LOG", "id" + temp);
                        cinemaAdapter.addItem(new CinemaItem((int) temp, listData.getImage(), listData.getName(), listData.getNameEng(), listData.getPremiere(), listData.getDescription())); //добавляем в адаптер
                        cinemaAdapter.notifyDataSetChanged(); //показываем изминения
                    }
                }, throwable -> loadLocal()); // это будет если не ответит сервер либо будет какая-то ошибка. будем грузить тогда все из кэша
    }

    CinemaAdapter.CallBack callBack = (position, cinemaItem) -> {
        Intent intent = new Intent(MainActivity.this, CinemaActivity.class);
        intent.putExtra("ID", cinemaItem.id);
        startActivity(intent);
    };
}
