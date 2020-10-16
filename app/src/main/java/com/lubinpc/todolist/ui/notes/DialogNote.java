package com.lubinpc.todolist.ui.notes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.lubinpc.todolist.R;
import com.lubinpc.todolist.databinding.DialogNoteBinding;
import com.lubinpc.todolist.models.NoteVM;
import com.lubinpc.todolist.utils.CalendarUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class DialogNote extends DialogFragment {
    private NoteVM note;
    private onActionDialog actions;
    private DialogNoteBinding vBind;
    public static final String TAG = "Dialog Note";
    private FragmentManager fragmentManager;

    public DialogNote(NoteVM note, onActionDialog actions) {
        this.note = note;
        this.actions = actions;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new  AlertDialog.Builder(getActivity());
        fragmentManager = getActivity().getSupportFragmentManager();
        vBind = DialogNoteBinding.bind(LayoutInflater.from(getContext()).inflate(R.layout.dialog_note,null));
        vBind.setNote(note);
        builder.setView(vBind.getRoot());
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setupBtn();
        setupTimePiker();
        return dialog;
    }


    private void setupTimePiker() {
        Calendar calendar = Calendar.getInstance();
        Calendar dateSelect = Calendar.getInstance();
        if (note.getProgramedTime() > 0L){
            calendar.setTimeInMillis(note.getProgramedTime());
        }

        TimePickerDialog timeDialog = TimePickerDialog.newInstance((view, hourOfDay, minute, second) -> {
            this.show(fragmentManager,TAG);
            dateSelect.set(Calendar.HOUR_OF_DAY,hourOfDay);
            dateSelect.set(Calendar.MINUTE,minute);
            dateSelect.set(Calendar.SECOND,second);

            note.setProgramedTime(dateSelect.getTimeInMillis());
            note.setProgramed(CalendarUtils.INSTANCE.fullFormat(dateSelect));

        },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true);

        DatePickerDialog datePicker = DatePickerDialog.newInstance((view, year, monthOfYear, dayOfMonth) -> {
            timeDialog.show(fragmentManager,"TIME PICKER");
            dateSelect.set(year,monthOfYear,dayOfMonth);
        },calendar);

        vBind.icAlarm.setOnClickListener(v -> {
            this.dismiss();
            datePicker.show(fragmentManager,"DATE PICKER");
        });
    }

    private void setupBtn() {
        vBind.fabSave.setOnClickListener(v -> actions.save(note));
        vBind.fabErase.setOnClickListener(v -> actions.destroy(note));
        vBind.fabClose.setOnClickListener(v -> dismiss());
    }


    public interface onActionDialog{
        void save(NoteVM note);
        void destroy(NoteVM note);
    }
}
