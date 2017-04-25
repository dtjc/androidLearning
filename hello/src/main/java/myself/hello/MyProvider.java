package myself.hello;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by dnnt9 on 2016/2/1.
 */
public class MyProvider extends ContentProvider{

    private static final int PERSON=1;
    private static final int PEOPLE=2;
    private MyDataBase mySQLOpenHelper;
    private static final UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
    private String tableName="test_info";

    static {
        uriMatcher.addURI(StringsConstants.HELLO_AUTHORITY,"people",PEOPLE);
        uriMatcher.addURI(StringsConstants.HELLO_AUTHORITY,"person/#",PERSON);
    }

    @Override
    public boolean onCreate() {
        mySQLOpenHelper=new MyDataBase(this.getContext(),tableName,1);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase sqldb=mySQLOpenHelper.getReadableDatabase();
        switch (uriMatcher.match(uri)){
            case PERSON:
                selection=getSelection(selection,ContentUris.parseId(uri));
                return sqldb.query(tableName,projection,selection,selectionArgs,null,null,sortOrder);
            case PEOPLE:
                return sqldb.query(tableName,projection,selection,selectionArgs,null,null,sortOrder);
            default:
                throw new IllegalArgumentException("unknown uri"+uri);
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case PERSON:
                return "vnd.android.cursor.item/myself.hello.test_info";
            case PEOPLE:
                return "vnd.android.cursor.dir/myself.hello.test_info";
            default:
                throw new IllegalArgumentException("unknown uri"+uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase sqldb=mySQLOpenHelper.getReadableDatabase();
        switch (uriMatcher.match(uri)){
            case PEOPLE:
                long rowid=sqldb.insert(tableName,null,values);
                Uri newUri=ContentUris.withAppendedId(uri,rowid);
                getContext().getContentResolver().notifyChange(newUri,null);
                return newUri;
            default:
                throw new IllegalArgumentException("unknown uri"+uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count=0;
        SQLiteDatabase sqldb=mySQLOpenHelper.getReadableDatabase();
        switch (uriMatcher.match(uri)){
            case PERSON:
                selection=getSelection(selection,ContentUris.parseId(uri));
                count=sqldb.delete(tableName,selection,selectionArgs);
                break;
            case PEOPLE:
                count=sqldb.delete(tableName,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("unknown uri"+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count=0;
        SQLiteDatabase sqldb=mySQLOpenHelper.getReadableDatabase();
        switch (uriMatcher.match(uri)){
            case PERSON:
                selection=getSelection(selection,ContentUris.parseId(uri));
                count=sqldb.update(tableName, values, selection, selectionArgs);
                break;
            case  PEOPLE:
                count = sqldb.update(tableName, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("unknown uri"+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }

    private String getSelection(String selection,long id){
        if (selection!=null&&(!selection.equals(""))){
            selection="rowid="+id+" and "+selection;
        }
        return selection;
    }

}
