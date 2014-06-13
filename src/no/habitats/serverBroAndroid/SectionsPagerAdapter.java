package no.habitats.serverBroAndroid;

import java.util.Locale;

import no.habitats.serverBroAndroid.R;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

  private Context context;
  private Fragment feedFragment;
  private Fragment computerInfoFragment;

  public SectionsPagerAdapter(Context context, FragmentManager fm, Fragment feedFragment, Fragment computerInfoFragment) {
    super(fm);
    this.context = context;
    this.feedFragment = feedFragment;
    this.computerInfoFragment = computerInfoFragment;
  }

  @Override
  public Fragment getItem(int position) {
    // getItem is called to instantiate the fragment for the given page.
    // Return a PlaceholderFragment (defined as a static inner class below).
    Fragment fragment = null;
    switch (position) {
      case 0:
        fragment = computerInfoFragment;
        break;
      case 1:
        fragment = feedFragment;
        break;
    }
    return fragment;
  }

  @Override
  public int getCount() {
    return 2;
  }

  @Override
  public CharSequence getPageTitle(int position) {
    Locale l = Locale.getDefault();
    switch (position) {
      case 0:
        return context.getString(R.string.sComputerInfo).toUpperCase(l);
      case 1:
        return context.getString(R.string.sFeed).toUpperCase(l);
    }
    return null;
  }
}
