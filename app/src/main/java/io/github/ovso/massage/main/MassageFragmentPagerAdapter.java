package io.github.ovso.massage.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import io.github.ovso.massage.framework.adapter.BaseAdapterDataModel;
import io.github.ovso.massage.framework.adapter.BaseAdapterView;
import io.github.ovso.massage.massage.MassageFragment;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by jaeho on 2017. 10. 20
 */

public class MassageFragmentPagerAdapter extends FragmentStatePagerAdapter
    implements BaseAdapterView, BaseAdapterDataModel<Fragment> {

  private ArrayList<Fragment> items = new ArrayList<>();

  @Inject public MassageFragmentPagerAdapter(FragmentManager fm) {
    super(fm);
    items.add(MassageFragment.newInstance());
    items.add(MassageFragment.newInstance());
    items.add(MassageFragment.newInstance());
    items.add(MassageFragment.newInstance());
    items.add(MassageFragment.newInstance());
  }

  @Override public void add(Fragment item) {
    items.add(item);
  }

  @Override public void addAll(List<Fragment> items) {
    items.addAll(items);
  }

  @Override public Fragment remove(int position) {
    return items.remove(position);
  }

  @Override public Fragment getItem(int position) {
    return items.get(position);
  }

  @Override public void add(int index, Fragment item) {
    items.add(index, item);
  }

  @Override public int getSize() {
    return items.size();
  }

  @Override public void clear() {
    items.clear();
  }

  @Override public int getCount() {
    return getSize();
  }

  @Override public void refresh() {
    notifyDataSetChanged();
  }
}
