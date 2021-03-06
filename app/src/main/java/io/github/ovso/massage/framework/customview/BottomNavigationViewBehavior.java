package io.github.ovso.massage.framework.customview;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Created by jaeho on 2017. 10. 18
 */

public class BottomNavigationViewBehavior extends CoordinatorLayout.Behavior<BottomNavigationView> {

  private int height;

  @Override public boolean onLayoutChild(CoordinatorLayout parent, BottomNavigationView child,
      int layoutDirection) {
    height = child.getHeight();
    return super.onLayoutChild(parent, child, layoutDirection);
  }

  @Override public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
      @NonNull BottomNavigationView child, @NonNull View directTargetChild, @NonNull View target,
      int axes, int type) {
    return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
  }

  @Override public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
      @NonNull BottomNavigationView child, @NonNull View target, int dxConsumed, int dyConsumed,
      int dxUnconsumed, int dyUnconsumed, int type) {

    if (dyConsumed > 0) {
      slideDown(child);
    } else if (dyConsumed < 0) {
      slideUp(child);
    }
  }

  private void slideUp(BottomNavigationView child) {
    child.clearAnimation();
    child.animate().translationY(0).setDuration(200);
  }

  private void slideDown(BottomNavigationView child) {
    child.clearAnimation();
    child.animate().translationY(height).setDuration(200);
  }
}