package net.atmode.lizt.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import net.atmode.lizt.dao.LiztDao;
import net.atmode.lizt.database.LiztDatabase;
import net.atmode.lizt.entity.Lizt;

import java.util.List;

public class LiztViewModel extends AndroidViewModel {
    private LiztDao liztDao;
    private LiztDatabase liztDatabase;
    private LiveData<List<Lizt>> allLizts;

    public LiztViewModel(Application application) {
        super(application);

        liztDatabase = LiztDatabase.getDatabase(application);
        liztDao = liztDatabase.liztDao();
        allLizts = liztDao.getAllLizts();
    }

    public LiveData<List<Lizt>> getAllLizts() {
        return allLizts;
    }

    public void insert(Lizt lizt) {new InsertAsyncTask(liztDao).execute(lizt);}

    public void update(Lizt lizt) {new UpdateAsyncTask(liztDao).execute(lizt);}

    public void delete(Lizt lizt) {new DeleteAsyncTask(liztDao).execute(lizt);}

    private class InsertAsyncTask extends AsyncTask<Lizt, Void, Void> {

        LiztDao liztDao;

        public InsertAsyncTask(LiztDao liztDao) {
            this.liztDao = liztDao;
        }

        @Override
        protected Void doInBackground(Lizt... lizts) {
            liztDao.insert(lizts[0]);
            return null;
        }
    }

    private class UpdateAsyncTask extends AsyncTask<Lizt, Void, Void> {

        LiztDao liztDao;

        public UpdateAsyncTask(LiztDao liztDao) {
            this.liztDao = liztDao;
        }

        @Override
        protected Void doInBackground(Lizt... lizts) {
            liztDao.update(lizts[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Lizt, Void, Void> {

        LiztDao liztDao;

        public DeleteAsyncTask(LiztDao liztDao) {
            this.liztDao = liztDao;
        }

        @Override
        protected Void doInBackground(Lizt... lizts) {
            liztDao.delete(lizts[0]);
            return null;
        }
    }
}
