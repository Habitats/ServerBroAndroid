package no.habitats.serverbroandroid;

import serverBro.broShared.view.LogView;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class AndroidLogView implements LogView {

  private TextView viewFeed;
  private Fragment feedFragment;
  private boolean autoScroll;

  public AndroidLogView(TextView viewFeed, Fragment feedFragment) {
    this.viewFeed = viewFeed;
    this.feedFragment = feedFragment;
    autoScroll = true;
    initTextView();
  }

  private void initTextView() {
    viewFeed.setMovementMethod(new ScrollingMovementMethod());
  }

  @Override
  public void add(final String log) {

    if (feedFragment.getActivity() != null) {
      feedFragment.getActivity().runOnUiThread(new Runnable() {


        @Override
        public void run() {
          viewFeed.append(log + "\n");

          if (autoScroll) {
            // auto-scroll
            final int scrollAmount = viewFeed.getLayout().getLineTop(viewFeed.getLineCount()) - viewFeed.getHeight();
            // if there is no need to scroll, scrollAmount will be <=0
            if (scrollAmount > 0)
              viewFeed.scrollTo(0, scrollAmount);
            else
              viewFeed.scrollTo(0, 0);
          }
        }
      });
    }
  }
}
