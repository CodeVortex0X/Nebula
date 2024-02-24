package com.nebula.NebulaApp;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class AttendaneOperations {
    private final String STUDENT = "Student";
    private final String INSTITUTE = "Institute";
    private final String ATTENDANCE_DATA = "Attendance Data";
    private final String IS_MARKED = "isMarked";
    private final String TIME = "time";
    private String sanitizedEmail;
    private String instituteID;

    // getAllAttendedPer
    public static double  ALLATTENDEDPERCENTAGE;
    // totalDays
    public int total_days;
    // leftDays
    public int left_days;
    // leaveDays
    public int leave_days;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference attendRef = database.getReference().child("Nebula");

    public AttendaneOperations() {
    }

    //TODO User details shall (should) be fetched from Shares-Preferences <>
    public AttendaneOperations(String sanitizedEmail, String instituteID) {
        this.sanitizedEmail = sanitizedEmail;
        this.instituteID = instituteID;
    }
    public DatabaseReference getDbRef(){
        return attendRef.child(INSTITUTE).child(instituteID).child(STUDENT).child(sanitizedEmail)
                .child(ATTENDANCE_DATA);
    }


//    public class LastNodeKeyNameRetriever{
//        String  lastYear, lastMonth, lastDay;
//
//        public LastNodeKeyNameRetriever(){
////            attendRef.child(getINSTITUTE()).child(getInstituteID()).child(getSTUDENT()).child(getSanitizedEmail())
////                    .child(getATTENDANCE_DATA());
//        }
//
//        public String getLastYear() {
//            return this.lastYear;
//        }
//        public void setLastYear(String lastYear) {
//            this.lastYear = lastYear;
//        }
//        public String getLastMonth() {
//            return lastMonth;
//        }
//        public void setLastMonth(String lastMonth) {
//            this.lastMonth = lastMonth;
//        }
//        public String getLastDay() {
//            return lastDay;
//        }
//        public void setLastDay(String lastDay) {
//            this.lastDay = lastDay;
//        }
//
//
//
//        public void getLastNodeKeyNames() {
//            DatabaseReference lastKeyNodeRef = attendRef.child(INSTITUTE).child(instituteID).child(STUDENT).child(sanitizedEmail)
//                    .child(ATTENDANCE_DATA);
//            lastKeyNodeRef.limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    // Check if there is any data  >>>
//                    if (dataSnapshot.exists()) {
//                        for (DataSnapshot yearSnapshot : dataSnapshot.getChildren()) {
//                            String yearKey = String.valueOf(yearSnapshot.getKey());
//                            setLastYear(yearKey);
//                            Log.d("== Last Year ==",getLastYear());
//
//                            // Get the last month node under the year node  >>>
//                            DatabaseReference yearRef = lastKeyNodeRef.child(yearKey);
//                            yearRef.limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot yearDataSnapshot) {
//                                    if (yearDataSnapshot.exists()) {
//                                        for (DataSnapshot monthSnapshot : yearDataSnapshot.getChildren()) {
//                                            String monthKey = monthSnapshot.getKey();
//                                            setLastMonth(monthKey);
//                                            Log.d("== Last Month ==",monthKey);
//
//                                            // Get the last day node under the month node  >>>
//                                            DatabaseReference monthRef = yearRef.child(monthKey);
//                                            monthRef.limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot monthDataSnapshot) {
//                                                    if (monthDataSnapshot.exists()) {
//                                                        for (DataSnapshot daySnapshot : monthDataSnapshot.getChildren()) {
//                                                            String dayKey = daySnapshot.getKey();
//                                                            setLastDay(dayKey);
//                                                            Log.d("== Last Day ==",dayKey);
//                                                        }
//                                                    } else {
//                                                        Log.e("Attendance Operations: LastKeyNodeNamesRetreiver","No data fetched for months");
//                                                    }
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//                                                    // Handle errors  >>>
//                                                    Log.e("Attendance Operations: LastKeyNodeNamesRetreiver","Error retrieving data:" + databaseError.getMessage());
//                                                    System.out.println("Error retrieving data: " + databaseError.getMessage());
//                                                }
//                                            });
//                                        }
//                                    } else {
//                                        // No data found for the year  >>>
//                                        Log.e("Attendance Operations: LastKeyNodeNamesRetreiver","No attendance data found for the year");
//                                        System.out.println("No attendance data found for the year.");
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//                                    // Handle errors  >>>
//                                    Log.e("Attendance Operations: LastKeyNodeNamesRetreiver","Error retrieving data: " + databaseError.getMessage());
//                                    System.out.println("Error retrieving data: " + databaseError.getMessage());
//                                }
//                            });
//                        }
//                    } else {
//                        // No data found for the attendance data  >>>
//                        Log.e("Attendance Operations: LastKeyNodeNamesRetreiver","No attendance data found for the student.");
//                        System.out.println("No attendance data found for the student.");
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    // Handle errors  >>>
//                    Log.e("Attendance Operations: LastKeyNodeNamesRetreiver","Error retrieving data: " + databaseError.getMessage());
//                    System.out.println("Error retrieving data: " + databaseError.getMessage());
//                }
//            });
//        }
//    }
    //   Todo LastNodeKeyNameRetriever inner class (above) 👆

    public String getSTUDENT() {
        return STUDENT;
    }
    public String getINSTITUTE() {
        return INSTITUTE;
    }
    public String getATTENDANCE_DATA() {
        return ATTENDANCE_DATA;
    }
    public String getSanitizedEmail() {
        return sanitizedEmail;
    }
    public void setSanitizedEmail(String sanitizedEmail) {
        this.sanitizedEmail = sanitizedEmail;
    }
    public String getYear() {
        return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    }
    public String getMonth() {
        return new SimpleDateFormat("MMM", Locale.getDefault()).format(Calendar.getInstance().getTime());
    }
    public String getDate() {
        return new SimpleDateFormat("dd|MM|yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime());
    }
    public String getIsMarked() {
        return IS_MARKED;
    }
    public String getInstituteID() {
        return instituteID;
    }
    public String getTime(){
        String timeStamp = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Calendar.getInstance().getTime());
        return timeStamp;
    }
    public void setInstituteID(String instituteID) {
        this.instituteID = instituteID;
    }
//    }
    public double getAllAttendedPer(){

        attendRef.child(INSTITUTE).child(instituteID).child(STUDENT).child(sanitizedEmail)
            .child(ATTENDANCE_DATA)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int totalDaysSince = 0;
                        int markedDays = 0;

                        for (DataSnapshot yearSnapshot : snapshot.getChildren()) {
                            for (DataSnapshot monthSnapshot : yearSnapshot.getChildren()) {
                                for (DataSnapshot dateSnapshot : monthSnapshot.getChildren()) {
                                    Boolean isMarked = dateSnapshot.child("isMarked").getValue(Boolean.class);
                                    if (isMarked != null && isMarked) {
                                        markedDays++;
                                    }
                                    totalDaysSince++;
                                }
                            }
                        }

                        if (totalDaysSince > 0) {
                            ALLATTENDEDPERCENTAGE = (double) markedDays / totalDaysSince * 100;
                            Log.d("AttendanceOperations", "All Attendance Perc:"+ALLATTENDEDPERCENTAGE);
                        } else {
                            ALLATTENDEDPERCENTAGE = 0.0;
                            Log.e("AttendanceOperations", "No attendance data found. Result: "+ALLATTENDEDPERCENTAGE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("AttendanceOperations: getAllAttendancePer", "Operation canceled: "+error);
                    }
                });
        return ALLATTENDEDPERCENTAGE;
    }

    // Helper Method to create a new day node inside a month node >>>
    public void createDayNode(int year, int month, int day) {
        String[] allMonths = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        // Create a reference to the year node  >>>
        DatabaseReference yearRef = attendRef.child(INSTITUTE).child(instituteID).child(STUDENT).child(sanitizedEmail)
            .child(ATTENDANCE_DATA).child(String.valueOf(year));
        // Create a reference to the month node  >>>
        DatabaseReference monthRef = yearRef.child(allMonths[month-1]);
        String formattedDay = day + "|" + month + "|" + year;
        // Create a reference to the day node  >>>
        DatabaseReference dayRef = monthRef.child(formattedDay);
        // Set some value for the day node >>>
        dayRef.setValue("Some value");
    }
    // Helper Method to create a new month node inside a year node >>>
    public void createMonthNode(int year, String month) {
        // Create a reference to the year node >>>
        DatabaseReference yearRef = attendRef.child(INSTITUTE).child(instituteID).child(STUDENT).child(sanitizedEmail)
            .child(ATTENDANCE_DATA).child(String.valueOf(year));
        // Create a reference to the month node >>>
        DatabaseReference monthRef = yearRef.child(month);
        // Set some value for the month node  >>>
//        monthRef.setValue("Some value");
    }
    // Helper Method to create a new year node >>>
    public void createYearNode(int year) {
        DatabaseReference yearRef = attendRef.child(INSTITUTE).child(instituteID).child(STUDENT).child(sanitizedEmail)
            .child(ATTENDANCE_DATA).child(String.valueOf(year));
    }

    // Method to check if a new day has arrived and create a new day node in DB>>>
    public void checkNewDay() {
        // Get the current date >>>
        LastNodeKeyNameRetriever lastNodeKeyNameRetriever = new LastNodeKeyNameRetriever(getDbRef());
        lastNodeKeyNameRetriever.getLastNodeKeyNames();

        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        ArrayList<String> allMonths = new ArrayList<String>(Arrays.asList("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"));

        String lastYearString = lastNodeKeyNameRetriever.getLastMonth();
        if (lastYearString != null) {
            int lastYear = Integer.parseInt(lastYearString);
        } else {
            Log.e("AttendaneOperations", String.valueOf(allMonths.indexOf(lastNodeKeyNameRetriever.getLastMonth()) + 1));
        }

        // Get the last stored date from the DB >>>
        int lastYear = Integer.parseInt(lastNodeKeyNameRetriever.getLastYear());
        int lastMonth = allMonths.indexOf(lastNodeKeyNameRetriever.getLastMonth()) + 1;
        int lastDay = Integer.parseInt(lastNodeKeyNameRetriever.getLastDay());

        // Compare the current date and the last stored date (From DB)>>>
        if (currentYear > lastYear) {
            createYearNode(currentYear);
            createMonthNode(currentYear, allMonths.get(currentMonth - 1));
            createDayNode(currentYear, currentMonth, currentDay);
        } else if (currentMonth > lastMonth) {
            createMonthNode(currentYear, allMonths.get(currentMonth - 1));
            createDayNode(currentYear, currentMonth, currentDay);
        } else if (currentDay > lastDay) {
            createDayNode(currentYear, currentMonth, currentDay);
        }
    }

    // Calculate total days of sem and return >>>
    public int getTotalDays(){
        checkNewDay();
        attendRef.child(INSTITUTE).child(instituteID).child(STUDENT).child(sanitizedEmail)
            .child(ATTENDANCE_DATA).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int totalDaysSince = 0;
                        for (DataSnapshot yearSnapshot : snapshot.getChildren()) {
                            for (DataSnapshot monthSnapshot : yearSnapshot.getChildren()) {
                                for (DataSnapshot dateSnapshot : monthSnapshot.getChildren()) {
                                    totalDaysSince++;
                                }
                            }
                        }
                        total_days = totalDaysSince;
                        if (total_days > 0) {
                            Log.e("AttendanceOperations: totalDays", "Total days:"+total_days);
                        } else {
                            Log.e("AttendanceOperations: totalDays", "No attendance data found. Result: "+total_days);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("AttendanceOperations: totalDays", "Operation Canceled    "+error);

                    }
                });

        return total_days;
    }

    // Calculate days left of sem and return >>>
    public int getLeftDays(){
        checkNewDay();
        int total_assigned_days = 0; //TODO get total days of the sem from the management DB data
        attendRef.child(INSTITUTE).child(instituteID).child(STUDENT).child(sanitizedEmail)
            .child(ATTENDANCE_DATA).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int totalDaysSince = 0;
                        for (DataSnapshot yearSnapshot : snapshot.getChildren()) {
                            for (DataSnapshot monthSnapshot : yearSnapshot.getChildren()) {
                                for (DataSnapshot dateSnapshot : monthSnapshot.getChildren()) {
                                    totalDaysSince++;
                                }
                            }
                        }
                        total_days = totalDaysSince;
                        if (total_days > 0) {
                            Log.e("AttendanceOperations: leftDays", "Total days:"+total_days);
                        } else {
                            Log.e("AttendanceOperations: leftDays", "No attendance data found. Result: "+total_days);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("AttendanceOperations: leftDays", "Operation Canceled"+error);
                    }
                });

        return (total_assigned_days-total_days);
    }

    // Calculate the days that student didn't attend >>>
    public int getLeaveDays(){
        DatabaseReference leavesRef = database.getReference().child("Nebula").child(INSTITUTE)
                .child(instituteID).child(STUDENT).child(sanitizedEmail).child("Leave Data"); //TODO Check DB for Exact name of "Leave Data" <<<
        leavesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int leaveDays = 0;

                for (DataSnapshot yearSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot monthSnapshot : yearSnapshot.getChildren()) {
                        for (DataSnapshot dateSnapshot : monthSnapshot.getChildren()) {
                            Boolean isMarked = dateSnapshot.child("isMarked").getValue(Boolean.class);
                            if (isMarked != null && (!isMarked)) {
                                leaveDays++;
                            }
                        }
                    }
                }

                if (leaveDays > 0) {
                    leave_days =  leaveDays;
                    Log.e("AttendanceOperations: getLeaveDays", "Leave Days:"+leave_days);
                } else {
                    leave_days = 0;
                    Log.e("AttendanceOperations: getLeaveDays", "No leave data found. Result: "+leave_days);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("AttendanceOperations: getLeaveDays", "Operation canceled: "+error);
            }
        });
        return leave_days;
    }

    // Mark new attendance >>>
    public void markAttendance(boolean isMarked) {
        DatabaseReference mark = attendRef.child(INSTITUTE).child(instituteID).child(STUDENT).child(sanitizedEmail)
            .child(ATTENDANCE_DATA).child(getYear()).child(getMonth()).child(getDate());
        mark.child("isMarked").setValue(isMarked);
        String timeStamp = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Calendar.getInstance().getTime());
        mark.child("time").setValue(timeStamp);
    }

    // Calculate the increase or decrease of the percentage of the current month >>>
    public double getThisMonthStats(){
        double percentage = 0.0;
        // TODO  INSTITUTE > instituteId > STUDENT > studentID > ATTENDANCE_DATA > year > month > date > {
        //      (isMarked=boolean),
        //      (time=String)
        //  }
        return percentage;
    }
}



