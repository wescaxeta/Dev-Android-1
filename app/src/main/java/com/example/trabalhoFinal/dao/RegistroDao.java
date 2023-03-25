package com.example.trabalhoFinal.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.trabalhoFinal.modelo.Filme;

import java.util.List;

@Dao
public interface RegistroDao {

    @Query("SELECT * FROM Filme")
    List<Filme> getAll();

    @Query("SELECT * FROM Filme WHERE id IN (:registroIds)")
    List<Filme> loadAllByIds(int[] registroIds);

    @Query("SELECT * FROM Filme WHERE titulo LIKE :titulo LIMIT 1")
    Filme findByName(String titulo);

    @Query("SELECT * FROM Filme ORDER BY titulo ASC")
    LiveData<List<Filme>> getAlphabetizedTitulos();

    @Query("SELECT * FROM Filme ORDER BY nota DESC")
    LiveData<List<Filme>> getNotaOrderDesc();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Filme... filmes);

    @Delete
    void delete(Filme filme);

    @Update
    int update(Filme filme);

    @Query("DELETE FROM Filme")
    void deleteAll();
}
