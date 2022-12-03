package com.sismics.docs.ui;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.NonNull;
import com.google.android.material.appbar.AppBarLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;

import com.sismics.docs.R;

public class ScrollingFABBehavior extends CoordinatorLayout.Behavior<FloatingActionButton> {
    private int toolbarHeight;

    public ScrollingFABBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.toolbarHeight = getToolbarHeight(context);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull FloatingActionButton fab, @NonNull View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull FloatingActionButton fab, @NonNull View dependency) {
        if (dependency instanceof AppBarLayout) {
                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
                int fabBottomMargin = lp.bottomMargin;
                int distanceToScroll = fab.getHeight() + fabBottomMargin;
                float ratio = dependency.getY() /(float) toolbarHeight;
                fab.setTranslationY(- distanceToScroll * ratio);
        }
        return true;
    }

    private int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[] { R.attr.actionBarSize });
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }
}