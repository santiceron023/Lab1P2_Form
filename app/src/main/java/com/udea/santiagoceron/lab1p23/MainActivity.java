package com.udea.santiagoceron.lab1p23;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.DatePickerDialog;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

class Person {
    String user, pass, pass2, email;
    boolean sex_male;
    String city;
    boolean movie, sport, dance, read;
}

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public EditText ePass, ePass2, eUser, eMail;
    private RadioButton sFem, sMale;
    private CheckBox sMovie, sSport, sRead, sDance;
    private TextView tShow, tDate;
    private TextView tHobbie;
    private Spinner sCity;
    Person person = new Person();
    String Date;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ePass = (EditText) findViewById(R.id.ePass);
        ePass2 = (EditText) findViewById(R.id.ePass2);
        eUser = (EditText) findViewById(R.id.eUser);
        eMail = (EditText) findViewById(R.id.eEmail);
        sFem = (RadioButton) findViewById(R.id.sFem);
        sMale = (RadioButton) findViewById(R.id.sFem);
        sMovie = (CheckBox) findViewById(R.id.sMovie);
        sSport = (CheckBox) findViewById(R.id.sSport);
        sRead = (CheckBox) findViewById(R.id.sRead);
        sDance = (CheckBox) findViewById(R.id.sDance);
        tHobbie = (TextView) findViewById(R.id.tHobbie);
        tShow = (TextView) findViewById(R.id.tInfo);

        //DATE
        tDate = (TextView) findViewById(R.id.showDate);

        //SPINnER CONF-------------------------

        sCity = (Spinner) findViewById(R.id.sCity);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cities, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sCity.setAdapter(adapter);
        //</SPINERRR
    }

    //<DATE-------------------------
    public void datePicker(View view) {

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(), "date");
    }

    private void setDate(final Calendar calendar) {
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        tDate.setText(dateFormat.format(calendar.getTime()));
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month, day);
        setDate(cal);
    }

    public static class DatePickerFragment extends DialogFragment {
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(),
                    (DatePickerDialog.OnDateSetListener)
                            getActivity(), year, month, day);
        }
    }
    //</date end-------------------------

    public void Register(View view) {

        //all-completed
        if (!All_selected()) return;

        //CHeck Email

        //get data
        person.user = eUser.getText().toString();
        person.pass = ePass.getText().toString();
        person.pass2 = ePass2.getText().toString();

        //compare pass
        if (!(person.pass2.equals(person.pass))) {
            ePass2.setError("Pass did not match");
            return;
        }
        person.email = eMail.getText().toString();
        if (sFem.isChecked()) {
            person.sex_male = false;
        } else {
            person.sex_male = true;
        }

        if (sMovie.isChecked()) {
            person.movie = true;
        } else {
            person.movie = false;
        }

        if (sDance.isChecked()) {
            person.dance = true;
        } else {
            person.dance = false;
        }

        if (sRead.isChecked()) {
            person.read = true;
        } else {
            person.read = false;
        }
        if (sSport.isChecked()) {
            person.sport = true;
        } else {
            person.sport = false;
        }
        person.city = sCity.getSelectedItem().toString();

        //date
        Date = tDate.getText().toString();

        //show
        String all;

        all = "User : \t" + person.user + "\nPassWord : \t" + person.pass;
        all += "\nE-mail: \t" + person.email;

        if (person.sex_male) {
            all += "\nSex : \tMale";
        } else {
            all += "\nSex : \tFemale";
        }

        all += "\nCity Born: \t" + person.city;
        all += "\n Date Born: " + Date;
        all += "\n Hobbies = ";

        if (person.sport) all += "Sports ";
        if (person.read) all += "Read ";
        if (person.dance) all += "Dance ";
        if (person.movie) all += "Movies ";


        tShow.setText(all);

    }

    //ALL SELECTED DEBUG
    public boolean All_selected() {
        String NoWr, NoDate;
        NoWr = "Field can not be empty";
        NoDate = "select Date";

        Boolean empty = false;

        if (ePass.getText().toString().isEmpty()) {
            ePass.setError(NoWr);
            empty = true;
        }
        if (ePass2.getText().toString().isEmpty()) {
            ePass2.setError(NoWr);
            empty = true;
        }
        if (eUser.getText().toString().isEmpty()) {
            eUser.setError(NoWr);
            empty = true;
        }
        if (eMail.getText().toString().isEmpty()) {
            eMail.setError(NoWr);
            empty = true;
        }//date
        //spiner

        //Toco aasi por que sino no funcionaba
        int aux = 0;
        if ((sMovie.isChecked())) aux += 1;
        if ((sDance.isChecked())) aux += 1;
        if ((sRead.isChecked())) aux += 1;
        if ((sSport.isChecked())) aux += 1;
        if (aux == 0) {
            tHobbie.setError(NoWr);
            empty = true;
        } else {
            tHobbie.setError(null);
        }

        //Date
        if (tDate.getText().toString().equals("Born Date")){
            tDate.setError(NoDate);
            empty = true;
        } else{
            tDate.setError(null);
        }

        //return
        if (!empty){
            return true;
        }else{
            return false;
        }
    }


}
