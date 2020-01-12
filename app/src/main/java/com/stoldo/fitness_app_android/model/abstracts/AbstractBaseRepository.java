package com.stoldo.fitness_app_android.model.abstracts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.stoldo.fitness_app_android.model.enums.ErrorCode;
import com.stoldo.fitness_app_android.model.interfaces.Entity;

import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.List;

public abstract class AbstractBaseRepository<T extends Entity> extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "fitness_app.db"; // TODO in property or res file

    private final ConnectionSource CONNECTION_SOURCE;
    private final Class ENTITY_CLASS;
    private final Dao<T, Integer> ENTITY_DAO;


    public AbstractBaseRepository(Context context, Class entityClass) throws SQLException {
        super(context, DATABASE_NAME, null, 1);
        this.CONNECTION_SOURCE = new AndroidConnectionSource(this);
        this.ENTITY_CLASS = entityClass;
        this.ENTITY_DAO = DaoManager.createDao(CONNECTION_SOURCE, ENTITY_CLASS);

        createEntityTableIfNotExists();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ENTITY_DAO.getTableName());
        onCreate(db);
    }

    // TODO doesnt work --> find a proper way, maybe in activity destroy?
//    @Override
//    protected void finalize() throws Throwable {
//        Log.d("MYDEBUG", "Closing connection?");
//        CONNECTION_SOURCE.close();
//        super.finalize();
//    }

    public T save(T entity) throws SQLException {
        if (entity.getId() == null || (entity.getId() != null && getById(entity.getId()) == null)) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }

    public T getById(Integer id) throws SQLException {
        return ENTITY_DAO.queryForId(id);
    }

    public List<T> findAll() throws SQLException {
        return ENTITY_DAO.queryForAll();
    }

    /**
     * @return the savable dao to extending classes can use it to implement repository specific queries
     * */
    protected Dao<T, Integer> getEntityDao() {
        return ENTITY_DAO;
    }

    /**
     * Can be used directly after the save to retrieve the saved entity.
     *
     * @return The last saved Id.
     * @throws SQLException if the query fails
     * */
    protected Integer getLastSavedId() throws SQLException {
        GenericRawResults<String[]> results = ENTITY_DAO.queryRaw("SELECT last_insert_rowid()");

        for (String[] result : results) {
            for (String s : result) {
                if (StringUtils.isNumeric(s)) {
                    return Integer.parseInt(s);
                }
            }
        }

        return null;
    }

    /**
     * Creates a table based on the given entity if that table does not exist already
     *
     * @throws SQLException when table creation fails.
     * */
    private void createEntityTableIfNotExists() throws SQLException {
        TableUtils.createTableIfNotExists(CONNECTION_SOURCE, ENTITY_CLASS);
    }

    /**
     * Inserts a given Entity
     *
     * @param entity Entity which should be inserted
     * @return inserted Entity
     * @throws Exception if insert fails
     * */
    private T insert(T entity) throws SQLException {
        if (ENTITY_DAO.create(entity) != 1) {
            throw new SQLException(ErrorCode.E1005.getErrorMsg(entity.toString()));
        }

        return ENTITY_DAO.queryForId(getLastSavedId());
    }

    /**
     * Updates a given Entity
     *
     * @param entity Entity which should be updated
     * @return updated Entity
     * @throws Exception if update fails
     * */
    private T update(T entity) throws SQLException {
        if (ENTITY_DAO.update(entity) != 1) {
            throw new SQLException(ErrorCode.E1007.getErrorMsg(entity.toString()));
        }

        return ENTITY_DAO.queryForId(entity.getId());
    }
}
