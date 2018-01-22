package com.myrecyclerview;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myownrecyclerview.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private EmptyRecyclerView emptyRecyclerView;
    private MyAdapeter myAdapeter;
    private List<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList = new ArrayList<>();

        myAdapeter = new MyAdapeter(mList);

        emptyRecyclerView = findViewById(R.id.myView);
        emptyRecyclerView.setLinearLayoutLayout(false);
        emptyRecyclerView.setSwipeRefreshListener(this);
        emptyRecyclerView.setDivider(DividerItemDecoration.VERTICAL);

        emptyRecyclerView.setAdapter(myAdapeter);

//        emptyRecyclerView.setSwipeRefreshEnabled(false);
        emptyRecyclerView.setRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadData();
            }
        });
        LoadDataEmpty();
    }

    private void LoadDataEmpty() {

        if (emptyRecyclerView.getNetworkstats()) {

            Toast.makeText(this, "Internet", Toast.LENGTH_SHORT).show();

            emptyRecyclerView.setLoading();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    emptyRecyclerView.setSwipeRefreshing(false);
                    myAdapeter.update(new ArrayList<String>());
                }
            }, 3000);


        } else {
            emptyRecyclerView.setNoInternetErrorMsg();
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();

        }


    }

    private void LoadData() {
        if (emptyRecyclerView.getNetworkstats()) {

            emptyRecyclerView.setLoading();
            Toast.makeText(this, "Internet", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < 100; i++) {
                        list.add("H " + i);
                    }
                    emptyRecyclerView.setSwipeRefreshing(false);
                    myAdapeter.update(list);

                }
            }, 3000);

        } else {


            emptyRecyclerView.setNoInternetErrorMsg();
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();


        }


    }

    @Override
    public void onRefresh() {
        LoadDataEmpty();
    }


    class MyAdapeter extends RecyclerView.Adapter<MyAdapeter.ViewHolder> {

        private List<String> list;

        public MyAdapeter(List<String> list) {
            this.list = list;
        }

        public void update(List<String> mList) {
            list = new ArrayList<>();
            list.addAll(mList);
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            String n = list.get(position);

            holder.name.setText(n);

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView name;

            public ViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.name);
            }
        }
    }
}
