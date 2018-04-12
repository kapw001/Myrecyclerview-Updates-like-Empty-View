package com.myrecyclerview;

import android.content.ClipData;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.customrecyclerview.EmptyRecyclerView;
import com.customrecyclerview.divider.DividerItemDecoration;
import com.customrecyclerview.helper.RecyclerItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

import commonadapter.RecyclerViewAdapter;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private EmptyRecyclerView emptyRecyclerView;
    private RecyclerViewAdapter myAdapeter;
    private List mList;

    private RelativeLayout mRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRoot = (RelativeLayout) findViewById(R.id.mRoot);
        mList = new ArrayList<>();

        myAdapeter = new RecyclerViewAdapter(mList);

        emptyRecyclerView = findViewById(R.id.myView);
        emptyRecyclerView.setLinearLayoutLayout(false);
        emptyRecyclerView.setSwipeRefreshListener(this);
        emptyRecyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider));

        emptyRecyclerView.setAdapter(myAdapeter);

//        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.RIGHT, this);
//
//        emptyRecyclerView.setItemTouchHelper(itemTouchHelperCallback);


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
                    myAdapeter.updateData(new ArrayList<String>());
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

                    List<ModelTest> list = new ArrayList<>();
                    for (int i = 0; i < 100; i++) {

                        ModelTest modelTest = new ModelTest();
                        modelTest.setName("H " + i);
                        list.add(modelTest);
                    }
                    emptyRecyclerView.setSwipeRefreshing(false);
                    mList.clear();
                    mList.addAll(list);
                    myAdapeter.updateData(list);

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


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        if (viewHolder instanceof RecyclerViewAdapter.ViewHolder) {
            // get the removed item name to display it in snack bar
            ModelTest name = (ModelTest) mList.get(viewHolder.getAdapterPosition());

            // backup of removed item for undo purpose
            final ModelTest deletedItem = (ModelTest) mList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            myAdapeter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(mRoot, name.getName() + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    myAdapeter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }

    }
}
