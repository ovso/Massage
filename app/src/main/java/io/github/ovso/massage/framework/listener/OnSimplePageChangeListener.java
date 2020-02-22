package io.github.ovso.massage.framework.listener;

import androidx.viewpager.widget.ViewPager;

public abstract class OnSimplePageChangeListener implements ViewPager.OnPageChangeListener {
  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

  }

  @Override public void onPageSelected(int position) {
    onPageChanged(position);
  }

  @Override public void onPageScrollStateChanged(int state) {

  }

  public abstract void onPageChanged(int position);
}