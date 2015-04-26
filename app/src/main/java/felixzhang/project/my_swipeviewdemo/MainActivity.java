package felixzhang.project.my_swipeviewdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import felixzhang.project.my_swipeviewdemo.view.SwipeView;


/**
 *
 */
public class MainActivity extends ActionBarActivity implements SwipeView.OnSwipeStatusChangeListener {


    private static final String TAG = "MainActivity";
    private ListView mListView;
    private MyAdapter mAdapter;
    private static ArrayList<Item> mList = new ArrayList<Item>();

    static {
        for (int i = 0; i < 25; i++) {
            mList.add(new Item("Content-" + i, "Delete-" + i));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new MyAdapter(this, R.layout.list_item, mList);
        mListView.setAdapter(mAdapter);

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (isExistUnClosed()) {
                    closeAllSwipeView();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

    }


    private ArrayList<SwipeView> unClosedSwipeViews = new ArrayList<SwipeView>();

    /**
     * 是否存在未关闭的条目
     */
    private boolean isExistUnClosed() {
        return unClosedSwipeViews.size() != 0;
    }

    @Override
    public void onClose(SwipeView swipeView) {
        unClosedSwipeViews.remove(swipeView);
    }

    @Override
    public void onOpen(SwipeView swipeView) {
        if (!unClosedSwipeViews.contains(swipeView)) {
            unClosedSwipeViews.add(swipeView);
        }
    }

    @Override
    public void onSwiping(SwipeView swipeView) {
        if (!unClosedSwipeViews.contains(swipeView)) {
            closeAllSwipeView();
        }
    }

    private void closeAllSwipeView() {
        for (SwipeView swipeView : unClosedSwipeViews) {
            swipeView.close();
        }
    }


    class MyAdapter extends ArrayAdapter<Item> {
        private Context mContext;
        private int mResource;
        private List<Item> lists;

        public MyAdapter(Context context, int resource, List<Item> objects) {
            super(context, resource, objects);
            mResource = resource;
            lists = objects;
            mContext = context;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(mContext, mResource, null);
            }

            holder = getViewHolder(convertView);
            holder.swipeView.setOnSwipeStatusChangeListener(MainActivity.this);

            final Item item = lists.get(position);
            holder.contentView.setText(item.content);
            holder.deleteView.setText(item.delete);

            holder.deleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lists.remove(position);
                    notifyDataSetChanged();
                }
            });

            holder.contentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isExistUnClosed()) {
                        closeAllSwipeView();
                    } else {      //只有此时才contentview才可被点击
                        Toast.makeText(MainActivity.this, item.content, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            return convertView;
        }

        private ViewHolder getViewHolder(View convertView) {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            if (holder == null) {
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            return holder;
        }

        private ViewHolder holder;

        class ViewHolder {
            TextView contentView, deleteView;
            SwipeView swipeView;

            public ViewHolder(View convertView) {
                contentView = (TextView) convertView.findViewById(R.id.content);
                deleteView = (TextView) convertView.findViewById(R.id.delete);
                swipeView = (SwipeView) convertView.findViewById(R.id.swipeview);
            }


        }
    }


}
