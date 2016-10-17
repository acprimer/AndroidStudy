package com.example.download;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.net.Uri;
import android.os.Environment;
import android.provider.BaseColumns;
import android.util.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.DownloadManager.COLUMN_LOCAL_FILENAME;

/**
 * Created by yaodh on 2016/10/14.
 */

public class DownloadManager {
    private static final String TAG = "DownloadManager";
    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_URI = "uri";
    public static final String COLUMN_MEDIA_TYPE = "media_type";
    public static final String COLUMN_TOTAL_SIZE_BYTES = "total_size";
    public static final String COLUMN_LOCAL_URI = "local_uri";
    public static final String COLUMN_STASUS = "status";
    public static final String COLUMN_REASON = "reason";
    public static final String COLUMN_BYTES_DOWNLOADED_SO_FAR = "bytes_so_far";
    public static final String COLUMN_LAST_MODIFIED_TIMESTAMP = "last_modified_timestamp";

    // status code
    public static final int STATUS_PENDING = 1;
    public static final int STATUS_RUNNING = 1 << 1;
    public static final int STATUS_PAUSED = 1 << 2;
    public static final int STATUS_SUCCESSFUL = 1 << 3;
    public static final int STATUS_FAILED = 1 << 4;

    // error code
    public static final int ERROR_UNKNOWN = 1000;
    public static final int ERROR_FILE_ERROR = 1001;
    public static final int ERROR_UNHANLED_HTTP_CODE = 1002;
    public static final int ERROR_HTTP_DATA_ERROT = 1003;
    public static final int ERROR_TOO_MANY_REDIRECTS = 1004;
    public static final int ERROR_INSUFFICIENT_SPACE = 1005;
    public static final int ERROR_DEVICE_NOT_FOUND = 1006;
    public static final int ERROR_CANNOT_RESUME = 1007;
    public static final int ERROR_FILE_ALREADY_EXISTS = 1008;
    public static final int ERROR_WAITING_TO_RETRY = 1009;

    // pause reason code
    public static final int PAUSED_WAITING_TO_RETRY = 1;
    public static final int PAUSED_WAITING_FOR_NETWORK = 2;
    public static final int PAUSED_QUEUED_FOR_WIFI = 3;
    public static final int PAUSED_UNKNOWN = 4;

    // broadcast intent action
    public static final String ACTION_DOWNLOAD_COMPLETE = "android.intent.action.DOWNLOAD_COMPLETE";
    public static final String ACTION_NOTIFICATION_CLICKED = "android.intent.action.NOTIFICATION_CLICKED";
    public static final String ACTION_VIWE_DOWNLOADS = "android.intent.action.ACTION_VIWE_DOWNLOADS";

    // intent extra
    public static final String EXTRA_DOWNLOAD_ID = "extra_download_id";

    public static final String[] UNDERLYING_COLUMNS = new String[]{
            Downloads.Impl._ID,
            Downloads.Impl._DATA + " AS " + COLUMN_LOCAL_FILENAME,
            Downloads.Impl.COLUMN_MEDIAPROVIDER_URI,
            Downloads.Impl.COLUMN_DESTINATION,
            Downloads.Impl.COLUMN_TITLE,
            Downloads.Impl.COLUMN_DESCRIPTION,
            Downloads.Impl.COLUMN_URI,
            Downloads.Impl.COLUMN_STATUS,
            Downloads.Impl.COLUMN_FILE_NAME_HINT,
            Downloads.Impl.COLUMN_MIME_TYPE + " AS " + COLUMN_MEDIA_TYPE,
            Downloads.Impl.COLUMN_TOTAL_BYTES + " AS " + COLUMN_TOTAL_SIZE_BYTES,
            Downloads.Impl.COLUMN_LAST_MODIFICATION + " AS " + COLUMN_LAST_MODIFIED_TIMESTAMP,
            Downloads.Impl.COLUMN_CURRENT_BYTES + " AS " + COLUMN_BYTES_DOWNLOADED_SO_FAR,
            Downloads.Impl.COLUMN_ALLOW_WRITE,
        /* add the following 'computed' columns to the cursor.
         * they are not 'returned' by the database, but their inclusion
         * eliminates need to have lot of methods in CursorTranslator
         */
            "'placeholder' AS " + COLUMN_LOCAL_URI,
            "'placeholder' AS " + COLUMN_REASON
    };

    private ContentResolver mResolver;
    private String mPackageName;
    private Uri mBaseUri = Downloads.Impl.CONTENT_URI;

    public DownloadManager(ContentResolver mResolver, String mPackageName) {
        this.mResolver = mResolver;
        this.mPackageName = mPackageName;
    }

    public void setAccessAllDownloads(boolean accessAllDownloads) {
        if (accessAllDownloads) {
            mBaseUri = Downloads.Impl.ALL_DOWNLOADS_CONTENT_URI;
        } else {
            mBaseUri = Downloads.Impl.CONTENT_URI;
        }
    }

    public long enqueue(Request request) {
        ContentValues values = request.toContentValues(mPackageName);
        Uri downloadUri = mResolver.insert(Downloads.Impl.CONTENT_URI, values);
        long id = Long.parseLong(downloadUri.getLastPathSegment());
        return id;
    }

    public int markRowDeleted(long... ids) {
        if (ids == null || ids.length == 0) {
            throw new IllegalArgumentException("input param 'ids' can't be null");
        }
        ContentValues values = new ContentValues();
        values.put(Downloads.Impl.COLUMN_DELETED, 1);
        if (ids.length == 1) {
            return mResolver.update(ContentUris.withAppendedId(
                    mBaseUri, ids[0]), values, null, null);
        }
        return mResolver.update(mBaseUri, values,
                getWhereClauseForIds(ids), getWhereArgsForIds(ids));
    }

    public int remove(long... ids) {
        return markRowDeleted(ids);
    }

    public Cursor query(Query query) {
        Cursor underlyingCursor = query.runQuery(mResolver, UNDERLYING_COLUMNS, mBaseUri);
        if (underlyingCursor == null) {
            return null;
        }
        return new CursorTranslator(underlyingCursor, mBaseUri);
    }

    static String getWhereClauseForIds(long[] ids) {
        StringBuilder whereClause = new StringBuilder();
        whereClause.append('(');
        for (int i = 0; i < ids.length; i++) {
            if (i > 0) {
                whereClause.append("OR ");
            }
            whereClause.append(Downloads.Impl._ID);
            whereClause.append(" = ? ");
        }
        whereClause.append(')');
        return whereClause.toString();
    }

    static String[] getWhereArgsForIds(long[] ids) {
        String[] whereArgs = new String[ids.length];
        for (int i = 0; i < ids.length; i++) {
            whereArgs[i] = Long.toString(ids[i]);
        }
        return whereArgs;
    }

    public static class Request {
        public static final int NETWORK_MOBILE = 1;
        public static final int NETWORK_WIFI = 1 << 1;
        public static final int NETWORK_BLUETOOTH = 1 << 2;

        private Uri mUri;

        private Uri mDestinationUri;
        private List<Pair<String, String>> mRequestHeaders = new ArrayList<>();
        private CharSequence mTitle;
        private CharSequence mDescription;
        private boolean mShowNotification = true;
        private String mMimeType;
        private boolean mRoamingAllowed = true; // 流量
        private boolean mMeteredAllowed = true; //
        private int mAllowedNetworkTypes = ~0;  // all types
        private boolean mIsVisibleInDownloadsUi = true;
        private boolean mScannable = false;
        private boolean mUseSystemCache = false;
        private static final int SCANNABLE_VALUE_YES = 0;

        private static final int SCANNABLE_VALUE_NO = 2;
        public static final int VISIBILITY_VISIBLE = 0;

        public static final int VISIBILITY_VISIBLE_NOTIFY_COMPLETED = 1;
        public static final int VISIBILITY_HIDDEN = 2;
        public static final int VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION = 3;

        private int mNotificationVisibility = VISIBILITY_VISIBLE;

        public Request(Uri uri) {
            if (uri == null) {
                throw new NullPointerException();
            }
            String scheme = uri.getScheme();
            if (scheme == null || !scheme.equals("http")) {
                throw new IllegalArgumentException("Can only download HTTP URIs: " + uri);
            }
            mUri = uri;
        }

        public Request setDestinationUri(Uri uri) {
            mDestinationUri = uri;
            return this;
        }

        public Request setTitle(CharSequence title) {
            mTitle = title;
            return this;
        }

        public Request setDescription(CharSequence description) {
            mDescription = description;
            return this;
        }

        public Request setMimeType(String mimeType) {
            mMimeType = mimeType;
            return this;
        }

        public Request setDestinationInExternalPublicDir(String dirType, String subPath) {
            setDestinationFromBase(Environment.getExternalStoragePublicDirectory(dirType), subPath);
            return this;
        }

        private void setDestinationFromBase(File base, String subPath) {
            if (subPath == null) {
                throw new NullPointerException("subPath cannot be null");
            }
            mDestinationUri = Uri.withAppendedPath(Uri.fromFile(base), subPath);
        }

        ContentValues toContentValues(String packageName) {
            ContentValues values = new ContentValues();
            assert mUri != null;
            values.put(Downloads.Impl.COLUMN_URI, mUri.toString());
            values.put(Downloads.Impl.COLUMN_IS_PUBLIC_API, true);
            values.put(Downloads.Impl.COLUMN_NOTIFICATION_PACKAGE, packageName);

            if (mDestinationUri != null) {
                values.put(Downloads.Impl.COLUMN_DESTINATION, Downloads.Impl.DESTINATION_FILE_URI);
                values.put(Downloads.Impl.COLUMN_FILE_NAME_HINT, mDestinationUri.toString());
            } else {
                values.put(Downloads.Impl.COLUMN_DESTINATION,
                        mUseSystemCache ?
                                Downloads.Impl.DESTINATION_SYSTEMCACHE_PARTITION :
                                Downloads.Impl.DESTINATION_CACHE_PARTITION_PURGEABLE);
            }

            values.put(Downloads.Impl.COLUMN_MEDIA_SCANNED,
                    mScannable ? SCANNABLE_VALUE_YES : SCANNABLE_VALUE_NO);

            if (!mRequestHeaders.isEmpty()) {
                encodeHttpHeaders(values);
            }

            putIfNonNull(values, Downloads.Impl.COLUMN_TITLE, mTitle);
            putIfNonNull(values, Downloads.Impl.COLUMN_DESCRIPTION, mDescription);
            putIfNonNull(values, Downloads.Impl.COLUMN_MIME_TYPE, mMimeType);

            values.put(Downloads.Impl.COLUMN_VISIBILITY, mNotificationVisibility);
            values.put(Downloads.Impl.COLUMN_ALLOWED_NETWORK_TYPES, mAllowedNetworkTypes);
            values.put(Downloads.Impl.COLUMN_ALLOW_ROAMING, mRoamingAllowed);
            values.put(Downloads.Impl.COLUMN_ALLOW_METERED, mMeteredAllowed);
            values.put(Downloads.Impl.COLUMN_IS_VISIBLE_IN_DOWNLOADS_UI, mIsVisibleInDownloadsUi);

            return values;
        }

        private void encodeHttpHeaders(ContentValues values) {
            int index = 0;
            for (Pair<String, String> header : mRequestHeaders) {
                String headerString = header.first + ": " + header.second;
                values.put(Downloads.Impl.RequestHeaders.INSERT_KEY_PREFIX + index, headerString);
                index++;
            }
        }

        private void putIfNonNull(ContentValues contentValues, String key, Object value) {
            if (value != null) {
                contentValues.put(key, value.toString());
            }
        }
    }

    public static class Query {
        public static final int ORDER_ASCENDING = 1;
        public static final int ORDER_DESCENDING = 2;

        private long[] mIds = null;
        private Integer mStatusFlags = null;
        private String mOrderByColumn = Downloads.Impl.COLUMN_LAST_MODIFICATION;
        private int mOrderDirection = ORDER_DESCENDING;
        private boolean mOnlyIncludeVisibleInDownloadUi = false;

        public Query setFilterById(long... ids) {
            mIds = ids;
            return this;
        }

        public Query setFilterByStatus(int flags) {
            mStatusFlags = flags;
            return this;
        }

        public Query setOnlyIncludeVisibleInDownloadUi(boolean value) {
            mOnlyIncludeVisibleInDownloadUi = value;
            return this;
        }

        public Query orderBy(String column, int direction) {
            if (direction != ORDER_ASCENDING && direction != ORDER_DESCENDING) {
                throw new IllegalArgumentException("Invalid direction: " + direction);
            }

            if (column.equals(COLUMN_LAST_MODIFIED_TIMESTAMP)) {
                mOrderByColumn = Downloads.Impl.COLUMN_LAST_MODIFICATION;
            } else if (column.equals(COLUMN_TOTAL_SIZE_BYTES)) {
                mOrderByColumn = Downloads.Impl.COLUMN_TOTAL_BYTES;
            } else {
                throw new IllegalArgumentException("Cannot order by " + column);
            }
            mOrderDirection = direction;
            return this;
        }

        Cursor runQuery(ContentResolver resolver, String[] projection, Uri baseUri) {
            Uri uri = baseUri;
            List<String> selectionParts = new ArrayList<>();
            String[] selectionArgs = null;

            if (mIds != null) {
                selectionParts.add(getWhereClauseForIds(mIds));
                selectionArgs = getWhereArgsForIds(mIds);
            }

            if (mStatusFlags != null) {
                List<String> parts = new ArrayList<>();
                if ((mStatusFlags & STATUS_PENDING) != 0) {
                    parts.add(statusClause("=", Downloads.Impl.STATUS_PENDING));
                }
                if ((mStatusFlags & STATUS_RUNNING) != 0) {
                    parts.add(statusClause("=", Downloads.Impl.STATUS_RUNNING));
                }
                if ((mStatusFlags & STATUS_PAUSED) != 0) {
                    parts.add(statusClause("=", Downloads.Impl.STATUS_PAUSED_BY_APP));
                    parts.add(statusClause("=", Downloads.Impl.STATUS_WAITING_TO_RETRY));
                    parts.add(statusClause("=", Downloads.Impl.STATUS_WAITING_FOR_NETWORK));
                    parts.add(statusClause("=", Downloads.Impl.STATUS_QUEUED_FOR_WIFI));
                }
                if ((mStatusFlags & STATUS_SUCCESSFUL) != 0) {
                    parts.add(statusClause("=", Downloads.Impl.STATUS_SUCCESS));
                }
                if ((mStatusFlags & STATUS_FAILED) != 0) {
                    parts.add("(" + statusClause(">=", 400)
                            + " AND " + statusClause("<", 600) + ")");
                }
                selectionParts.add(joinStrings(" OR ", parts));
            }

            if (mOnlyIncludeVisibleInDownloadUi) {
                selectionParts.add(Downloads.Impl.COLUMN_IS_VISIBLE_IN_DOWNLOADS_UI + " != 0");
            }

            selectionParts.add(Downloads.Impl.COLUMN_DELETED + " != '1'");

            String selection = joinStrings(" AND ", selectionParts);
            String orderDirection = (mOrderDirection == ORDER_ASCENDING ? "ASC" : "DESC");
            String orderBy = mOrderByColumn + " " + orderDirection;

            return resolver.query(uri, projection, selection, selectionArgs, orderBy);
        }

        private String joinStrings(String joiner, Iterable<String> parts) {
            StringBuilder builder = new StringBuilder();
            boolean first = true;
            for (String part : parts) {
                if (!first) {
                    builder.append(joiner);
                }
                builder.append(part);
                first = false;
            }
            return builder.toString();
        }

        private String statusClause(String operator, int value) {
            return Downloads.Impl.COLUMN_STATUS + operator + "'" + value + "'";
        }
    }

    private static class CursorTranslator extends CursorWrapper {
        private Uri mBaseUri;
        /**
         * Creates a cursor wrapper.
         *
         * @param cursor The underlying cursor to wrap.
         */
        public CursorTranslator(Cursor cursor, Uri baseUri) {
            super(cursor);
            mBaseUri = baseUri;
        }

        @Override
        public int getInt(int columnIndex) {
            return (int) getLong(columnIndex);
        }

        @Override
        public long getLong(int columnIndex) {
            if(getColumnName(columnIndex).equals(COLUMN_REASON)) {
                return getReason(super.getInt(getColumnIndex(Downloads.Impl.COLUMN_STATUS)));
            } else if(getColumnName(columnIndex).equals(COLUMN_STASUS)) {
                return translateStatus(super.getInt(getColumnIndex(Downloads.Impl.COLUMN_STATUS)));
            } else {
                return super.getLong(columnIndex);
            }
        }

        @Override
        public String getString(int columnIndex) {
            return (getColumnName(columnIndex).equals(COLUMN_LOCAL_URI) ?
            getLocalUri() : super.getString(columnIndex));
        }

        private String getLocalUri() {
            long destinationType = getLong(getColumnIndex(Downloads.Impl.COLUMN_DESTINATION));
            if(destinationType == Downloads.Impl.DESTINATION_FILE_URI
                    || destinationType == Downloads.Impl.DESTINATION_EXTERNAL
                    || destinationType == Downloads.Impl.DESTINATION_NON_DOWNLOADMANAGER_DOWNLOAD) {
                String localPath = getString(getColumnIndex(COLUMN_LOCAL_FILENAME));
                if(localPath == null) {
                    return null;
                }
                return Uri.fromFile(new File(localPath)).toString();
            }

            long downloadId = getLong(getColumnIndex(Downloads.Impl._ID));
            return ContentUris.withAppendedId(mBaseUri, downloadId).toString();
        }

        private long getReason(int status) {
            switch (translateStatus(status)) {
                case STATUS_FAILED:
                    return getErrorCode(status);

                case STATUS_PAUSED:
                    return getPausedReason(status);

                default:
                    return 0;
            }
        }

        private long getPausedReason(int status) {
            switch (status) {
                case Downloads.Impl.STATUS_WAITING_TO_RETRY:
                    return PAUSED_WAITING_TO_RETRY;

                case Downloads.Impl.STATUS_WAITING_FOR_NETWORK:
                    return PAUSED_WAITING_FOR_NETWORK;

                case Downloads.Impl.STATUS_QUEUED_FOR_WIFI:
                    return PAUSED_QUEUED_FOR_WIFI;

                default:
                    return PAUSED_UNKNOWN;
            }
        }

        private long getErrorCode(int status) {
            if((400 <= status && status < Downloads.Impl.MIN_ARTIFICIAL_ERROR_STATUS)
                    || (500 <= status && status < 600)) {
                return status;
            }

            switch (status) {
                case Downloads.Impl.STATUS_FILE_ERROR:
                    return ERROR_FILE_ERROR;

                case Downloads.Impl.STATUS_UNHANDLED_HTTP_CODE:
                case Downloads.Impl.STATUS_UNHANDLED_REDIRECT:
                    return ERROR_UNHANLED_HTTP_CODE;

                case Downloads.Impl.STATUS_HTTP_DATA_ERROR:
                    return ERROR_HTTP_DATA_ERROT;

                case Downloads.Impl.STATUS_TOO_MANY_REDIRECTS:
                    return ERROR_TOO_MANY_REDIRECTS;

                case Downloads.Impl.STATUS_INSUFFICIENT_SPACE_ERROR:
                    return ERROR_INSUFFICIENT_SPACE;

                case Downloads.Impl.STATUS_DEVICE_NOT_FOUND_ERROR:
                    return ERROR_DEVICE_NOT_FOUND;

                case Downloads.Impl.STATUS_CANNOT_RESUME:
                    return ERROR_CANNOT_RESUME;

                case Downloads.Impl.STATUS_FILE_ALREADY_EXISTS_ERROR:
                    return ERROR_FILE_ALREADY_EXISTS;

                default:
                    return ERROR_UNKNOWN;
            }
        }

        private int translateStatus(int status) {
            switch (status) {
                case Downloads.Impl.STATUS_PENDING:
                    return STATUS_PENDING;

                case Downloads.Impl.STATUS_RUNNING:
                    return STATUS_RUNNING;

                case Downloads.Impl.STATUS_PAUSED_BY_APP:
                case Downloads.Impl.STATUS_WAITING_TO_RETRY:
                case Downloads.Impl.STATUS_WAITING_FOR_NETWORK:
                case Downloads.Impl.STATUS_QUEUED_FOR_WIFI:
                    return STATUS_PAUSED;

                case Downloads.Impl.STATUS_SUCCESS:
                    return STATUS_SUCCESSFUL;

                default:
                    assert Downloads.Impl.isStatusError(status);
                    return STATUS_FAILED;
            }
        }
    }

}
