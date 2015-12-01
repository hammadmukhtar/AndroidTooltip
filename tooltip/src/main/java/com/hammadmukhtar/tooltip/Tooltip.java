package com.hammadmukhtar.tooltip;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class Tooltip implements View.OnClickListener {

    private static final int MSG_DISMISS_TOOLTIP = 100;
    private Context ctx;
    private PopupWindow tipWindow;
    private View contentView;
    private LayoutInflater inflater;

    public Tooltip(Context ctx) {
        this.ctx = ctx;
        tipWindow = new PopupWindow(ctx);

        inflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.tooltip_layout, null);
    }

    public void showToolTip(final View anchor, final String tip) {

        int viewLocation[] = new int[2];
        anchor.getLocationOnScreen(viewLocation);

        /*Display display = anchor.getRootView().getDisplay();

        int width = display.getWidth();

        int height = display.getHeight();*/

        int width = anchor.getRootView().getRight();

        int height = anchor.getRootView().getBottom();
        ;

        DisplayMetrics displayMetrics = ctx.getResources().getDisplayMetrics();

        int dpHeight = (int) (displayMetrics.heightPixels / displayMetrics.density);
        int dpWidth = (int) (displayMetrics.widthPixels / displayMetrics.density);

        viewLocation[0] = (int) ((viewLocation[0] * dpWidth) / width);

        viewLocation[1] = (int) ((viewLocation[1] * dpHeight) / height);



        /*ViewGroup.LayoutParams params = (LinearLayout.LayoutParams) iv.getLayoutParams();*/

        int tipPosition = dpWidth - anchor.getWidth() / 2;

        if (viewLocation[1] > dpHeight / 2)
            contentView = inflater.inflate(R.layout.tooltip_layout_down, null);
        tipWindow.setHeight(LayoutParams.WRAP_CONTENT);
        tipWindow.setWidth(LayoutParams.WRAP_CONTENT);

        tipWindow.setOutsideTouchable(true);
        tipWindow.setFocusable(true);
        tipWindow.update();
        tipWindow.setFocusable(true);

        ImageView tipCenter = (ImageView) (contentView.findViewById(R.id.tooltip_nav_center));
        ImageView tipRight = (ImageView) (contentView.findViewById(R.id.tooltip_nav_right));
        ImageView tipLeft = (ImageView) (contentView.findViewById(R.id.tooltip_nav_left));
        ViewGroup.MarginLayoutParams s = (ViewGroup.MarginLayoutParams) tipCenter.getLayoutParams();

        if (viewLocation[0] < dpWidth / 2) {
            s = (ViewGroup.MarginLayoutParams) tipLeft.getLayoutParams();
            tipPosition = anchor.getMeasuredWidth() / 10;/*viewLocation[0] + 15;*/
            s.setMargins(tipPosition, 0, 0, 0);
            tipCenter.setVisibility(View.GONE);
            tipRight.setVisibility(View.GONE);
            tipLeft.setVisibility(View.INVISIBLE);
            tipLeft.setLayoutParams(s);

        } else if (viewLocation[0] > dpWidth / 2) {
            s = (ViewGroup.MarginLayoutParams) tipRight.getLayoutParams();
            tipPosition = anchor.getMeasuredWidth() / 10;
            s.setMargins(0, 0, tipPosition, 0);
            tipCenter.setVisibility(View.GONE);
            tipRight.setVisibility(View.INVISIBLE);
            tipLeft.setVisibility(View.GONE);
            tipRight.setLayoutParams(s);
        } else {

            s = (ViewGroup.MarginLayoutParams) tipCenter.getLayoutParams();
            tipPosition = anchor.getMeasuredWidth() / 10;
            s.setMargins(0, 0, 0, 0);
            tipCenter.setVisibility(View.INVISIBLE);
            tipRight.setVisibility(View.GONE);
            tipLeft.setVisibility(View.GONE);
            tipCenter.setLayoutParams(s);

        }

        //tipCenter.setLayoutParams(s);
        //params.setMargins(0, 0, tipPosition , 0);
        //iv.setLayoutParams(params);
        final TextView tv1 = (TextView) (contentView.findViewById(R.id.tooltip_text));
        tv1.setText(tip);
        /*tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        tipWindow.setBackgroundDrawable(new BitmapDrawable());

        tipWindow.setContentView(contentView);

        int screen_pos[] = new int[2];
// Get location of anchor view on screen
        anchor.getLocationOnScreen(screen_pos);

// Get rect for anchor view
        Rect anchor_rect = new Rect(screen_pos[0], screen_pos[1], screen_pos[0]
                + anchor.getWidth(), screen_pos[1] + anchor.getHeight());

// Call view measure to calculate how big your view should be.
        contentView.measure(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);

        int contentViewHeight = contentView.getMeasuredHeight();
        int contentViewWidth = contentView.getMeasuredWidth();

// In this case , i dont need much calculation for x and y position of
// tooltip
// For cases if anchor is near screen border, you need to take care of
// direction as well
// to show left, right, above or below of anchor view
        int position_x = anchor_rect.centerX() - (contentViewWidth / 2);
        int position_y = anchor_rect.bottom - (anchor_rect.height() / 2);

        if (viewLocation[1] > dpHeight / 2) {
            position_x = anchor_rect.centerX() - (contentViewWidth / 2);
            position_y = anchor_rect.bottom - (anchor_rect.height() + (anchor_rect.height() / 2) + (anchor_rect.height() / 3));
        }

        tipWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, position_x,
                position_y);

// send message to handler to dismiss tipWindow after X milliseconds
        handler.sendEmptyMessageDelayed(MSG_DISMISS_TOOLTIP, 4000);
    }

    public boolean isTooltipShown() {
        if (tipWindow != null && tipWindow.isShowing())
            return true;
        return false;
    }

    public void dismissTooltip() {
        if (tipWindow != null && tipWindow.isShowing())
            tipWindow.dismiss();
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_DISMISS_TOOLTIP:
                    if (tipWindow != null && tipWindow.isShowing())
                        tipWindow.dismiss();
                    break;
            }
        }

        ;
    };

    @Override
    public void onClick(View v) {

    }
}