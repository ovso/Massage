package io.github.ovso.massage.framework.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaeho on 2017. 10. 18..
 */

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
  public BaseFragmentPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override public Fragment getItem(int position) {
    return null;
  }

  @Override public int getCount() {
    return 0;
  }

  public class PagerBaseAdapter extends FragmentPagerAdapter
      implements BaseAdapterView, BaseAdapterDataModel<Fragment> {
    private ArrayList<Fragment> items = new ArrayList<>();

    @Override public void destroyItem(ViewGroup container, int position, Object object) {
      //super.destroyItem(container, position, object);
    }

    public PagerBaseAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override public void add(Fragment item) {
      items.add(item);
    }

    @Override public void addAll(List<Fragment> items) {
      this.items.addAll(items);
    }

    @Override public Fragment remove(int position) {
      return items.remove(position);
    }

    @Override public Fragment getItem(int position) {
      return items.get(position);
    }

    @Override public void add(int index, Fragment item) {

    }

    @Override public int getSize() {
      return items.size();
    }

    @Override public void clear() {

    }

    @Override public int getCount() {
      return getSize();
    }

    @Override public void refresh() {
      notifyDataSetChanged();
    }
  }}
