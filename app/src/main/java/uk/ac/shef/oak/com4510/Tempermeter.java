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


/*
 * Copyright (c) 2019. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */


/**
 * The type Tempermeter.
 */
public class Tempermeter {
    private static final String TAG = Tempermeter.class.getSimpleName();
    private long mSamplingRateInMSecs;
    private long mSamplingRateNano;
    private SensorEventListener mTemperatureListener = null;
    private SensorManager mSensorManager;
    private Sensor mTemperSensor;
    private long timePhoneWasLastRebooted = 0;
    private long BAROMETER_READING_FREQUENCY= 20000;
    private long lastReportTime = 0;
    private boolean started;
    private Accelerometer accelerometer;
    /**
     * this is used to stop the barometer if we have not seen any movement in the last 20 seconds
     */
    private static final long STOPPING_THRESHOLD = (long)1000000000;


    /**
     * Instantiates a new Tempermeter.
     *
     * @param context the context
     */
    public Tempermeter(Context context) {
        // http://androidforums.com/threads/how-to-get-time-of-last-system-boot.548661/
        timePhoneWasLastRebooted = System.currentTimeMillis() - SystemClock.elapsedRealtime();

        mSamplingRateNano = (long) (BAROMETER_READING_FREQUENCY) * 1000000;
        mSamplingRateInMSecs = (long) BAROMETER_READING_FREQUENCY;
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mTemperSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        initTempermeterListener();
    }

    /**
     * it inits the listener and establishes the actions to take when a reading is available
     */
    private void initTempermeterListener() {
        if (!TemperatureSensorAvailable()) {
            Log.d(TAG, "Standard Tempermeter unavailable");
        } else {
            Log.d(TAG, "Using Tempermeter");
            mTemperatureListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    long diff = event.timestamp - lastReportTime;
                    // time is in nanoseconds it represents the set reference times the first time we come here
                    // set event timestamp to current time in milliseconds
                    // see answer 2 at http://stackoverflow.com/questions/5500765/accelerometer-sensorevent-timestamp
                    // the following operation avoids reporting too many events too quickly - the sensor may always
                    // misbehave and start sending data very quickly
                    if (diff >= mSamplingRateNano) {
                        long actualTimeInMseconds = timePhoneWasLastRebooted + (long) (event.timestamp / 1000000.0);
                        float temperature = event.values[0];
                        Log.d("testTemper",temperature + "");
                        int accuracy = event.accuracy;
                        lastReportTime = event.timestamp;
                        MapsActivity.temperatrue = temperature;
                        // if we have not see any movement on the side of the accelerometer, let's stop
                        long timeLag= actualTimeInMseconds-accelerometer.getLastReportTime();
                    }
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
    public boolean TemperatureSensorAvailable() {
        return (mTemperSensor != null);
    }

    /**
     * it starts the pressure monitoring
     *
     * @param accelerometer the accelerometer
     */
    public void startTemper(Accelerometer accelerometer) {
        this.accelerometer= accelerometer;
        // if the sensor is null,then mSensorManager is null and we get a crash
        if (TemperatureSensorAvailable()) {
            Log.d("Standard Temper", "starting listener");
            // delay is in microseconds (1millisecond=1000 microseconds)
            // it does not seem to work though
            //stopBarometer();
            // otherwise we stop immediately because
            mSensorManager.registerListener(mTemperatureListener, mTemperSensor, (int) (mSamplingRateInMSecs * 1000));
            setStarted(true);
        } else {
            Log.i(TAG, "Temper unavailable or already active");
        }
    }


    /**
     * this stops the barometer
     */
    public void stopTemperm() {
        if (TemperatureSensorAvailable()) {
            Log.d("Standard Temper", "Stopping listener");
            try {
                mSensorManager.unregisterListener(mTemperatureListener);
            } catch (Exception e) {
                // probably already unregistered
            }
        }
        setStarted(false);
    }

    /**
     * returns true if the barometer is currently working
     *
     * @return boolean
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * Sets started.
     *
     * @param started the started
     */
    public void setStarted(boolean started) {
        this.started = started;
    }
}
