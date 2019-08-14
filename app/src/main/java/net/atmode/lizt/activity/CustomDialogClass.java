package net.atmode.lizt.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import net.atmode.lizt.R;

public class CustomDialogClass extends Dialog implements android.view.View.OnClickListener {

    private Button save;
    private Button cancel;
    private EditText name;
    OnMyDialogResult mDialogResult;

    public CustomDialogClass(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.custom_dialog);

        name = findViewById(R.id.nameInput);
        save = findViewById(R.id.btnSave);
        cancel = findViewById(R.id.btnCancel);
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    public void setValue (String text) {
        name.setText(text);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                if (mDialogResult != null) {
                    mDialogResult.finish(String.valueOf(name.getText()));
                }
                CustomDialogClass.this.dismiss();
                break;
            case R.id.btnCancel:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

    public void setmDialogResult(OnMyDialogResult mDialogResult) {
        this.mDialogResult = mDialogResult;
    }

    public interface OnMyDialogResult {
        void finish(String result);
    }

    public void showDialog(String text) {
        this.show();
        name.setText(text);
    }
}
