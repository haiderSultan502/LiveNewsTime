package app.webscare.livenewstime.otherClasses;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import app.webscare.livenewstime.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SweetAlertDialogGeneral {

    SweetAlertDialog sweetDialog;
    Context context;
    public SweetAlertDialogGeneral(Context context)
    {
        this.context=context;
    }

    public SweetAlertDialog showSweetAlertDialog(String setDialogType,String setText)
    {
        if(setDialogType.equals("success"))
        {
            sweetDialog = new SweetAlertDialog(context,SweetAlertDialog.SUCCESS_TYPE);
            sweetDialogDismiss();
        }
        else if(setDialogType.equals("warning"))
        {
            sweetDialog = new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE);
            sweetDialogDismiss();
        }
        else if(setDialogType.equals("error"))
        {
            sweetDialog = new SweetAlertDialog(context,SweetAlertDialog.ERROR_TYPE);
            sweetDialogDismiss();
        }
        else if(setDialogType.equals("progress"))
        {
            sweetDialog = new SweetAlertDialog(context,SweetAlertDialog.PROGRESS_TYPE);

        }

        sweetDialog.setTitleText(setText);
//        sweetDialog.setContentText("You clicked the button!");
        sweetDialog.setCanceledOnTouchOutside(true);
        sweetDialog.show();

        Button btn = (Button) sweetDialog.findViewById(R.id.confirm_button);
        sweetDialog.getWindow().setLayout(800,WindowManager.LayoutParams.WRAP_CONTENT);
        btn.setVisibility(View.VISIBLE);

        return sweetDialog;
    }

    public void sweetDialogDismiss()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                sweetDialog.dismiss();
            }
        },3000);
    }
}
