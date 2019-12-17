package uk.ac.shef.oak.com4510.Entity;



import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import uk.ac.shef.oak.com4510.presenter.Presenter;
import uk.ac.shef.oak.com4510.presenter.RetPresenter;


public class Model {
    private final RecordMsgDAO recordMsgDAO;
    Presenter presenter;
    RetPresenter rpresenter;

    //private final List<PhotoData> m_list_PhotoData = new ArrayList<>();


    public Model(Context context, Presenter presenter) {
        this.presenter= presenter;

        RecordMsgDataBase db = RecordMsgDataBase.getDatabase(context);
        recordMsgDAO = db.recordMsgDAO();
    }

    public Model(Context context, RetPresenter rpresenter) {
        this.rpresenter= rpresenter;

        RecordMsgDataBase db = RecordMsgDataBase.getDatabase(context);
        recordMsgDAO = db.recordMsgDAO();
    }

    /**
     *  it generates a random integer (0 and 1) and returns either an error or a correct message
     * @param title
     * @param description
     */
    public void insertData(float temperature, float pressure,String title, String date,String files) {
        if ((!title.isEmpty()) && (!date.isEmpty())) {
            // data insertion cannot be done on the UI thread. Use an ASync process!!
            new InsertIntoDbAsync(recordMsgDAO, new RecordMsg(temperature,pressure,title, date,files),presenter).execute();
        } else presenter.errorInsertingData(temperature,pressure,title, date,files, "data should not be empty");
    }

    public void getData(float temperature, float pressure,String title, String date,String files)
    {
        new GetFromDbAsync(recordMsgDAO, new RecordMsg(temperature,pressure,title, date,files),rpresenter).execute();

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

            if(!m_list_RecordMsg.isEmpty()) {
                int i = m_list_RecordMsg.size();
                mPresenter.dataRetrieved(m_list_RecordMsg.get(0).getTemperature(), m_list_RecordMsg.get(0).getPressure(),m_list_RecordMsg.get(0).getTitle(),m_list_RecordMsg.get(0).getDate(),m_list_RecordMsg.get(0).getFiles());
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
            mPresenter.dataInserted(recordMsg.getTemperature(), recordMsg.getPressure(),recordMsg.getTitle(),recordMsg.getDate(),recordMsg.getFiles());

        }
    }

}

