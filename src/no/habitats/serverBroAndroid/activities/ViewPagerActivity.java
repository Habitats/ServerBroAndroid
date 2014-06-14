package no.habitats.serverBroAndroid.activities;

import java.util.Observable;
import java.util.Observer;

import no.habitats.serverBroAndroid.GuiControllerAndroid;
import no.habitats.serverBroAndroid.R;
import no.habitats.serverBroAndroid.adapters.SectionsPagerAdapter;
import serverBro.broClient.ClientController;
import serverBro.broShared.BroModel;
import serverBro.broShared.events.internal.ComputerInfoButtonEvent;
import serverBro.broShared.events.internal.ConnectButtonEvent;
import serverBro.broShared.events.internal.DisconnectButtonEvent;
import serverBro.broShared.misc.Config;
import serverBro.broShared.view.BroGuiController;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class ViewPagerActivity extends ActionBarActivity implements ActionBar.TabListener, Observer {

  private ClientController clientController;

  private SectionsPagerAdapter mSectionsPagerAdapter;
  private ViewPager mViewPager;

  private Button bRequest;
  private Button bDisconnect;
  private Button bConnect;
  private TextView tvStatus;

  private Fragment feedFragment;
  private Fragment computerInfoFragment;
  private Fragment processesFragment;

  private Context context;
  private Activity a;

  protected boolean continiousUpdates;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    setContentView(R.layout.view_pager_activity);

    // Set up the action bar.
    final ActionBar actionBar = getSupportActionBar();
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

    feedFragment = new FeedFragment();
    computerInfoFragment = new ComputerInfoFragment();
    processesFragment = new ProcessesFragment();

    // Create the adapter that will return a fragment for each of the three
    // primary sections of the activity.
    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
    mSectionsPagerAdapter.addFragment(computerInfoFragment, getString(R.string.sComputerInfo));
    mSectionsPagerAdapter.addFragment(processesFragment, getString(R.string.sProcesses));
    mSectionsPagerAdapter.addFragment(feedFragment, getString(R.string.sFeed));

    // Set up the ViewPager with the sections adapter.
    mViewPager = (ViewPager) findViewById(R.id.pager);
    mViewPager.setAdapter(mSectionsPagerAdapter);
    mViewPager.setOffscreenPageLimit(mSectionsPagerAdapter.getCount());

    // When swiping between different sections, select the corresponding
    // tab. We can also use ActionBar.Tab#select() to do this if we have
    // a reference to the Tab.
    mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
      @Override
      public void onPageSelected(int position) {
        actionBar.setSelectedNavigationItem(position);
      }
    });

    for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
      actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
    }
    initializeComponents();
    initializeController();
    clientController.startService();
    // initiateContiniousStatusUpdate(this);
  }

  // @Override
  // public boolean onCreateOptionsMenu(Menu menu) {
  // getMenuInflater().inflate(R.menu.main, menu);
  // return true;
  // }
  //
  // @Override
  // public boolean onOptionsItemSelected(MenuItem item) {
  // int id = item.getItemId();
  // if (id == R.id.action_settings) {
  // return true;
  // }
  // return super.onOptionsItemSelected(item);
  // }

  @Override
  public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    mViewPager.setCurrentItem(tab.getPosition());
  }

  @Override
  public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}

  @Override
  public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}



  private void initializeController() {

    BroGuiController guiController = new GuiControllerAndroid();
    clientController = new ClientController(guiController);
    ((GuiControllerAndroid) guiController).addObserver((Observer) feedFragment);
    ((GuiControllerAndroid) guiController).addObserver((Observer) computerInfoFragment);
    ((GuiControllerAndroid) guiController).addObserver((Observer) processesFragment);
    ((GuiControllerAndroid) guiController).addObserver(this);
  }


  private void initializeComponents() {
    bConnect = (Button) findViewById(R.id.buttonConnect);
    bDisconnect = (Button) findViewById(R.id.buttonDisconnect);
    bRequest = (Button) findViewById(R.id.buttonRequest);

    tvStatus = (TextView) findViewById(R.id.tvStatus);

    bConnect.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        clientController.actionPerformed(new ConnectButtonEvent());
      }
    });

    bDisconnect.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        clientController.actionPerformed(new DisconnectButtonEvent());
      }
    });

    bRequest.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        // clientController.actionPerformed(new MessageButtonEvent());
        clientController.actionPerformed(new ComputerInfoButtonEvent());
        setProgressBarIndeterminateVisibility(true);
      }
    });
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    clientController.stopService();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    clientController.stopService();
  }

  @Override
  public void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    clientController.stopService();
  }

  @Override
  public void update(final Observable observable, Object data) {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        // tvStatus.setText(Config.getInstance().getNetworkStatus());
        tvStatus.setText(((BroModel) observable).getLastMessage());
        setProgressBarIndeterminateVisibility(false);
      }
    });
  }

  public void initiateContiniousStatusUpdate(final Activity a) {
    this.a = a;
    new Thread(new Runnable() {

      @Override
      public void run() {
        while (true) {
          if (continiousUpdates) {
            a.runOnUiThread(new Runnable() {

              @Override
              public void run() {
                tvStatus.setText(Config.getInstance().getNetworkStatus());
              }
            });
            try {
              Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
          }
        }
      }
    }).start();
  }
}
