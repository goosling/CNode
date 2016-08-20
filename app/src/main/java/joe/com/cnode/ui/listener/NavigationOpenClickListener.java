package joe.com.cnode.ui.listener;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

/**
 * Created by JOE on 2016/8/19.
 */
public class NavigationOpenClickListener implements View.OnClickListener {

    private final DrawerLayout drawerLayout;

    public NavigationOpenClickListener(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }

    @Override
    public void onClick(View view) {
        drawerLayout.openDrawer(GravityCompat.START);
    }
}
