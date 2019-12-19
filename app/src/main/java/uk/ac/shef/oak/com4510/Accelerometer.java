/*
 * Copyright (c) 2019. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package uk.ac.shef.oak.com4510;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.util.Log;


/**
 * Accelerometer is the class which can start or stop accelerometer sensor,temperature sensor
 * and pressure sensor.This class can provide record data for activity.
 *
 * @author Yuzhou Zhang
 * @version 1.0
 */
public class Accelerometer {
    private static final String TAG = Accelerometer.class.getSimpleName();
    private SensorEventListener mAccelerationListener = null;
    private SensorManager mSensorManager;
    private Sensor mAccelerometerSensor;
    private long timePhoneWasLastRebooted = 0;
    private long lastReportTime = 0;
    private Barometer barometer;
    private Tempermeter tempermeter;
    private float lastX=0;
    private float lastY=0;
    private float lastZ=0;


    /**
     * Instantiates a new Accelerometer.
     *
     * @param context     the context
     * @param barometer   the barometer
     * @param tempermeter the tempermeter
     */
    public Accelerometer(Context context, Barometer barometer,Tempermeter tempermeter) {
        // http://androidforums.com/threads/how-to-get-time-of-last-system-boot.548661/
        timePhoneWasLastRebooted = System.currentTimeMillis() - SystemClock.elapsedRealtime();
        this.barometer= barometer;
        this.tempermeter = tempermeter;
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        initAccelerometerListener();
    }

    /**
     * it inits the listener and establishes the actions to take when a reading is available
     */
    private void initAccelerometerListener() {
        if (!standardAccelerometerAvailable()) {
            Log.d(TAG, "Standard Accelerometer unavailable");
        } else {
            Log.d(TAG, "Using Accelerometer");
            mAccelerationListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    long actualTimeInMseconds = timePhoneWasLastRebooted + (long) (event.timestamp / 1000000.0);
                    float x = event.values[0];
                    float y = event.values[1];
                    float z = event.values[2];

                    float deltaX = Math.abs(lastX - event.values[0]);
                    float deltaY = Math.abs(lastY - event.values[1]);
                    float deltaZ = Math.abs(lastZ - event.values[2]);
                    // if the change is below 2, it is just plain noise
                    if (deltaX < 2) deltaX = 0;
                    if (deltaY < 2) deltaY = 0;
                    if (deltaZ < 2) deltaZ = 0;
                    if (deltaX > 0 || deltaY > 0 || deltaZ > 0) {
                        if (!barometer.isStarted())
                            barometer.startSensingPressure(Accelerometer.this);
                        tempermeter.startTemper(Accelerometer.this);
                        setLastReportTime(actualTimeInMseconds);
                    }
                    lastX = x;
                    lastY = y;
                    lastZ = z;
                }


                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                }
            };
        }
    }


    /**
     * it returns true if the sensor is available
     *
     * @return boolean
     */
    public boolean standardAccelerometerAvailable() {
        return (mAccelerometerSensor != null);
    }

    /**
     * it starts the pressure monitoring and temperature monitoring.
     */
    public void startAccelerometerRecording() {
        // if the sensor is null,then mSensorManager is null and we get a crash
        if (standardAccelerometerAvailable()) {
            Log.d(TAG, "starting listener");
            // THE ACCELEROMETER receives as frequency a predefined subset of timing
            // https://developer.android.com/reference/android/hardware/SensorManager
            mSensorManager.registerListener(mAccelerationListener, mAccelerometerSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Log.i(TAG, "barometer unavailable or already active");
        }
    }


    /**
     * this stops the barometer and temperature monitoring.
     */
    public void stopAccelerometer() {
        if (standardAccelerometerAvailable()) {
            Log.d(TAG, "Stopping listener");
            try {
                mSensorManager.unregisterListener(mAccelerationListener);
            } catch (Exception e) {
                // probably already unregistered
            }
        }
        // remember to stop the barometer
        barometer.stopBarometer();
        tempermeter.stopTemperm();
    }


    /**
     * Gets last report time.
     *
     * @return the last report time
     */
    public long getLastReportTime() {
        return lastReportTime;
    }

    /**
     * Sets last report time.
     *
     * @param lastReportTime the last report time
     */
    public void setLastReportTime(long lastReportTime) {
        this.lastReportTime = lastReportTime;
    }
}
