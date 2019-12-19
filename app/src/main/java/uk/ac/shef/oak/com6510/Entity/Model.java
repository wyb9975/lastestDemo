package uk.ac.shef.oak.com6510.Entity;



import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import uk.ac.shef.oak.com6510.presenter.Presenter;
import uk.ac.shef.oak.com6510.presenter.RetPresenter;


/**
 * Model is the class which provide some methods for data query and data acquisition,
 * which allows presenter to query or get data from database using asynchronous task
 * and callbacks the methods in the interface.This class Operate the database by calling dao.
 *
 * @author Yuzhou Zhang
 * @version 1.0
 */
public class Model {
    private final RecordMsgDAO recordMsgDAO;
    Presenter presenter;
    RetPresenter rpresenter;

    /**
     * Instantiates a new Model.
     *
     * @param context   the application context
     * @param presenter the presenter
     */
    public Model(Context context, Presenter presenter) {
        this.presenter= presenter;

        RecordMsgDataBase db = RecordMsgDataBase.getDatabase(context);
        recordMsgDAO = db.recordMsgDAO();
    }

    /**
     * Instantiates a new Model.
     *
     * @param context    the application context
     * @param rpresenter the rpresenter
     */
    public Model(Context context, RetPresenter rpresenter) {
        this.rpresenter= rpresenter;

        RecordMsgDataBase db = RecordMsgDataBase.getDatabase(context);
        recordMsgDAO = db.recordMsgDAO();
    }

    /**
     * Uses dao to insert data into database by using asynchronous task.
     *
     * @param temperature the temperature
     * @param pressure    the pressure
     * @param title       the title
     * @param date        the date
     * @param files       the files
     * @param lat         the lat
     * @param lng         the lng
     */
    public void insertData(float temperature, float pressure,String title, String date,String files,double lat,double lng) {
        if ((!title.isEmpty()) && (!date.isEmpty())) {
            // data insertion cannot be done on the UI thread. Use an ASync process!!
            new InsertIntoDbAsync(recordMsgDAO, new RecordMsg(temperature,pressure,title, date,files,lat,lng),presenter).execute();
        } else presenter.errorInsertingData(temperature,pressure,title, date,files,lat,lng ,"data should not be empty");
    }

    /**
     * Uses dao to get data from database by using asynchronous task.
     *
     * @param temperature the temperature
     * @param pressure    the pressure
     * @param title       the title
     * @param date        the date
     * @param files       the files
     * @param lat         the lat
     * @param lng         the lng
     */
    public void getData(float temperature, float pressure,String title, String date,String files,double lat,double lng)
    {
        new GetFromDbAsync(recordMsgDAO, new RecordMsg(temperature,pressure,title, date,files,lat,lng),rpresenter).execute();

    }

    /**
     * Populate the database in the background.
     * If you want to start with more words, just add them.
     */
    private static class GetFromDbAsync extends AsyncTask<Void, Void, Void> {
        private final RecordMsgDAO recordMsgDAO;
        private final RecordMsg recordMsg;
        private final RetPresenter mPresenter;

        private final ArrayList<RecordMsg> m_list_RecordMsg = new ArrayList<>();

        /**
         * Instantiates a new Get from db async.
         *
         * @param dao        the dao
         * @param data       the data
         * @param rpresenter the rpresenter
         */
        GetFromDbAsync(RecordMsgDAO dao, RecordMsg data, RetPresenter rpresenter) {
            recordMsgDAO = dao;
            recordMsg= data;
            mPresenter= rpresenter;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            //mPhotoDao.insert(mPhotoData);
            if(!recordMsgDAO.retrieveAllData().isEmpty()) {

                for (int i = 0; i < recordMsgDAO.retrieveAllData().size(); i++) {
                    m_list_RecordMsg.add(recordMsgDAO.retrieveAllData().get(i));
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mPresenter.DataRetreived(m_list_RecordMsg);
            if(!m_list_RecordMsg.isEmpty()) {
                int i = m_list_RecordMsg.size();
                mPresenter.dataRetrieved(m_list_RecordMsg.get(0).getTemperature(), m_list_RecordMsg.get(0).getPressure(),m_list_RecordMsg.get(0).getTitle(),m_list_RecordMsg.get(0).getDate(),m_list_RecordMsg.get(0).getFiles(),m_list_RecordMsg.get(0).getLat(),m_list_RecordMsg.get(0).getLng());
                Log.d("opopopop",m_list_RecordMsg.get(0).getTemperature() + " " + m_list_RecordMsg.get(0).getPressure() + " " + m_list_RecordMsg.get(0).getTitle() + " " + m_list_RecordMsg.get(0).getDate() + " " + m_list_RecordMsg.get(0).getFiles());
                mPresenter.ListDataRetreived(m_list_RecordMsg);
            }
        }

    }

    /**
     * Populate the database in the background.
     * If you want to start with more words, just add them.
     */
    private static class InsertIntoDbAsync extends AsyncTask<Void, Void, Void> {
        private final RecordMsgDAO recordMsgDAO;
        private final RecordMsg recordMsg;
        private final Presenter mPresenter;

        /**
         * Instantiates a new InsertIntoDbAsync class.
         *
         * @param dao       the dao
         * @param data      the data
         * @param presenter the presenter
         */
        InsertIntoDbAsync(RecordMsgDAO dao, RecordMsg data, Presenter presenter) {
            recordMsgDAO = dao;
            recordMsg= data;
            mPresenter= presenter;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            recordMsgDAO.insert(recordMsg);
            Log.d("yuyuyu",recordMsg.getDate() + " " + recordMsg.getFiles() + " " + recordMsg.getTitle() + " " + recordMsg.getPressure() + " " + recordMsg.getTemperature());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mPresenter.dataInserted(recordMsg.getTemperature(), recordMsg.getPressure(),recordMsg.getTitle(),recordMsg.getDate(),recordMsg.getFiles(),recordMsg.getLat(),recordMsg.getLng());

        }
    }

}

