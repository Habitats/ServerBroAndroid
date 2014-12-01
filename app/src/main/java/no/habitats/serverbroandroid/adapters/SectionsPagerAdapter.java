package no.habitats.serverbroandroid.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

  private List<FragmentHolder> fragments;

  public SectionsPagerAdapter(FragmentManager fm) {
    super(fm);
    fragments = new ArrayList<FragmentHolder>();
  }

  public void addFragment(Fragment fragment, String title) {
    fragments.add(new FragmentHolder(fragment, title));
  }

  private class FragmentHolder {
    public final String title;
    public final Fragment fragment;

    public FragmentHolder(Fragment fragment, String title) {
      this.fragment = fragment;
      this.title = title;
    }
  }

  @Override
  public Fragment getItem(int position) {
    return fragments.get(position).fragment;
  }

  @Override
  public int getCount() {
    return fragments.size();
  }

  @Override
  public CharSequence getPageTitle(int position) {
    Locale l = Locale.getDefault();
    return fragments.get(position).title.toUpperCase(l);
  }
}
