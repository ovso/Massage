package io.github.ovso.massage.main.navigation;

import android.view.View;
import io.github.ovso.massage.R;
import io.github.ovso.massage.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.massage.framework.adapter.BaseAdapterView;
import io.github.ovso.massage.framework.adapter.BaseRecyclerAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaeho on 2017. 10. 25
 */

public class NaviAdapter extends BaseRecyclerAdapter
    implements BaseAdapterView, BaseAdapterDataModel<NaviMenuItem> {
  private static volatile NaviAdapter singletonInstance = null;
  private OnNaviItemSelectedListener onNaviItemSelectedListener;

  private NaviAdapter(OnNaviItemSelectedListener listener) {
    this.onNaviItemSelectedListener = listener;
    items.add(new NaviMenuItem("사랑해", 0, R.drawable.ic_menu_camera));
    items.add(new NaviMenuItem("사랑해", 0, R.drawable.ic_menu_camera));
    items.add(new NaviMenuItem("사랑해", 0, R.drawable.ic_menu_camera));
    items.add(new NaviMenuItem("사랑해", 0, R.drawable.ic_menu_camera));
    items.add(new NaviMenuItem("사랑해", 0, R.drawable.ic_menu_camera));
    items.add(new NaviMenuItem("사랑해", 0, R.drawable.ic_menu_camera));
    items.add(new NaviMenuItem("사랑해", 0, R.drawable.ic_menu_camera));
    items.add(new NaviMenuItem("사랑해", 0, R.drawable.ic_menu_camera));
    items.add(new NaviMenuItem("사랑해", 0, R.drawable.ic_menu_camera));
    items.add(new NaviMenuItem("사랑해", 0, R.drawable.ic_menu_camera));
    items.add(new NaviMenuItem("사랑해", 0, R.drawable.ic_menu_camera));
    items.add(new NaviMenuItem("사랑해", 0, R.drawable.ic_menu_camera));
    items.add(new NaviMenuItem("사랑해", 0, R.drawable.ic_menu_camera));
    items.add(new NaviMenuItem("사랑해", 0, R.drawable.ic_menu_camera));
  }

  public static NaviAdapter getInstance(OnNaviItemSelectedListener listener) {
    if (singletonInstance == null) {
      synchronized (NaviAdapter.class) {
        if (singletonInstance == null) {
          singletonInstance = new NaviAdapter(listener);
        }
      }
    }
    return singletonInstance;
  }

  private ArrayList<NaviMenuItem> items = new ArrayList<>();

  @Override protected BaseViewHolder createViewHolder(View view, int viewType) {
    return new NaviViewHolder(view);
  }

  @Override public int getLayoutRes(int viewType) {
    return android.R.layout.simple_list_item_multiple_choice;
  }

  @Override public void onBindViewHolder(BaseViewHolder viewHolder, int position) {
    if (viewHolder instanceof NaviViewHolder) {
      NaviViewHolder holder = (NaviViewHolder) viewHolder;
      holder.checkedTextView.setChecked(true);
      holder.checkedTextView.setText("I Love you!");
      holder.onNaviItemSelectedListener = onNaviItemSelectedListener;
      holder.naviMenuItem = items.get(position);
    }
  }

  @Override public int getItemCount() {
    return getSize();
  }

  @Override public void refresh() {
    notifyDataSetChanged();
  }

  @Override public void add(NaviMenuItem item) {
    this.items.add(item);
  }

  @Override public void addAll(List<NaviMenuItem> items) {
    this.items.addAll(items);
  }

  @Override public NaviMenuItem remove(int position) {
    return items.remove(position);
  }

  @Override public NaviMenuItem getItem(int position) {
    return items.get(position);
  }

  @Override public void add(int index, NaviMenuItem item) {
    items.add(index, item);
  }

  @Override public int getSize() {
    return items.size();
  }

  @Override public void clear() {
    items.clear();
  }
}
