package felixzhang.project.my_swipeviewdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {


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
            Item item = lists.get(position);
            holder.contentView.setText(item.content);
            holder.deleteView.setText(item.delete);

            holder.deleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lists.remove(position);
                    notifyDataSetChanged();
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

            public ViewHolder(View convertView) {
                contentView = (TextView) convertView.findViewById(R.id.content);
                deleteView = (TextView) convertView.findViewById(R.id.delete);
            }


        }
    }


}
