package com.rncomponents.MSBase;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rncomponents.R;

public class ToastTools {
    final static String TAG = "ToastTools";
    private static String oldMsg;
    protected static Toast s_toast   = null;
    private static long oneTime=0;
    private static long twoTime=0;
    private static int[] icon = {R.drawable.success,R.drawable.wrong,R.drawable.warn,R.drawable.wifi};

    public static void showToast (Context context,int type,String content) {//  0 成功；1 失败 ；2 警告 ； 3 网络问题
        Toast	toast =  new Toast(context);
        toast.setGravity(Gravity.CENTER , 0, 0);
        View layout = LayoutInflater.from(context).inflate(R.layout.custom_toast_view,null);
        ImageView iv= (ImageView)layout.findViewById(R.id.custom_toast_iv);
        TextView tv= (TextView)layout.findViewById(R.id.custom_toast_tv);
        iv.setImageResource(icon[type]);
        switch (type) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                tv.setText("网络无法连接");
                break;

            default:
                break;
        }
        if (content!=null) {
            tv.setText(content);
        }
        toast.setView(layout);
        toast.show();

    }

    public static void cancelToast () {
        if(null != s_toast) {
            s_toast.cancel();
        }
        s_toast = null;
    }

    public static Dialog showLoading(Context context,String content){
        if(null == context)
            return null;

        View layout = LayoutInflater.from(context).inflate(R.layout.custom_loading_view,null);
        ImageView iv= (ImageView)layout.findViewById(R.id.custom_toast_iv);
        TextView tv= (TextView)layout.findViewById(R.id.custom_toast_tv);
        final AnimationDrawable loadingDw = ((AnimationDrawable) iv
                .getBackground());
        // loadingDw.start();
        iv.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                loadingDw.start();
                return true; // 必须要有这个true返回
            }
        });
        if (content!=null) {
            tv.setText(content);
        }
        Dialog dialog = new Dialog(context,R.style.mydialog);
        dialog.setContentView(layout);
        dialog.setCanceledOnTouchOutside(false);

      return dialog;
    }


}
