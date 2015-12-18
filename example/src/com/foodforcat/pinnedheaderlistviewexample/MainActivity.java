package com.foodforcat.pinnedheaderlistviewexample;

import com.foodforcat.R;
import com.foodforcat.pinnedheaderlistview.PinnedHeaderListView;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private boolean isScroll = true;
	private MyAdapter adapter;
	private ListView left_listView;

	private String[] leftStr = new String[] { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日", "星期1", "星期2", "星期3",
			"星期4", "星期5", "星期6", "星期7", "星期8", "星期9", "星期0", "星期01", "星期02" };
	private boolean[] flagArray = { true, false, false, false, false, false, false, false, false, false, false, false,
			false, false, false, false, false, false, false };
	private String[][] rightStr = new String[][] { { "星期一  早餐", "星期一  午餐", "星期一  晚餐" },
			{ "星期二  早餐", "星期二  午餐", "星期二  晚餐" }, { "星期三  早餐", "星期三  午餐", "星期三  晚餐" },
			{ "星期四  早餐", "星期四  午餐", "星期四  晚餐" }, { "星期五  早餐", "星期五  午餐", "星期五  晚餐" },
			{ "星期六  早餐", "星期六  午餐", "星期六  晚餐" }, { "星期日  早餐", "星期日  午餐", "星期日  晚餐" },
			{ "星期1  早餐", "星期日  午餐", "星期日  晚餐" }, { "星期2  早餐", "星期日  午餐", "星期日  晚餐" },
			{ "星期3  早餐", "星期日  午餐", "星期日  晚餐" }, { "星期4  早餐", "星期日  午餐", "星期日  晚餐" },
			{ "星期5  早餐", "星期日  午餐", "星期日  晚餐" }, { "星期6  早餐", "星期日  午餐", "星期日  晚餐" },
			{ "星期7  早餐", "星期日  午餐", "星期日  晚餐" }, { "星期8  早餐", "星期日  午餐", "星期日  晚餐" },
			{ "星期9  早餐", "星期日  午餐", "星期日  晚餐" }, { "星期0  早餐", "星期日  午餐", "星期日  晚餐" },
			{ "星期01  早餐", "星期日  午餐", "星期日  晚餐" }, { "星期02  早餐", "星期日  午餐", "星期日  晚餐" } };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final PinnedHeaderListView right_listview = (PinnedHeaderListView) findViewById(R.id.pinnedListView);
		final TestSectionedAdapter sectionedAdapter = new TestSectionedAdapter(this, leftStr, rightStr);
		right_listview.setAdapter(sectionedAdapter);

		left_listView = (ListView) findViewById(R.id.left_listview);
		adapter = new MyAdapter();
		left_listView.setAdapter(adapter);
		left_listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				isScroll = false;

				for (int i = 0; i < leftStr.length; i++) {
					if (i == position) {
						flagArray[i] = true;
					} else {
						flagArray[i] = false;
					}
				}
				adapter.notifyDataSetChanged();
				int rightSection = 0;
				for (int i = 0; i < position; i++) {
					rightSection += sectionedAdapter.getCountForSection(i) + 1;
				}
				right_listview.setSelection(rightSection);

			}

		});

		right_listview.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView arg0, int scrollState) {
				switch (scrollState) {
				// 当不滚动时
				case OnScrollListener.SCROLL_STATE_IDLE:
					// 判断滚动到底部
					if (right_listview.getLastVisiblePosition() == (right_listview.getCount() - 1)) {
						left_listView.setSelection(ListView.FOCUS_DOWN);
					}

					// 判断滚动到顶部
					if (right_listview.getFirstVisiblePosition() == 0) {
					}

					break;
				}
			}

			int y = 0;
			int x = 0;
			int z = 0;

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (isScroll) {
					for (int i = 0; i < rightStr.length; i++) {
						if (i == sectionedAdapter.getSectionForPosition(right_listview.getFirstVisiblePosition())) {
							flagArray[i] = true;
							x = i;
						} else {
							flagArray[i] = false;
						}
					}
					if (x != y) {
						adapter.notifyDataSetChanged();
						y = x;
						if (y == left_listView.getLastVisiblePosition()) {
							z = z + 3;
							left_listView.setSelection(z);
						}
						if (x == left_listView.getFirstVisiblePosition()) {
							z = z - 1;
							left_listView.setSelection(z);
						}
						if (firstVisibleItem + visibleItemCount == totalItemCount - 1) {
							left_listView.setSelection(ListView.FOCUS_DOWN);
						}
					}
				} else {
					isScroll = true;
				}
			}
		});

	}

	public class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return leftStr.length;
		}

		@Override
		public Object getItem(int arg0) {
			return leftStr[arg0];
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			Holder holder = null;
			if (arg1 == null) {
				holder = new Holder();
				arg1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.left_list_item, null);
				holder.left_list_item = (TextView) arg1.findViewById(R.id.left_list_item);
				arg1.setTag(holder);
			} else {
				holder = (Holder) arg1.getTag();
			}
			holder.updataView(arg0);
			return arg1;
		}

		private class Holder {
			private TextView left_list_item;

			public void updataView(final int position) {
				left_list_item.setText(leftStr[position]);
				if (flagArray[position]) {
					left_list_item.setBackgroundColor(Color.rgb(255, 255, 255));
				} else {
					left_list_item.setBackgroundColor(Color.TRANSPARENT);
				}
			}

		}
	}
}
