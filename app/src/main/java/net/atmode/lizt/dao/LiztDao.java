package net.atmode.lizt.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import net.atmode.lizt.entity.Lizt;

import java.util.List;

@Dao
public interface LiztDao {
    @Insert
    void insert(Lizt lizt);

    @Update
    void update(Lizt lizt);

    @Delete
    void delete(Lizt lizt);

    @Query("SELECT * FROM list")
    LiveData<List<Lizt>> getAllLizts();

    @Query("SELECT * FROM list WHERE id = :liztId")
    Lizt findLiztById(int liztId);
}
