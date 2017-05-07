package momocorp.partybus.CustomObjects;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.widget.DatePicker;

/**
 * Created by Pablo on 3/20/2017.
 */

public class DatePickerFragment extends
        DialogFragment implements
        DatePickerDialog.OnDateSetListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {



    }
}
